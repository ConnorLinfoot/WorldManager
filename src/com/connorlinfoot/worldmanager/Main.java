package com.connorlinfoot.worldmanager;

import com.connorlinfoot.worldmanager.Handlers.Startup;
import com.connorlinfoot.worldmanager.Handlers.WorldDelete;
import com.connorlinfoot.worldmanager.Handlers.WorldImporter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;


public class Main extends JavaPlugin {
    private static Plugin instance;

    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults(true);
        saveConfig();

        if( getConfig().getInt("Config Version") == 0 ){
            Startup.resetWorldConfig();
            if( Startup.runSetup() ){
                getConfig().set("Config Version",1);
            } else {
                getLogger().warning("World Manager: Failed to setup");
                getPluginLoader().disablePlugin(this);
                return;
            }
        }


        Server server = getServer();
        ConsoleCommandSender console = server.getConsoleSender();

        console.sendMessage(ChatColor.GREEN + "=========== WorldManager ===========");
        console.sendMessage(ChatColor.GREEN + "=========== VERSION: 1.0 ===========");
        console.sendMessage(ChatColor.GREEN + "======== BY CONNOR LINFOOT! ========");

        // Call world generator method
        //WorldGenerator.createWorld("game");

        if( getConfig().getBoolean("World Importer") ){
            try {
                WorldImporter.importWorld(getConfig().getString("Importer World Name"), getConfig().getString("Destination World Name"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

    public void onDisable() {
        getLogger().info(getDescription().getName() + " has been disabled!");
        if( getConfig().getBoolean("World Importer") ) {
            //WorldDelete.deleteWorld(getConfig().getString("Destination World Name"));
        }
        if( getServer().getOnlinePlayers() == null ){
            for( World w : getServer().getWorlds() ){
                getServer().unloadWorld(w,true);
            }
            Startup.backupWorlds();
        }
    }

    public static Plugin getInstance() {
        return instance;
    }
}