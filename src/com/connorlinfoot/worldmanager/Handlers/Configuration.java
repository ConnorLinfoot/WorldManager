package com.connorlinfoot.worldmanager.Handlers;

import com.connorlinfoot.worldmanager.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Configuration {

    public static FileConfiguration getWorldConfiguration(String name){
        File folder = new File(Main.getInstance().getDataFolder() + "/worlds");
        if( folder.listFiles() == null ) return null;
        for ( final File file : folder.listFiles() ) {
            if (!file.isDirectory() && file.getName().equalsIgnoreCase(name)) {
                return YamlConfiguration.loadConfiguration(file);
            }
        }
        return null;
    }

    public static boolean createWorldConfig(World w){
        boolean debug = Main.getInstance().getConfig().getBoolean("Debug Mode");
        File folder = new File(Main.getInstance().getDataFolder() + "/worlds");
        String wName = w.getName();
        File file = new File(folder,wName + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("Name",wName);
        config.set("Alias",wName);
        config.set("Hidden",false);
        config.set("Import Backup",false);
        config.set("Backup Name",wName);
        config.set("Spawn.X",w.getSpawnLocation().getX());
        config.set("Spawn.Y",w.getSpawnLocation().getY());
        config.set("Spawn.Z",w.getSpawnLocation().getZ());
        config.set("Force Spawn",false);
        config.set("Player Limit",-1);
        try {
            config.save(file);
            if( debug ) Bukkit.getServer().getLogger().info("World Manager Debug: World Config for world: " + wName + " has been created");
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getServer().getLogger().info("World Manager: Failed saving config for world: " + wName + ". Aborting");
            return false;
        }
        return true;
    }

}
