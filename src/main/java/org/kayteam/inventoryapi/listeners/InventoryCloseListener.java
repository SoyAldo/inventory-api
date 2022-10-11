package org.kayteam.inventoryapi.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.kayteam.inventoryapi.InventoryManager;
import org.kayteam.inventoryapi.InventoryView;

import java.util.UUID;

public class InventoryCloseListener implements Listener {

    private final InventoryManager inventoryManager;

    public InventoryCloseListener(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(InventoryCloseEvent event) {

        Player player = ( Player ) event.getPlayer();

        UUID uuid = player.getUniqueId();

        if ( ! inventoryManager.hasOpenedInventory( uuid ) ) return;

        InventoryView inventoryView = inventoryManager.getOpenedInventory( uuid );

        if ( ! event.getView().getTitle().equals( ChatColor.translateAlternateColorCodes( '&' , inventoryView.getTitle() ) ) ) return;

        inventoryManager.removeOpenedInventory( uuid );

    }

}