package com.connorlinfoot.worldmanager.Handlers;

import com.connorlinfoot.worldmanager.Main;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;

import java.io.File;
import java.io.IOException;

public class WorldGenerator {

    public static void createWorld(String name){
        org.bukkit.WorldCreator world = new org.bukkit.WorldCreator(name);
        Main.getInstance().getServer().createWorld(world);
        World w = Bukkit.getWorld(name);
        Configuration.createWorldConfig(w);
    }

    private static void loadArea(World world, File file, Vector origin) throws DataException, IOException, MaxChangedBlocksException {
        EditSession es = new EditSession(new BukkitWorld(world), 999999999);
        CuboidClipboard cc = CuboidClipboard.loadSchematic(file);
        cc.paste(es, origin, false);
    }

}
