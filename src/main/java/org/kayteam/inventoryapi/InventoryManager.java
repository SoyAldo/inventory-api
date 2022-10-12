package org.kayteam.inventoryapi;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.plugin.java.JavaPlugin;
import org.kayteam.actionapi.ActionManager;
import org.kayteam.actionapi.Actions;
import org.kayteam.inventoryapi.actions.CloseExpansion;
import org.kayteam.inventoryapi.listeners.InventoryClickListener;
import org.kayteam.inventoryapi.listeners.InventoryCloseListener;
import org.kayteam.inventoryapi.listeners.PlayerQuitListener;
import org.kayteam.inventoryapi.util.InventoryUtil;
import org.kayteam.inventoryapi.util.MinecraftUtil;
import org.kayteam.requirementapi.RequirementManager;
import org.kayteam.requirementapi.Requirements;

import java.util.*;

public class InventoryManager {

    private final HashMap< String , Inventory > registeredInventories = new HashMap<>();
    private final HashMap< UUID , InventoryView > openedInventories = new HashMap<>();
    private final RequirementManager requirementManager;
    private final ActionManager actionManager;
    private final JavaPlugin javaPlugin;

    public InventoryManager( JavaPlugin javaPlugin ) {

        this.javaPlugin = javaPlugin;

        requirementManager = new RequirementManager( javaPlugin );

        actionManager = new ActionManager( javaPlugin );

    }

    /**
     * Get registered inventories
     * @return HashMap with all registered inventories
     */
    public HashMap< String , Inventory > getRegisteredInventories() {

        return registeredInventories;

    }

    /**
     * Get opened inventories views
     * @return HashMap with all opened inventories views
     */
    public HashMap<UUID, InventoryView> getOpenedInventories() {

        return openedInventories;

    }

    /**
     * Get the requirement manager
     * @return The requirement manager
     */
    public RequirementManager getRequirementManager() {

        return requirementManager;

    }

    /**
     * Get the action manager
     * @return The action manager
     */
    public ActionManager getActionManager() {

        return actionManager;

    }

    /**
     * Register the manager
     */
    public void registerManager() {

        requirementManager.registerManager();

        actionManager.registerManager();

        actionManager.addActionExpansion( new CloseExpansion() );

        javaPlugin.getServer().getPluginManager().registerEvents( new InventoryClickListener( this ) , javaPlugin);

        javaPlugin.getServer().getPluginManager().registerEvents( new InventoryCloseListener( this ) , javaPlugin);

        javaPlugin.getServer().getPluginManager().registerEvents( new PlayerQuitListener( this ) , javaPlugin);

    }

    /**
     * Reload the manager
     */
    public void reloadManager() {

        requirementManager.reloadManager();

        actionManager.reloadManager();

    }

    /**
     * Get the java plugin
     * @return The java plugin
     */
    public JavaPlugin getJavaPlugin() {

        return javaPlugin;

    }

    /**
     * Register inventory from FileConfiguration
     * @param name The inventory name
     * @param path The path
     * @param fileConfiguration The FileConfiguration
     */
    public void registerInventory( String name , String path , FileConfiguration fileConfiguration ) {

        if ( ! path.equals( "" ) ) {

            if ( ! fileConfiguration.isConfigurationSection( path ) )   return;

            registerInventory( name , fileConfiguration.getConfigurationSection( path ) );

            return;

        }

        registerInventory( name , fileConfiguration );

    }

    /**
     * Register inventory from FileConfiguration
     * @param name The inventory name
     * @param configurationSection The FileConfiguration
     */
    public void registerInventory( String name , ConfigurationSection configurationSection ) {

        registerInventory( loadInventory( name , configurationSection ) );

    }

    /**
     * Register a new inventory
     * @param inventory The inventory
     */
    public void registerInventory( Inventory inventory ) {

        inventory.setInventoryManager( this );

        registeredInventories.put( inventory.getName() , inventory );

    }

    /**
     * Verify if exist specific inventory
     * @param name The inventory name
     * @return true if exist or false if not
     */
    public boolean existInventory( String name ) {

        return registeredInventories.containsKey( name );

    }

    /**
     * Unregister specific inventory
     * @param name The inventory name
     */
    public void unregisterInventory( String name ) {

        registeredInventories.remove( name );

    }

    /**
     * Get the inventory
     * @param name The inventory name
     * @return The inventory if exist or null if not exist
     */
    public Inventory getInventory( String name ) {

        return registeredInventories.get( name );

    }

    /**
     * Add opened inventory
     * @param inventoryView The opened inventory
     */
    public void addOpenedInventory( InventoryView inventoryView ) {

        Player player = inventoryView.getPlayer();

        UUID uuid = player.getUniqueId();

        openedInventories.put( uuid , inventoryView );

    }

    /**
     * Verify if specific Player contain opened inventory
     * @param player The player
     * @return true if contain opened inventory or false if not
     */
    public boolean hasOpenedInventory( Player player ) {

        UUID uuid = player.getUniqueId();

        return openedInventories.containsKey( uuid );

    }

    /**
     * Verify if specific Player UUID contain opened inventory
     * @param uuid The player UUID
     * @return true if contain opened inventory or false if not
     */
    public boolean hasOpenedInventory( UUID uuid ) {

        return openedInventories.containsKey( uuid );

    }

    /**
     * Remove specific opened inventory by Player
     * @param player The player
     */
    public void removeOpenedInventory( Player player ) {

        UUID uuid = player.getUniqueId();

        openedInventories.remove( uuid );

    }

    /**
     * Remove specific opened inventory by UUID
     * @param uuid The player UUID
     */
    public void removeOpenedInventory( UUID uuid ) {

        openedInventories.remove( uuid );

    }

    /**
     * Get the open inventory by Player
     * @param player The player
     * @return The InventoryView if exist or null if not
     */
    public InventoryView getOpenedInventory( Player player ) {

        UUID uuid = player.getUniqueId();

        return openedInventories.get( uuid );

    }

    /**
     * Get the open inventory by UUID
     * @param uuid The player UUID
     * @return The InventoryView if exist or null if not
     */
    public InventoryView getOpenedInventory( UUID uuid ) {

        return openedInventories.get( uuid );

    }

    public Inventory loadInventory( String name , ConfigurationSection configurationSection ) {

        // Initialize inventory
        Inventory inventory;

        if ( ! configurationSection.contains( "title" ) )   return null;
        if ( ! configurationSection.isString( "title" ) )   return null;

        if ( ! configurationSection.contains( "rows" ) )   return null;
        if ( ! configurationSection.isInt( "rows" ) )   return null;

        // Title
        String title = configurationSection.getString( "title" );

        // Rows
        int rows = configurationSection.getInt( "rows" );

        // Inventory
        inventory = new Inventory( name , title , rows );

        // Update Interval
        if ( ! configurationSection.contains( "updateInterval" ) ) {

            if ( ! configurationSection.isInt( "updateInterval" ) ) {

                inventory.setUpdateInterval( configurationSection.getInt( "updateInterval" ) );

            }

        }

        // Open requirements
        inventory.setOpenRequirements( getRequirements( "openRequirements" , configurationSection ) );

        // Open Actions
        inventory.setOpenActions( getActions( "openActions" , configurationSection ) );

        // Items
        for ( String itemName : configurationSection.getConfigurationSection( "items" ).getKeys( false ) ) {

            if ( ! configurationSection.isConfigurationSection( "items." + itemName ) )   continue;

            ConfigurationSection itemSection = configurationSection.getConfigurationSection( "items." + itemName );

            List<Integer> slots = new ArrayList<>();

            if ( itemSection.contains( "slot" ) ) {

                if ( itemSection.isInt( "slot" ) ) {

                    slots.add( itemSection.getInt( "slot" ) );

                } else if ( itemSection.isString( "slot" ) ) {

                    slots = InventoryUtil.getSlotsFromFormat( itemSection.getString( "slot" ) );

                }

            } else if ( itemSection.contains( "slots" ) ) {

                if ( itemSection.isList( "slots" ) ) {

                    for ( String slotsFormat : itemSection.getStringList( "slots" ) )   slots.addAll( InventoryUtil.getSlotsFromFormat( slotsFormat ) );

                } else if ( itemSection.isString( "slots" ) ) {

                    slots = InventoryUtil.getSlotsFromFormat( itemSection.getString( "slots" ) );

                } else if ( itemSection.isInt( "slots" ) ) {

                    slots.add( itemSection.getInt( "slots" ) );

                }

            }

            if ( ! slots.isEmpty() ) {

                Item item = new Item( MinecraftUtil.getItemStack( itemSection ) );

                if ( itemSection.contains( "priority" ) ) {

                    if ( itemSection.isInt( "priority" ) ) {

                        item.setPriority( itemSection.getInt( "priority" ) );

                    }

                }

                item.setViewRequirements( getRequirements( "viewRequirements" , itemSection ) );

                item.getClickActions().put( ClickType.LEFT , getClickActions( "leftClick", ClickType.LEFT , itemSection ) );

                item.getClickActions().put( ClickType.SHIFT_LEFT , getClickActions( "shiftLeftClick", ClickType.SHIFT_LEFT , itemSection ) );

                item.getClickActions().put( ClickType.MIDDLE , getClickActions( "middleClick", ClickType.MIDDLE , itemSection ) );

                item.getClickActions().put( ClickType.RIGHT , getClickActions( "rightClick", ClickType.RIGHT , itemSection ) );

                item.getClickActions().put( ClickType.SHIFT_RIGHT , getClickActions( "shiftRightClick", ClickType.LEFT , itemSection ) );

                for ( Integer slotNumber : slots ) {

                    Slot slot = inventory.getSlots().get( slotNumber );

                    slot.addItem( item );

                }

            }

        }

        // Return the inventory
        return inventory;

    }

    private Requirements getRequirements( String name , ConfigurationSection configurationSection ) {

        if ( ! configurationSection.contains( name ) )   return new Requirements();

        if ( ! configurationSection.isConfigurationSection( name ) )   return new Requirements();

        return requirementManager.loadRequirements( configurationSection.getConfigurationSection( name ) );

    }

    private Actions getActions( String name , ConfigurationSection configurationSection ) {

        if ( ! configurationSection.contains( name ) )   return new Actions();

        if ( ! configurationSection.isList( name ) )   return new Actions();

        return actionManager.loadActions( configurationSection.getStringList( name ) );

    }

    private ClickActions getClickActions( String type , ClickType clickType , ConfigurationSection configurationSection ) {

        ClickActions clickActions = new ClickActions( clickType );

        clickActions.setRequirements( getRequirements( type + "Requirements" , configurationSection ) );

        clickActions.setActions( getActions( type + "Actions" , configurationSection ) );

        return clickActions;

    }

}