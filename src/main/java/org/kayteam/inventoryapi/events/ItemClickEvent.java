package org.kayteam.inventoryapi.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.kayteam.inventoryapi.InventoryManager;
import org.kayteam.inventoryapi.InventoryView;
import org.kayteam.inventoryapi.Item;

public class ItemClickEvent extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();
    private boolean cancel = false;
    private final InventoryManager inventoryManager;
    private final InventoryView inventoryView;
    private final Player player;
    private final Item item;

    public ItemClickEvent( InventoryManager inventoryManager , InventoryView inventoryView , Player player , Item item ) {

        this.inventoryManager = inventoryManager;

        this.inventoryView = inventoryView;

        this.player = player;

        this.item = item;

    }

    public InventoryManager getInventoryManager() {

        return inventoryManager;

    }

    public InventoryView getInventoryView() {

        return inventoryView;

    }

    public Player getPlayer() {

        return player;

    }

    public Item getItem() {

        return item;

    }

    @Override
    public boolean isCancelled() {

        return cancel;

    }

    @Override
    public void setCancelled( boolean cancel ) {

        this.cancel = cancel;

    }

    @Override
    public HandlerList getHandlers() {

        return handlerList;

    }

    public static HandlerList getHandlerList() {

        return handlerList;

    }

}