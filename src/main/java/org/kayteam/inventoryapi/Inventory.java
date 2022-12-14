package org.kayteam.inventoryapi;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.kayteam.actionapi.Actions;
import org.kayteam.requirementapi.Requirements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Inventory {

    private final String name;
    private String title;
    private int rows , updateInterval;
    private Requirements openRequirements;
    private Actions openActions;
    private boolean paginated;
    private String paginationType = "";
    private List< Integer > paginationSlots = new ArrayList<>();
    private Item paginationItemExist = new Item( new ItemStack( Material.AIR ) );
    private Item paginationItemEmpty = new Item( new ItemStack( Material.AIR ) );
    private final HashMap< Integer , Slot > slots;

    private InventoryManager inventoryManager;

    public Inventory( String name , String title , int rows , int updateInterval ) {
        this.name = name;
        this.title = title;
        this.rows = rows;
        this.updateInterval = updateInterval;
        openRequirements = new Requirements();
        openActions = new Actions();
        paginated = false;
        slots = new HashMap<>();

        for ( int slot = 0 ; slot < ( rows * 9 ) ; slot++ ) {

            slots.put( slot , new Slot( slot ) );

        }

    }

    public Inventory( String name , String title , int rows ) {
        this.name = name;
        this.title = title;
        this.rows = rows;
        this.updateInterval = 0;
        openRequirements = new Requirements();
        openActions = new Actions();
        paginated = false;
        slots = new HashMap<>();

        for ( int slot = 0 ; slot < ( rows * 9 ) ; slot++ ) {

            slots.put( slot , new Slot( slot ) );

        }

    }

    /**
     * Get the inventory name
     * @return The inventory name
     */
    public String getName() {

        return name;

    }

    /**
     * Get the inventory title
     * @return The inventory title
     */
    public String getTitle() {

        return title;

    }

    /**
     * Set the new inventory title
     * @param title The new inventory title
     */
    public void setTitle( String title ) {

        this.title = title;

    }

    /**
     * Get the inventory rows
     * @return The inventory rows
     */
    public int getRows() {

        return rows;

    }

    /**
     * Set the new inventory rows
     * @param rows The new inventory rows
     */
    public void setRows( int rows ) {

        this.rows = rows;

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
     * Verify if the inventory contain pagination
     * @return true if the inventory contain pagination or false if not
     */
    public boolean isPaginated() {

        return paginated;

    }

    /**
     * Set new paginated value
     * @param paginated The new paginated value
     */
    public void setPaginated( boolean paginated ) {

        this.paginated = paginated;

    }

    /**
     * Get the pagination type
     * @return The pagination type
     */
    public String getPaginationType() {

        return paginationType;

    }

    /**
     * Set the new pagination type
     * @param paginationType The new pagination type
     */
    public void setPaginationType( String paginationType ) {

        this.paginationType = paginationType;

    }

    /**
     * Get the pagination slots
     * @return The pagination slots
     */
    public List< Integer > getPaginationSlots() {

        return paginationSlots;

    }

    /**
     * Set the new pagination slots
     * @param paginationSlots The new pagination slots
     */
    public void setPaginationSlots( List< Integer > paginationSlots ) {

        this.paginationSlots = paginationSlots;

    }

    /**
     * Get pagination item exist
     * @return The pagination item exist
     */
    public Item getPaginationItemExist() {

        return paginationItemExist;

    }

    /**
     * Set the new pagination item exist
     * @param paginationItemExist The item new pagination item exist
     */
    public void setPaginationItemExist( Item paginationItemExist ) {

        this.paginationItemExist = paginationItemExist;

    }

    /**
     * Get the pagination item empty
     * @return The item
     */
    public Item getPaginationItemEmpty() {

        return paginationItemEmpty;

    }

    /**
     * Set the new pagination item empty
     * @param paginationItemEmpty The new pagination item empty
     */
    public void setPaginationItemEmpty( Item paginationItemEmpty ) {

        this.paginationItemEmpty = paginationItemEmpty;

    }

    /**
     * Get slots
     * @return Map with slots
     */
    public HashMap<Integer, Slot> getSlots() {

        return slots;

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

    public InventoryView generateView( Player player ) {

        InventoryView inventoryView = new InventoryView( title , rows );

        inventoryView.setInventoryManager( inventoryManager );

        inventoryView.setUpdateInterval( updateInterval );

        inventoryView.setOpenRequirements( openRequirements );

        inventoryView.setOpenActions( openActions );

        inventoryView.setPaginated( paginated );

        inventoryView.setPaginationType( paginationType );

        inventoryView.setPaginationSlots( paginationSlots );

        inventoryView.setPaginationItemExist( paginationItemExist );

        inventoryView.setPaginationItemEmpty( paginationItemEmpty );

        inventoryView.setPlayer( player );

        for ( int slot : slots.keySet() ) {

            inventoryView.getSlots().put( slot , slots.get( slot ) );

        }

        return inventoryView;

    }

    public LinkedHashMap< String , Object > serialize() {
        LinkedHashMap< String , Object > result = new LinkedHashMap<>();
        // TODO serialize inventory method
        return result;
    }

}