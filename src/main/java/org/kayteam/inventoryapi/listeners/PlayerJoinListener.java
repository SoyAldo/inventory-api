package org.kayteam.inventoryapi.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.kayteam.inventoryapi.InventoryManager;
import org.kayteam.inventoryapi.pagination.Pagination;

public class PlayerJoinListener implements Listener {

    private final InventoryManager inventoryManager;

    public PlayerJoinListener( InventoryManager inventoryManager ) {

        this.inventoryManager = inventoryManager;

    }

    @EventHandler
    public void onPlayerJoin( PlayerJoinEvent event ) {

        // Pagination
        playersPagination();
        offlinePlayersPagination( event );

    }

    private void playersPagination() {

        if ( ! inventoryManager.existPagination( "players" ) )   return;

        Pagination playersPagination = inventoryManager.getPagination( "players" );

        playersPagination.updateData();

    }

    private void offlinePlayersPagination( PlayerJoinEvent event ) {

        if ( ! inventoryManager.existPagination( "offlinePlayers" ) )   return;

        Player player = event.getPlayer();

        if ( player.hasPlayedBefore() )   return;

        Pagination playersPagination = inventoryManager.getPagination( "offlinePlayers" );

        playersPagination.updateData();

    }

}