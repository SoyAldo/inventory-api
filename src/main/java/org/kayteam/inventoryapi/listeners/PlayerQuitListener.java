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

        Player player = event.getPlayer();

        UUID uuid = player.getUniqueId();

        inventoryManager.removeOpenedInventory( uuid );

        // Pagination

        if ( inventoryManager.existPagination( "players" ) ) {

            Pagination playersPagination = inventoryManager.getPagination( "players" );

            playersPagination.updateData();

        }

    }

}