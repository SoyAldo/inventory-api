package org.kayteam.inventoryapi.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.kayteam.inventoryapi.InventoryManager;
import org.kayteam.inventoryapi.pagination.Pagination;

import java.util.UUID;

public class PlayerQuitListener implements Listener {

    private final InventoryManager inventoryManager;

    public PlayerQuitListener( InventoryManager inventoryManager ) {

        this.inventoryManager = inventoryManager;

    }

    @EventHandler
    public void onPlayerQuit( PlayerQuitEvent event ) {

        // Get the player
        Player player = event.getPlayer();

        // Get the player uuid
        UUID uuid = player.getUniqueId();

        // Remove opened inventory from the player
        inventoryManager.removeOpenedInventory( uuid );

        // Paginations
        playersPagination();

    }

    private void playersPagination() {

        // return if players pagination don't exist
        if ( ! inventoryManager.existPagination( "players" ) )   return;

        // Get the players pagination
        Pagination playersPagination = inventoryManager.getPagination( "players" );

        // Update the pagination
        playersPagination.updateData();

    }

}