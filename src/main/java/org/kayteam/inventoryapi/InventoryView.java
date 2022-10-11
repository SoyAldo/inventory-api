package org.kayteam.inventoryapi;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import org.kayteam.actionapi.Actions;
import org.kayteam.requirementapi.Requirements;

import java.util.HashMap;

public class InventoryView {

    private final String title;
    private final int rows;
    private int updateInterval;
    private Requirements openRequirements;
    private Actions openActions;
    private final HashMap<Integer , Slot> slots , visibleSlots;

    private InventoryManager inventoryManager;
    private InventoryUpdater inventoryUpdater;
    private Inventory inventory;
    private Player player;

    public InventoryView( String title , int rows ) {

        this.title = title;

        this.rows = rows;

        slots = new HashMap<>();

        visibleSlots = new HashMap<>();

        for ( int slot = 0 ; slot < ( rows * 9 ) ; slot++ ) {

            slots.put( slot , new Slot( slot ) );

            visibleSlots.put( slot , new Slot( slot ) );

        }

    }

    /**
     * Get the inventory title
     * @return The inventory title
     */
    public String getTitle() {

        return title;

    }

    /**
     * Get the inventory rows
     * @return The inventory rows
     */
    public int getRows() {

        return rows;

    }

    /**
     * Get the inventory update interval
     * @return The inventory update interval
     */
    public int getUpdateInterval() {

        return updateInterval;

    }

    /**
     * Set the new inventory update interval
     * @param updateInterval The new inventory update interval
     */
    public void setUpdateInterval( int updateInterval ) {

        this.updateInterval = updateInterval;

    }

    /**
     * Get the open requirements
     * @return The open requirements
     */
    public Requirements getOpenRequirements() {

        return openRequirements;

    }

    /**
     * Set the new open requirements
     * @param openRequirements The new requirements
     */
    public void setOpenRequirements( Requirements openRequirements ) {

        this.openRequirements = openRequirements;

    }

    /**
     * Get the open actions
     * @return The open actions
     */
    public Actions getOpenActions() {

        return openActions;

    }

    /**
     * Set the new open actions
     * @param openActions The new open actions
     */
    public void setOpenActions( Actions openActions ) {

        this.openActions = openActions;

    }

    /**
     * Get the slots
     * @return The slots
     */
    public HashMap<Integer , Slot> getSlots() {

        return slots;

    }

    /**
     * Get the visible slots
     * @return The visible slots
     */
    public HashMap<Integer , Slot> getVisibleSlots() {

        return visibleSlots;

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
     * Get the inventory updater
     * @return The inventory updater
     */
    public InventoryUpdater getInventoryUpdater() {

        return inventoryUpdater;

    }

    /**
     * Set the new inventory updater
     * @param inventoryUpdater The new inventory updater
     */
    public void setInventoryUpdater( InventoryUpdater inventoryUpdater ) {

        this.inventoryUpdater = inventoryUpdater;

    }

    /**
     * Get the inventory
     * @return The inventory
     */
    public Inventory getInventory() {

        return inventory;

    }

    /**
     * Set the new inventory
     * @param inventory The new inventory
     */
    public void setInventory( Inventory inventory ) {

        this.inventory = inventory;

    }

    /**
     * Get the player
     * @return The player
     */
    public Player getPlayer() {

        return player;

    }

    /**
     * Set the new player
     * @param player The new player
     */
    public void setPlayer( Player player ) {

        this.player = player;

    }

    /**
     * Open the inventory
     */
    public void openInventory() {

        if ( ! openRequirements.verifyAll( player ) )   return;

        inventory = Bukkit.createInventory( null , rows * 9 , ChatColor.translateAlternateColorCodes( '&' , title ) );

        for ( Slot slot : slots.values() ) {

            slot.sortItems();

            Slot visibleSlot = new Slot( slot.getSlot() );

            if ( slot.getItems().isEmpty() )   continue;

            Item result = null;

            for ( int i = 0 ; i < slot.getItems().size() ; i++) {

                Item item = slot.getItems().get( i );

                if ( item.getViewRequirements().verifyAll( player , false ) ) {

                    result = item;

                    break;

                }

            }

            if ( result != null ) {

                visibleSlot.getItems().add( result );

                visibleSlots.put( slot.getSlot() , visibleSlot );

                inventory.setItem( slot.getSlot() , visibleSlot.getItems().get(0).getItemStack() );

            }

        }

        if ( updateInterval > 0 ) {

            inventoryUpdater = new InventoryUpdater( inventoryManager , this );

            inventoryUpdater.runTaskTimer( inventoryManager.getJavaPlugin() , 0 , updateInterval );

        }

        openActions.executeAll( player );

        player.openInventory( inventory );

        inventoryManager.addOpenedInventory( this );

    }

}