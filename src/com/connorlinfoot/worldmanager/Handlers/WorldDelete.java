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
                player.sendMessage("Server Rebooting"); // Kicks any players for reset
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);

                try {
                    out.writeUTF("Connect");
                    out.writeUTF("hub");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                player.sendPluginMessage(instance, "BungeeCord", b.toByteArray());
            }

            instance.getServer().getLogger().info("Old UHC world found...");
            instance.getServer().unloadWorld(name, true);
            File directory = new File(name);
            if (directory.exists()) {
                try {
                    instance.getServer().getLogger().info("Deleting old UHC world...");
                    com.enkelhosting.filemanager.Main.deleteFiles(directory);
                    instance.getServer().getLogger().info("Deleted old UHC world");
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
