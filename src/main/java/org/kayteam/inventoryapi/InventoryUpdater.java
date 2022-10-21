package org.kayteam.inventoryapi;

import org.bukkit.scheduler.BukkitRunnable;

public class InventoryUpdater extends BukkitRunnable {

    private final InventoryManager inventoryManager;
    private final InventoryView inventoryView;

    public InventoryUpdater( InventoryManager inventoryManager , InventoryView inventoryView ) {

        this.inventoryManager = inventoryManager;

        this.inventoryView = inventoryView;

    }

    /**
     * Get the inventory manager
     * @return The inventory manager
     */
    public InventoryManager getInventoryManager() {

        return inventoryManager;

    }

    /**
     * Get the inventory view
     * @return The inventory view
     */
    public InventoryView getInventoryView() {

        return inventoryView;

    }

    @Override
    public void run() {

        if ( ! inventoryManager.getOpenedInventories().containsValue( inventoryView ) ) {

            cancel();

            return;

        }

        for ( Slot slot : inventoryView.getVisibleSlots().values() ) {

            if ( slot.getItems().isEmpty() ) continue;

            Item item = slot.getItems().get( 0 );

            if ( ! item.isUpdate() ) continue;

            item.updateItem( inventoryManager , inventoryView , inventoryView.getPlayer() );

            inventoryView.getInventory().setItem( slot.getSlot() , item.getItemStack() );

        }

    }

}