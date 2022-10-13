package org.kayteam.inventoryapi.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.kayteam.inventoryapi.InventoryManager;
import org.kayteam.inventoryapi.InventoryView;

public class InventoryOpenEvent extends Event implements Cancellable {

    private final static HandlerList handlerList = new HandlerList();
    private boolean cancel = false;

    private final InventoryManager inventoryManager;
    private final InventoryView inventoryView;
    private final Player player;

    public InventoryOpenEvent( InventoryManager inventoryManager , InventoryView inventoryView , Player player ) {

        this.inventoryManager = inventoryManager;

        this.inventoryView = inventoryView;

        this.player = player;

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

    /**
     * Get the player
     * @return The player
     */
    public Player getPlayer() {

        return player;

    }

    @Override
    public HandlerList getHandlers() {

        return handlerList;

    }

    public static HandlerList getHandlerList() {

        return handlerList;

    }

    @Override
    public boolean isCancelled() {

        return cancel;

    }

    @Override
    public void setCancelled(boolean cancel) {

        this.cancel = cancel;

    }

}