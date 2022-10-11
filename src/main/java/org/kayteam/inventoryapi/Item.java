package org.kayteam.inventoryapi;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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

    private final HashMap< ClickType , ClickActions > clickActions = new HashMap<>();

    public Item( ItemStack itemStack , String displayName , List<String> lore ) {

        this.itemStack = itemStack;

        this.displayName = displayName;

        this.lore = lore;

    }

    public Item( ItemStack itemStack , String displayName ) {

        this.itemStack = itemStack;

        this.displayName = displayName;

        ItemMeta itemMeta = itemStack.getItemMeta();

        if ( itemMeta.hasLore() ) lore = itemMeta.getLore();

    }

    public Item( ItemStack itemStack ) {

        this.itemStack = itemStack;

        ItemMeta itemMeta = itemStack.getItemMeta();

        if ( itemMeta.hasDisplayName() ) displayName = itemMeta.getDisplayName();

        if ( itemMeta.hasLore() ) lore = itemMeta.getLore();

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
     * Update the ItemStack
     * @param player The player with PlaceholderAPI apply placeholders
     */
    public void updateItem( Player player ) {

        ItemMeta itemMeta = itemStack.getItemMeta();

        if ( ! displayName.equals("") ) {

            String newDisplayName = displayName;

            newDisplayName = PlaceholderAPIUtil.setPlaceholders( player , newDisplayName );

            newDisplayName = ChatColor.translateAlternateColorCodes( '&' , newDisplayName );

            itemMeta.setDisplayName( newDisplayName );

        }

        if ( ! lore.isEmpty() ) {

            List<String> newLore = new ArrayList<>();

            for ( String loreLine : lore ) {

                loreLine = PlaceholderAPIUtil.setPlaceholders( player , loreLine );

                loreLine = ChatColor.translateAlternateColorCodes( '&' , loreLine );

                newLore.add( loreLine );

            }

            itemMeta.setLore( newLore );

        }

        itemStack.setItemMeta( itemMeta );

    }

}