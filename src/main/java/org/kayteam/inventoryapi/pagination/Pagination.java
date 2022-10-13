package org.kayteam.inventoryapi.pagination;

import org.kayteam.inventoryapi.InventoryManager;

import java.util.ArrayList;
import java.util.List;

public abstract class Pagination {

    private final String name;
    private List< Object > data = new ArrayList<>();
    private InventoryManager inventoryManager;

    public Pagination(String name) {

        this.name = name;

    }

    /**
     * Get name
     * @return The name
     */
    public String getName() {

        return name;

    }

    /**
     * Get data
     * @return The data
     */
    public List<Object> getData() {

        return data;

    }

    /**
     * Set the new data
     * @param data The new data
     */
    public void setData(List<Object> data ) {

        this.data = data;

    }

    /**
     * Get the inventory manager
     * @return The inventory manager
     */
    public InventoryManager getInventoryManager() {

        return inventoryManager;

    }

    /**
     * Set the new inventory manager
     * @param inventoryManager The new inventory manager
     */
    public void setInventoryManager( InventoryManager inventoryManager ) {

        this.inventoryManager = inventoryManager;

    }

    /**
     * Load the data
     */
    public abstract void loadData();

    /**
     * Update the data
     */
    public abstract void updateData();

    /**
     * Unload the data
     */
    public abstract void unloadData();

}