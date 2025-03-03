package fr.robinpluginsmc.customtpcommands;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CustomTpDataConfig {
     public static FileConfiguration getFile(CustomTpMain plugin,Player player){
    File newConfig=new File(plugin.getDataFolder(), "user_data/"+player.getUniqueId().toString()+".yml");
    return YamlConfiguration.loadConfiguration(newConfig);
   }
public static void saveNewConfig(CustomTpMain plugin,Player player,String world,Location location){
    FileConfiguration file=getFile(plugin,player);
    file.set(world, location);
    try {
        file.save(new File(plugin.getDataFolder()+"/user_data",player.getUniqueId().toString()+".yml"));
    } catch (IOException ex) {
        Logger.getLogger(CustomTpDataConfig.class.getName()).log(java.util.logging.Level.SEVERE, "mauvaise config: ", ex);
        
    }
}
public static Location getConfig(CustomTpMain plugin,Player player,String world){
    FileConfiguration file=getFile(plugin,player);
    return (Location) file.get(world);
}
}
