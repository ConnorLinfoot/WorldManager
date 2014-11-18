package com.connorlinfoot.worldmanager.Handlers;

import com.connorlinfoot.worldmanager.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Startup {

    public static boolean runSetup(){
        boolean debug = Main.getInstance().getConfig().getBoolean("Debug Mode");
        File folder = new File(Main.getInstance().getDataFolder() + "/worlds");
        if(!folder.exists()){
            if( !folder.mkdir() ) {
                Bukkit.getServer().getLogger().warning("World Manager: Failed to create worlds directory, aborting");
                return false;
            }
            if( debug ) Bukkit.getServer().getLogger().info("World Manager Debug: Created worlds directory");
        } else if( debug ) Bukkit.getServer().getLogger().info("World Manager Debug: Worlds directory already exists");


        File backup = new File(Main.getInstance().getDataFolder() + "/backups");
        if(!backup.exists()){
            if( !backup.mkdir() ) {
                Bukkit.getServer().getLogger().warning("World Manager: Failed to create backups directory, aborting");
                return false;
            }
            if( debug ) Bukkit.getServer().getLogger().info("World Manager Debug: Created backups directory");
        } else if( debug ) Bukkit.getServer().getLogger().info("World Manager Debug: backups directory already exists");


        List<World> worlds = Bukkit.getWorlds();
        for( World w : worlds ){
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
        }
        if( debug ) Bukkit.getServer().getLogger().info("World Manager Debug: World config for all worlds has been created");



        return true;
    }

    public static boolean backupWorlds(){
        File folder = new File(Main.getInstance().getDataFolder() + "/worlds");
        if( folder.listFiles() == null ) return true;
        for ( final File file : folder.listFiles() ) {
            if (!file.isDirectory()) {
                FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                if( config.getBoolean("Import Backup") ){
                    File backups = new File(Main.getInstance().getDataFolder() + "/backups");
                    boolean runBackup = false;
                    if( backups.listFiles() == null ) runBackup = true;

                    if( !runBackup ){
                        for ( final File backup : backups.listFiles() ) {
                            if( config.getString("Backup Name").equalsIgnoreCase(backup.getName()) ){
                                runBackup = false;
                                break;
                            }
                        }
                    }

                    if( runBackup ) {
                        File srcFolder = new File(config.getString("Name"));
                        File destFolder = new File(backups + config.getString("Backup Name"));

                        if (!srcFolder.exists()) {
                            System.out.println("World does not exist");
                        } else {
                            try {
                                com.enkelhosting.filemanager.Main.copyFolder(srcFolder, destFolder);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    public static boolean resetWorldConfig(){
        // TODO Finish method for resetting world config
        return true;
    }

}