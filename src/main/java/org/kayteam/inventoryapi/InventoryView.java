package org.kayteam.inventoryapi;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.kayteam.actionapi.Actions;
import org.kayteam.inventoryapi.events.InventoryOpenEvent;
import org.kayteam.inventoryapi.util.PlaceholderAPIUtil;
import org.kayteam.requirementapi.Requirements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryView {

    private final String title;
    private final int rows;
    private int updateInterval;
    private Requirements openRequirements;
    private Actions openActions;
    private boolean paginated;
    private int page = 1;
    private String paginationType = "";
    private List< Integer > paginationSlots = new ArrayList<>();
    private Item paginationItemExist = new Item( new ItemStack( Material.AIR ) );
    private Item paginationItemEmpty = new Item( new ItemStack( Material.AIR ) );
    private final HashMap<Integer , Slot> slots , visibleSlots;

    private InventoryManager inventoryManager;
    private InventoryUpdater inventoryUpdater;
    private Inventory inventory;
    private Player player;
    private boolean clickable = true;

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
     * Get the page
     * @return The page
     */
    public int getPage() {

        return page;

    }

    /**
     * Set the new page
     * @param page The new page
     */
    public void setPage( int page ) {

        this.page = page;

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
     * Verify if the inventory is clickable
     * @return true if is clickable or false if not
     */
    public boolean isClickable() {

        return clickable;

    }

    /**
     * Set the new clickable value
     * @param clickable The new clickable value
     */
    public void setClickable( boolean clickable ) {

        this.clickable = clickable;

    }

    /**
     * Open the inventory
     */
    public void openInventory() {

        if ( ! openRequirements.verifyAll( player ) )   return;

        String realTitle = title;

        realTitle = PlaceholderAPIUtil.setPlaceholders( player , realTitle );

        realTitle = ChatColor.translateAlternateColorCodes( '&' , realTitle );

        inventory = Bukkit.createInventory( null , rows * 9 , realTitle );

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

            if ( result == null )   continue;

            visibleSlot.getItems().add( result );

            visibleSlots.put( slot.getSlot() , visibleSlot );

        }

        if ( updateInterval > 0 ) {

            inventoryUpdater = new InventoryUpdater( inventoryManager , this );

            inventoryUpdater.runTaskTimer( inventoryManager.getJavaPlugin() , 0 , updateInterval );

        }

        openActions.executeAll( player );

        for ( Slot visibleSlot : visibleSlots.values() ) {

            if ( visibleSlot.getItems().isEmpty() )   continue;

            visibleSlot.sortItems();

            Item visibleItem = visibleSlot.getItems().get( 0 );

            inventory.setItem( visibleSlot.getSlot() , visibleItem.getItemStack() );

        }

        if ( paginated ) {

            // TODO pagination
            for ( int slotNumber : paginationSlots ) {

                Slot visibleSlot = visibleSlots.get( slotNumber );

                visibleSlot.getItems().clear();

                Item a = new Item( paginationItemExist );

                visibleSlot.getItems().add( a );

            }

        }

        JavaPlugin javaPlugin = inventoryManager.getJavaPlugin();

        InventoryOpenEvent inventoryOpenEvent = new InventoryOpenEvent( inventoryManager , this , player );

        Server server = javaPlugin.getServer();

        PluginManager pluginManager = server.getPluginManager();

        pluginManager.callEvent( inventoryOpenEvent );

        if ( inventoryOpenEvent.isCancelled() )  return;

        player.openInventory( inventory );

        inventoryManager.addOpenedInventory( this );

    }

    public void reloadItems() {

        clickable = false;

        for ( Slot visibleSlot : visibleSlots.values() )   visibleSlot.getItems().clear();

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

            if ( result == null )   continue;

            visibleSlot.getItems().add( result );

            visibleSlots.put( slot.getSlot() , visibleSlot );

        }

        for ( Slot visibleSlot : visibleSlots.values() ) {

            if ( visibleSlot.getItems().isEmpty() )   continue;

            visibleSlot.sortItems();

            Item visibleItem = visibleSlot.getItems().get( 0 );

            inventory.setItem( visibleSlot.getSlot() , visibleItem.getItemStack() );

        }

        if ( paginated ) {

            // TODO pagination

        }

        clickable = true;

    }

}