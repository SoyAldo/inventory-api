package org.kayteam.inventoryapi;

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
import org.kayteam.inventoryapi.listeners.PlayerJoinListener;
import org.kayteam.inventoryapi.listeners.PlayerQuitListener;
import org.kayteam.inventoryapi.pagination.Pagination;
import org.kayteam.inventoryapi.pagination.paginations.OfflinePlayersPagination;
import org.kayteam.inventoryapi.pagination.paginations.PlayersPagination;
import org.kayteam.inventoryapi.util.InventoryUtil;
import org.kayteam.inventoryapi.util.MinecraftUtil;
import org.kayteam.requirementapi.RequirementManager;
import org.kayteam.requirementapi.Requirements;

import java.util.*;

public class InventoryManager {

    private final HashMap< String , Inventory > registeredInventories = new HashMap<>();
    private final HashMap< UUID , InventoryView > openedInventories = new HashMap<>();
    private final HashMap< String , Pagination > paginations = new HashMap<>();
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
     * Get paginations
     * @return All paginations map
     */
    public HashMap<String, Pagination> getPaginations() {

        return paginations;

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

        // Managers
        requirementManager.registerManager();
        actionManager.registerManager();

        // Actions
        actionManager.addActionExpansion( new CloseExpansion() );

        // Listeners
        javaPlugin.getServer().getPluginManager().registerEvents( new InventoryClickListener( this ) , javaPlugin);
        javaPlugin.getServer().getPluginManager().registerEvents( new InventoryCloseListener( this ) , javaPlugin);
        javaPlugin.getServer().getPluginManager().registerEvents( new PlayerJoinListener( this ) , javaPlugin);
        javaPlugin.getServer().getPluginManager().registerEvents( new PlayerQuitListener( this ) , javaPlugin);

        // Paginations
        paginations.put( "players" , new PlayersPagination( this ) );
        paginations.put( "offlinePlayers" , new OfflinePlayersPagination( this ) );

    }

    /**
     * Reload the manager
     */
    public void reloadManager() {

        // Managers
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

    /**
     * Verify if specific pagination exist
     * @param name The pagination name
     * @return true if exist or false if not
     */
    public boolean existPagination( String name ) {

        return paginations.containsKey( name );

    }

    /**
     * Add new pagination
     * @param pagination The new pagination
     */
    public void addPagination( Pagination pagination ) {

        paginations.put( pagination.getName() , pagination );

    }

    /**
     * Remove specific pagination
     * @param name The pagination name
     */
    public void removePagination( String name ) {

        paginations.remove( name );

    }

    /**
     * Get specific pagination
     * @param name The pagination name
     * @return The pagination if exist or null if not exist
     */
    public Pagination getPagination( String name ) {

        return paginations.get( name );

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
        if ( configurationSection.contains( "updateInterval" ) ) {

            if ( configurationSection.isInt( "updateInterval" ) ) {

                inventory.setUpdateInterval( configurationSection.getInt( "updateInterval" ) );

            }

        }

        // Open requirements
        inventory.setOpenRequirements( getRequirements( "openRequirements" , configurationSection ) );

        // Open Actions
        inventory.setOpenActions( getActions( "openActions" , configurationSection ) );

        // Pagination
        if ( configurationSection.contains( "paginated" ) ) {

            if ( configurationSection.isBoolean( "paginated" ) ) {

                inventory.setPaginated( configurationSection.getBoolean( "paginated" ) );

            }

        }

        if ( configurationSection.contains( "paginationInfo.slots" ) ) {

            if ( configurationSection.isList( "paginationInfo.slots" ) ) {

                inventory.setPaginationSlots( InventoryUtil.getSlotsFromFormats( configurationSection.getStringList( "paginationInfo.slots" ) ) );

            }

        }

        if ( configurationSection.contains( "paginationInfo.type" ) ) {

            if ( configurationSection.isString( "paginationInfo.type" ) ) {

                inventory.setPaginationType( configurationSection.getString( "paginationInfo.type" ) );

            }

        }

        if ( configurationSection.contains( "paginationInfo.existItem" ) ) {

            if ( configurationSection.isConfigurationSection( "paginationInfo.existItem" ) ) {

                ConfigurationSection existItemSection = configurationSection.getConfigurationSection( "paginationInfo.emptyItem" );

                Item item = new Item( MinecraftUtil.getItemStack( existItemSection ) );

                item.setViewRequirements( getRequirements( "viewRequirements" , existItemSection ) );

                item.getClickActions().put( ClickType.LEFT , getClickActions( "leftClick", ClickType.LEFT , existItemSection ) );

                item.getClickActions().put( ClickType.SHIFT_LEFT , getClickActions( "shiftLeftClick", ClickType.SHIFT_LEFT , existItemSection ) );

                item.getClickActions().put( ClickType.MIDDLE , getClickActions( "middleClick", ClickType.MIDDLE , existItemSection ) );

                item.getClickActions().put( ClickType.RIGHT , getClickActions( "rightClick", ClickType.RIGHT , existItemSection ) );

                item.getClickActions().put( ClickType.SHIFT_RIGHT , getClickActions( "shiftRightClick", ClickType.LEFT , existItemSection ) );

                inventory.setPaginationItemExist( item );

            }

        }

        if ( configurationSection.contains( "paginationInfo.emptyItem" ) ) {

            if ( configurationSection.isConfigurationSection( "paginationInfo.emptyItem" ) ) {

                ConfigurationSection emptyItemSection = configurationSection.getConfigurationSection( "paginationInfo.emptyItem" );

                Item item = new Item( MinecraftUtil.getItemStack( emptyItemSection ) );

                item.setViewRequirements( getRequirements( "viewRequirements" , emptyItemSection ) );

                item.getClickActions().put( ClickType.LEFT , getClickActions( "leftClick", ClickType.LEFT , emptyItemSection ) );

                item.getClickActions().put( ClickType.SHIFT_LEFT , getClickActions( "shiftLeftClick", ClickType.SHIFT_LEFT , emptyItemSection ) );

                item.getClickActions().put( ClickType.MIDDLE , getClickActions( "middleClick", ClickType.MIDDLE , emptyItemSection ) );

                item.getClickActions().put( ClickType.RIGHT , getClickActions( "rightClick", ClickType.RIGHT , emptyItemSection ) );

                item.getClickActions().put( ClickType.SHIFT_RIGHT , getClickActions( "shiftRightClick", ClickType.LEFT , emptyItemSection ) );

                inventory.setPaginationItemEmpty( item );

            }

        }

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

                    slots = InventoryUtil.getSlotsFromFormats( itemSection.getStringList( "slots" ) );

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