package org.kayteam.inventoryapi.pagination.paginations;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.kayteam.inventoryapi.InventoryManager;
import org.kayteam.inventoryapi.pagination.Pagination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PlayersPagination extends Pagination {

    public PlayersPagination() {

        super( "players" );

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
        Collection<? extends Player> players = server.getOnlinePlayers();

        // Set the data
        setData( Collections.singletonList( players ) );

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
        Collection<? extends Player> players = server.getOnlinePlayers();

        // Set the data
        setData( Collections.singletonList( players ) );

    }

    @Override
    public void unloadData() {

        // Set the data to empty list
        setData( new ArrayList<>() );

    }

}