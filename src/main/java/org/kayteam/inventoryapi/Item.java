package org.kayteam.inventoryapi;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.kayteam.inventoryapi.events.ItemUpdateEvent;
import org.kayteam.inventoryapi.util.PlaceholderAPIUtil;
import org.kayteam.requirementapi.Requirements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Item {

    private ItemStack itemStack;
    private int priority;
    private boolean update;
    private String displayName;
    private List<String> lore;
    private Requirements viewRequirements;
    private HashMap< ClickType , ClickActions > clickActions = new HashMap<>();
    private HashMap< String , Object > data = new HashMap<>();

    public Item( ItemStack itemStack , String displayName , List<String> lore ) {

        priority = -1;

        update = false;

        this.itemStack = itemStack;

        if ( this.itemStack == null )   this.itemStack = new ItemStack( Material.AIR );

        this.displayName = displayName;

        this.lore = lore;

    }

    public Item( ItemStack itemStack , String displayName ) {

        priority = -1;

        update = false;

        this.itemStack = itemStack;

        if ( this.itemStack == null ) {

            this.itemStack = new ItemStack( Material.AIR );

            this.displayName = displayName;

            lore = new ArrayList<>();

        } else {

            ItemMeta itemMeta = itemStack.getItemMeta();

            if ( itemMeta != null ) {

                this.displayName = displayName;

                if ( itemMeta.hasLore() ) lore = itemMeta.getLore();

            }

        }

    }

    public Item( Item item ) {

        itemStack = item.getItemStack();

        priority = item.getPriority();

        update = item.isUpdate();

        displayName = item.getDisplayName();

        lore = item.getLore();

        viewRequirements = item.getViewRequirements();

        clickActions = item.getClickActions();

        data = item.getData();

        if ( this.itemStack == null ) {

            this.itemStack = new ItemStack( Material.AIR );

            this.displayName = item.getDisplayName();

            lore = new ArrayList<>();

        } else {

            ItemMeta itemMeta = itemStack.getItemMeta();

            if ( itemMeta != null ) {

                this.displayName = item.getDisplayName();

                if ( itemMeta.hasLore() ) lore = itemMeta.getLore();

            }

        }

    }

    public Item( ItemStack itemStack ) {

        priority = -1;

        update = false;

        this.itemStack = itemStack;

        if ( this.itemStack == null ) {

            this.itemStack = new ItemStack( Material.AIR );

            displayName = "";

            lore = new ArrayList<>();

        } else {

            ItemMeta itemMeta = itemStack.getItemMeta();

            if ( itemMeta != null ) {

                if ( itemMeta.hasDisplayName() ) displayName = itemMeta.getDisplayName();

                if ( itemMeta.hasLore() ) lore = itemMeta.getLore();

            }

        }

    }

    /**
     * Get the ItemStack
     * @return The ItemStack
     */
    public ItemStack getItemStack() {

        return itemStack;

    }

    /**
     * Set the new ItemStack
     * @param itemStack The new ItemStack
     */
    public void setItemStack( ItemStack itemStack ) {

        this.itemStack = itemStack;

        ItemMeta itemMeta = itemStack.getItemMeta();

        if ( itemMeta.hasDisplayName() ) displayName = itemMeta.getDisplayName();

        if ( itemMeta.hasLore() ) lore = itemMeta.getLore();

    }

    /**
     * Get the priority
     * @return The priority
     */
    public int getPriority() {

        return priority;

    }

    /**
     * Set the new priority
     * @param priority The new priority
     */
    public void setPriority( int priority ) {

        this.priority = priority;

    }

    /**
     * Verify if the item can update
     * @return true if item can update or false if not
     */
    public boolean isUpdate() {

        return update;

    }

    /**
     * Set item updatable
     * @param update The new value
     */
    public void setUpdate( boolean update ) {

        this.update = update;

    }

    /**
     * Get the display name
     * @return The display name
     */
    public String getDisplayName() {

        return displayName;

    }

    /**
     * Set the new display name
     * @param displayName The new display name
     */
    public void setDisplayName( String displayName ) {

        this.displayName = displayName;

    }

    /**
     * Get the lore
     * @return The lore
     */
    public List<String> getLore() {

        return lore;

    }

    /**
     * Set the new lore
     * @param lore The new lore
     */
    public void setLore( List<String> lore ) {

        this.lore = lore;

    }

    /**
     * Get the view requirements
     * @return The view requirements
     */
    public Requirements getViewRequirements() {

        return viewRequirements;

    }

    /**
     * Set the new view requirements
     * @param viewRequirements The new view requirements
     */
    public void setViewRequirements( Requirements viewRequirements ) {

        this.viewRequirements = viewRequirements;

    }

    /**
     * Get the click actions
     * @return The Map with the actions
     */
    public HashMap< ClickType , ClickActions > getClickActions() {

        return clickActions;

    }

    /**
     * Set the new click actions
     * @param clickActions The new click actions
     */
    public void setClickActions( HashMap< ClickType , ClickActions > clickActions ) {

        this.clickActions = clickActions;

    }

    /**
     * Get data
     * @return The data
     */
    public HashMap< String , Object > getData() {

        return data;

    }

    /**
     * Set the new data
     * @param data The new data
     */
    public void setData( HashMap< String , Object > data ) {

        this.data = data;

    }

    /**
     * Update the ItemStack
     * @param player The player with PlaceholderAPI apply placeholders
     */
    public void updateItem( InventoryManager inventoryManager , InventoryView inventoryView , Player player ) {

        ItemMeta itemMeta = itemStack.getItemMeta();

        ItemUpdateEvent itemUpdateEvent = new ItemUpdateEvent( inventoryManager , inventoryView , this );

        inventoryManager.getJavaPlugin().getServer().getPluginManager().callEvent( itemUpdateEvent );

        if ( itemUpdateEvent.isCancelled() )   return;

        if ( ! displayName.equals("") ) {

            String newDisplayName = displayName;

            for ( String replacement : itemUpdateEvent.getReplacements().keySet() ) {

                newDisplayName = newDisplayName.replaceAll( replacement , itemUpdateEvent.getReplacements().get( replacement ) );

            }

            newDisplayName = PlaceholderAPIUtil.setPlaceholders( player , newDisplayName );

            newDisplayName = ChatColor.translateAlternateColorCodes( '&' , newDisplayName );

            itemMeta.setDisplayName( newDisplayName );

        }

        if ( ! lore.isEmpty() ) {

            List<String> newLore = new ArrayList<>();

            for ( String loreLine : lore ) {

                for ( String replacement : itemUpdateEvent.getReplacements().keySet() ) {

                    loreLine = loreLine.replaceAll( replacement , itemUpdateEvent.getReplacements().get( replacement ) );

                }

                loreLine = PlaceholderAPIUtil.setPlaceholders( player , loreLine );

                loreLine = ChatColor.translateAlternateColorCodes( '&' , loreLine );

                newLore.add( loreLine );

            }

            itemMeta.setLore( newLore );

        }

        itemStack.setItemMeta( itemMeta );

    }

}