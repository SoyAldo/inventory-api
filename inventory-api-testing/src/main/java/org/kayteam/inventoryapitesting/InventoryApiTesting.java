package org.kayteam.inventoryapitesting;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import org.kayteam.inventoryapi.InventoryManager;
import org.kayteam.inventoryapitesting.commands.InventoryApiTestingCommand;

import java.io.File;

public final class InventoryApiTesting extends JavaPlugin {

    private FileConfiguration config;
    private final InventoryManager inventoryManager = new InventoryManager( this );

    @Override
    public void onEnable() {

        registerFiles();

        registerManagers();

        registerCommands();

    }

    @Override
    public void onDisable() {

    }

    public void onReload() {

        registerFiles();

    }

    private void registerFiles() {

        File file = new File( getDataFolder() , "config.yml");

        if ( ! file.exists() ) {

            saveResource("config.yml", true );

        }

        config = YamlConfiguration.loadConfiguration( file );

    }

    private void registerManagers() {

        inventoryManager.registerManager();

    }

    private void registerCommands() {

        getCommand( "InventoryApiTesting" ).setExecutor( new InventoryApiTestingCommand( this ) );

        getCommand( "InventoryApiTesting" ).setTabCompleter( new InventoryApiTestingCommand( this ) );

    }

    public InventoryManager getInventoryManager() {

        return inventoryManager;

    }

    @Override
    public FileConfiguration getConfig() {
        return config;
    }

}