package org.kayteam.inventoryapi.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.kayteam.inventoryapi.InventoryManager;
import org.kayteam.inventoryapi.InventoryView;
import org.kayteam.inventoryapi.Item;

import java.util.HashMap;

public class ItemUpdateEvent extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();
    private boolean cancel = false;
    private final InventoryManager inventoryManager;
    private final InventoryView inventoryView;
    private final Item item;
    private final HashMap< String , String > replacements = new HashMap<>();

    public ItemUpdateEvent( InventoryManager inventoryManager , InventoryView inventoryView , Item item ) {

        this.inventoryManager = inventoryManager;

        this.inventoryView = inventoryView;

        this.item = item;

    }

    public InventoryManager getInventoryManager() {

        return inventoryManager;

    }

    public InventoryView getInventoryView() {

        return inventoryView;

    }

    public Item getItem() {

        return item;

    }

    public HashMap<String, String> getReplacements() {

        return replacements;

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