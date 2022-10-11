package org.kayteam.inventoryapi.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.kayteam.inventoryapi.*;

import java.util.UUID;

public class InventoryClickListener implements Listener {

    private final InventoryManager inventoryManager;

    public InventoryClickListener(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = ( Player ) event.getWhoClicked();

        UUID uuid = player.getUniqueId();

        if ( ! inventoryManager.hasOpenedInventory( uuid ) )   return;

        InventoryView inventoryView = inventoryManager.getOpenedInventory( uuid );

        if ( ! event.getView().getTitle().equals( ChatColor.translateAlternateColorCodes( '&' , inventoryView.getTitle() ) ) )   return;

        event.setCancelled( true );

        int rawSlot = event.getRawSlot();

        if ( ! inventoryView.getVisibleSlots().containsKey( rawSlot ) )   return;

        Slot slot = inventoryView.getSlots().get( rawSlot );

        if ( slot.getItems().isEmpty() )   return;

        Item item = slot.getItems().get( 0 );

        ClickType clickType = event.getClick();

        ClickActions clickActions = item.getClickActions().get( clickType );

        if ( clickActions == null )   return;

        if ( ! clickActions.getRequirements().verifyAll( player ) )   return;

        clickActions.getActions().executeAll( player );

    }

}