package fr.robinpluginsmc.customtpcommands;


import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class CustomTpWorldListener implements Listener {
    private final CustomTpMain plugin;
    private final List<String> worlds;
    public CustomTpWorldListener(CustomTpMain plugin,List<String> worlds) {
        this.plugin = plugin;
        this.worlds=worlds;
        plugin.getLogger().info("loading world listener, list of worlds");
    }
       @EventHandler(
      priority = EventPriority.HIGH
   )
  
   public void onPlayerTeleport(PlayerTeleportEvent event) {
    Player player = event.getPlayer();
    Location location = event.getFrom();
    World world = location.getWorld();
    if (world != null && worlds.contains(world.getName()) && !location.equals(event.getTo())) {
        CustomTpDataConfig.saveNewConfig(plugin,player,world.getName(),location);
    }
   }
    @EventHandler(
        priority = EventPriority.HIGH
    )
   public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    Location location = player.getLocation();
    if(location!=null){
    World world = location.getWorld();
    if (world != null && worlds.contains(world.getName())) {
        CustomTpDataConfig.saveNewConfig(plugin,player,world.getName(),location);
    }
   }
  
   }


}
