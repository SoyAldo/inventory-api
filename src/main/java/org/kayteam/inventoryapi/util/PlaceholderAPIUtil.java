package org.kayteam.inventoryapi.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class PlaceholderAPIUtil {

    public static String setPlaceholders( Player player , String text ) {

        Server server = Bukkit.getServer();

        PluginManager pluginManager = server.getPluginManager();

        Plugin placeholderapi = pluginManager.getPlugin( "PlaceholderAPI" );

        if ( placeholderapi != null ) text = PlaceholderAPI.setPlaceholders( player , text );

        return text;

    }

    public static String setPlaceholders( OfflinePlayer player , String text ) {

        Server server = Bukkit.getServer();

        PluginManager pluginManager = server.getPluginManager();

        Plugin placeholderapi = pluginManager.getPlugin( "PlaceholderAPI" );

        if ( placeholderapi != null ) text = PlaceholderAPI.setPlaceholders( player , text );

        return text;

    }

}