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

        // Paginations
        playersPagination();
        offlinePlayersPagination( event );

    }

    private void playersPagination() {

        // return if players pagination don't exist
        if ( ! inventoryManager.existPagination( "players" ) )   return;

        // Get the players pagination
        Pagination playersPagination = inventoryManager.getPagination( "players" );

        // Update the pagination
        playersPagination.updateData();

    }

    private void offlinePlayersPagination( PlayerJoinEvent event ) {

        // return if offlinePlayers pagination don't exist
        if ( ! inventoryManager.existPagination( "offlinePlayers" ) )   return;

        // Get the player
        Player player = event.getPlayer();

        // return ff the player has played before
        if ( player.hasPlayedBefore() )   return;

        // Get the offlinePlayers pagination
        Pagination playersPagination = inventoryManager.getPagination( "offlinePlayers" );

        // Update the pagination
        playersPagination.updateData();

    }

}