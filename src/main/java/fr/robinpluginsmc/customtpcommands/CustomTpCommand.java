package fr.robinpluginsmc.customtpcommands;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CustomTpCommand extends BukkitCommand {
    private final Map<UUID, Integer> cooldowns = new HashMap<>();
    private final CustomTpMain plugin;
    private final String world;
    private final Boolean rememberLocation;
    private final int cooldown;
    private final String message;

    public CustomTpCommand(String name, String description,String permission, String message, String world, Boolean rememberLocation, int cooldown,  CustomTpMain plugin) {
        super(name);
        this.plugin = plugin;
        this.world = world;
        this.setUsage(name);
        if(name==null || message==null||world==null||rememberLocation==null){
            throw new IllegalArgumentException("erreur dans les paramètres");
        }
        if(description!=null){
        this.setDescription(description);
        }
        else{
            this.setDescription(name);
        }
        if(permission!=null){
        this.setPermission(permission);
        }
        else{
            this.setPermission("customtpcommand.globaltp");
        }
        this.rememberLocation = rememberLocation;
        this.cooldown = cooldown;
        this.message = message;
        
    }
    public void setCooldown(UUID player, int time){
        if(time < 1) {
            cooldowns.remove(player);
        } else {
            cooldowns.put(player, time);
        }
    }
    public int getCooldown(UUID player){
        return cooldowns.getOrDefault(player, 0);
    }
    @Override
    public boolean execute(org.bukkit.command.CommandSender sender, String commandLabel, String[] args) {
            Player player = (Player) sender;
            String permission=this.getPermission();
            if(permission==null){
                permission="customtpcommand.globaltp";
            }
            if(player.hasPermission(permission)){
                int timeLeft = getCooldown(player.getUniqueId());
                if(timeLeft == 0){
                    setCooldown(player.getUniqueId(), cooldown);
                    if(cooldown > 0){
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                int timeLeft = getCooldown(player.getUniqueId());
                                setCooldown(player.getUniqueId(), --timeLeft);
                                if(timeLeft == 0){
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(this.plugin, 20, 20);
                    }
                    World world2 = plugin.getServer().getWorld(this.world);
                    if(world2==null){
                        plugin.getLogger().warning(String.format("world not found for command %s: %s", this.getName(), this.world));
                        player.sendMessage("you can't teleport to your destination. Ask the server admin to fix this.");
                    }
                    else{
                        if(rememberLocation){
                            Location location=CustomTpDataConfig.getConfig(plugin,player,world2.getName());
                            if(location!=null){
                                player.teleport(location);
                            }
                            else{
                                player.teleport(world2.getSpawnLocation());
                            }
                        }
                        else{
                            player.teleport(world2.getSpawnLocation());
                        }
                    }
                    player.sendMessage(message);
                }
                else{
                    String cooldownMessage=plugin.getConfig().getString("cooldownMessage");
                    if(cooldownMessage!=null){
                        player.sendMessage(cooldownMessage.replace("%time%", String.valueOf(timeLeft)));
                    }
                    else{
                        player.sendMessage("wait "+timeLeft+" seconds");
                    }
                }
            }
            else{
                String nopermission=plugin.getConfig().getString("nopermission");
                if(nopermission!=null){
                    player.sendMessage(nopermission);
                }
                else{
                    player.sendMessage("no permission");
                }
            }
        return sender instanceof Player != false;
    }
    
}
