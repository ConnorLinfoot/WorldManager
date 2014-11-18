package com.connorlinfoot.worldmanager.Handlers;

import com.connorlinfoot.worldmanager.Main;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

public class WorldDelete {

    public static boolean deleteWorld(String name){
        Plugin instance = Main.getInstance();
        World world = instance.getServer().getWorld(name);
        if( world != null ) {
            Player[] onlinePlayers = instance.getServer().getOnlinePlayers();
            for( Player player : onlinePlayers ){
                player.kickPlayer("Server Rebooting"); // Kicks any players for reset
            }

            instance.getServer().getLogger().info("World found...");
            instance.getServer().unloadWorld(name, true);
            File directory = new File(name);
            if (directory.exists()) {
                try {
                    instance.getServer().getLogger().info("Deleting world...");
                    com.enkelhosting.filemanager.Main.deleteFiles(directory);
                    instance.getServer().getLogger().info("Deleted world");
                    return true;
                } catch (IOException ignored) {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

}
