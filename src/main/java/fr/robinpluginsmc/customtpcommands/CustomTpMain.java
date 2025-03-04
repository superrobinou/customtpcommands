package fr.robinpluginsmc.customtpcommands;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Hello world!
 *
 */
public class CustomTpMain extends JavaPlugin{
    public static void main( String[] args )
    {
       
    }
    @Override
    public void onEnable() {
        List<String> worlds = new ArrayList<>();
        this.saveDefaultConfig();
        ConfigurationSection section=this.getConfig().getConfigurationSection("commands");
        if(section!=null){
        for(String key:section.getKeys(false)){
            ConfigurationSection section2=section.getConfigurationSection(key);
            if(section2!=null){
                String message=section2.getString("message");
                String world=section2.getString("world");
                String description=section2.getString("description");
                String permission=section2.getString("permission");
                Boolean rememberLocation=section2.getBoolean("rememberLocation");
                if(rememberLocation){
                    worlds.add(world);
                }
                int cooldown=section2.getInt("cooldown");
                CustomTpCommand command=new CustomTpCommand(key, description,permission,message, world, rememberLocation, cooldown, this);
                try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
           bukkitCommandMap.setAccessible(true);
          CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
          commandMap.register("customTpMain", command);
} catch(IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
}
            }
        }
        this.getServer().getPluginManager().registerEvents(new CustomTpWorldListener(this,worlds), this);
    }
    }
}
