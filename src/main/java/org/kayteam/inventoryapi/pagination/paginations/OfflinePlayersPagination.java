package org.kayteam.inventoryapi.pagination.paginations;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.kayteam.inventoryapi.InventoryManager;
import org.kayteam.inventoryapi.pagination.Pagination;

import java.util.ArrayList;
import java.util.Arrays;

public class OfflinePlayersPagination extends Pagination {

    public OfflinePlayersPagination() {

        super( "offlinePlayers" );

    }

    @Override
    public void loadData() {

        // Get the inventory manager
        InventoryManager inventoryManager = getInventoryManager();

        // Get the java plugin
        JavaPlugin javaPlugin = inventoryManager.getJavaPlugin();

        // Get the server
        Server server = javaPlugin.getServer();

        // Get all online players
        OfflinePlayer[] offlinePlayers = server.getOfflinePlayers();

        // Set the data
        setData( Arrays.asList( offlinePlayers ) );

    }

    @Override
    public void updateData() {

        // Get the inventory manager
        InventoryManager inventoryManager = getInventoryManager();

        // Get the java plugin
        JavaPlugin javaPlugin = inventoryManager.getJavaPlugin();

        // Get the server
        Server server = javaPlugin.getServer();

        // Get all online players
        OfflinePlayer[] offlinePlayers = server.getOfflinePlayers();

        // Set the data
        setData( Arrays.asList( offlinePlayers ) );

    }

    @Override
    public void unloadData() {

        // Set the data to empty list
        setData( new ArrayList<>() );

    }

}