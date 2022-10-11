package org.kayteam.inventoryapitesting.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.kayteam.inventoryapi.Inventory;
import org.kayteam.inventoryapi.InventoryManager;
import org.kayteam.inventoryapi.InventoryView;
import org.kayteam.inventoryapitesting.InventoryApiTesting;

import java.util.*;

public class InventoryApiTestingCommand implements CommandExecutor , TabCompleter {

    private final InventoryApiTesting inventoryApiTesting;

    public InventoryApiTestingCommand( InventoryApiTesting inventoryApiTesting ) {

        this.inventoryApiTesting = inventoryApiTesting;

    }

    @Override
    public boolean onCommand( CommandSender sender , Command command , String label , String[] args ) {

        FileConfiguration config = inventoryApiTesting.getConfig();

        if ( ! ( sender instanceof Player) ) {

            sender.sendMessage( ChatColor.translateAlternateColorCodes( '&' , config.getString( "messages.onlyPlayers" ) ) );

            return true;

        }

        Player player = ( Player ) sender;

        if ( args.length < 1 ) {

            sender.sendMessage( ChatColor.translateAlternateColorCodes( '&' , config.getString( "messages.emptyArgs" ) ) );

            return true;

        }

        String subcommand = args[0];

        if ( subcommand.equalsIgnoreCase( "reload" ) ) {

            inventoryApiTesting.onReload();

            sender.sendMessage( ChatColor.translateAlternateColorCodes( '&' , config.getString( "messages.reloaded" ) ) );

        } else if ( subcommand.equalsIgnoreCase( "open" ) ) {

            InventoryManager inventoryManager = inventoryApiTesting.getInventoryManager();

            if ( args.length < 2 ) {

                sender.sendMessage( ChatColor.translateAlternateColorCodes( '&' , config.getString( "messages.emptyInventoryName" ) ) );

                return true;

            }

            String inventoryName = args[1];

            if ( ! inventoryManager.existInventory( inventoryName ) ) {

                sender.sendMessage( ChatColor.translateAlternateColorCodes( '&' , config.getString( "messages.invalidInventoryName" ) ) );

                return true;

            }

            Inventory inventory = inventoryManager.getInventory( inventoryName );

            InventoryView inventoryView = inventory.generateView( player );

            inventoryView.openInventory();

        } else if ( subcommand.equalsIgnoreCase( "register" ) ) {

            InventoryManager inventoryManager = inventoryApiTesting.getInventoryManager();

            if ( args.length < 2 ) {

                sender.sendMessage( ChatColor.translateAlternateColorCodes( '&' , config.getString( "messages.emptyInventoryName" ) ) );
                return true;

            }

            String inventoryName = args[1];

            if ( ! config.contains( "inventories." + inventoryName ) || ! config.isConfigurationSection( "inventories." + inventoryName )) {

                sender.sendMessage( ChatColor.translateAlternateColorCodes( '&' , config.getString( "messages.invalidInventoryName" ) ) );
                return true;

            }

            inventoryManager.registerInventory( inventoryName , config.getConfigurationSection( "inventories." + inventoryName ) );

            sender.sendMessage( ChatColor.translateAlternateColorCodes( '&' , config.getString( "messages.inventoryRegistered" ) ) );

        } else if ( subcommand.equalsIgnoreCase( "unregister" ) ) {

            InventoryManager inventoryManager = inventoryApiTesting.getInventoryManager();

            if ( args.length < 2 ) {

                sender.sendMessage( ChatColor.translateAlternateColorCodes( '&' , config.getString( "messages.emptyInventoryName" ) ) );

                return true;

            }

            String inventoryName = args[1];

            if ( ! inventoryManager.existInventory( inventoryName ) ) {

                sender.sendMessage( ChatColor.translateAlternateColorCodes( '&' , config.getString( "messages.invalidInventoryName" ) ) );
                return true;

            }

            inventoryManager.unregisterInventory( inventoryName );

            sender.sendMessage( ChatColor.translateAlternateColorCodes( '&' , config.getString( "messages.inventoryUnregistered" ) ) );

        }

        return true;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender , Command command , String alias , String[] args ) {

        if ( args.length == 1 ) {

            return Arrays.asList( "reload" , "open" , "register" , "unregister" );

        }

        if ( args.length == 2 ) {

            if ( args[0].equalsIgnoreCase( "open" ) ) return new ArrayList<>( inventoryApiTesting.getInventoryManager().getRegisteredInventories().keySet() );

            if ( args[0].equalsIgnoreCase( "register" ) ) return new ArrayList<>( inventoryApiTesting.getConfig().getConfigurationSection( "inventories" ).getKeys( false ) );

            if ( args[0].equalsIgnoreCase( "unregister" ) ) return new ArrayList<>( inventoryApiTesting.getInventoryManager().getRegisteredInventories().keySet() );

        }

        return null;

    }

}