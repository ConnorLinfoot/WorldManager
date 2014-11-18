package com.connorlinfoot.worldmanager.Handlers;

import com.connorlinfoot.worldmanager.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WorldUtil {

    public static boolean checkPlayers(World world){
        FileConfiguration config = Configuration.getWorldConfiguration(world.getName());
        if( config == null ) return true;

        if( config.getInt("Player Limit") != -1 ) {
            Integer limit = config.getInt("Player Limit");
            if( world.getPlayers().size() >= limit ){
                return false;
            }
        }
        return true;
    }

    public static List<World> listWorlds(boolean includeHiddenWorlds){
        List<World> worldList = new ArrayList<World>();
        File folder = new File(Main.getInstance().getDataFolder() + "/worlds");
        if( folder.listFiles() == null ) return null;
        for ( final File file : folder.listFiles() ) {
            if( Bukkit.getWorld(file.getName().replace(".yml","")) == null ){
                continue;
            }
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            if( !includeHiddenWorlds ) {
                if (!config.getBoolean("Hidden")) {
                    World w = Bukkit.getWorld(file.getName().replace(".yml",""));
                    worldList.add(w);
                }
            }
        }
        return worldList;
    }

}