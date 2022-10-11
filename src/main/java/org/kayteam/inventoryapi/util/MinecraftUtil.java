package org.kayteam.inventoryapi.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MinecraftUtil {

    public static ItemStack getItemStack( String path , FileConfiguration fileConfiguration ) {

        ItemStack result = new ItemStack( Material.AIR );
        Material material;
        int amount;
        short durability;
        String displayName;
        List<String> lore;

        // Material
        if ( fileConfiguration.contains( path + ".material" ) ) {
            if ( fileConfiguration.isString( path + ".material" ) ) {
                String materialString = fileConfiguration.getString( path + ".material" );
                if ( materialString.startsWith( "basehead-" ) ) {
                    material = Material.PLAYER_HEAD;
                    result = new ItemStack( material );
                    String base64 = materialString.replaceFirst("basehead-", "");

                    SkullMeta skullMeta = ( SkullMeta ) result.getItemMeta();
                    GameProfile profile = new GameProfile( UUID.randomUUID() , null );

                    profile.getProperties().put( "textures" , new Property("textures", base64 ) );

                    try {
                        Method method = skullMeta.getClass().getDeclaredMethod( "setProfile" , GameProfile.class );
                        method.setAccessible( true );
                        method.invoke( skullMeta , profile );
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignore ) { }
                } else {
                    try {
                        material = Material.valueOf( materialString );
                        result = new ItemStack( material );
                    } catch (IllegalArgumentException ignore) {}
                }
            }
        }

        // Durability
        if ( fileConfiguration.contains( path + ".durability" ) ) {
            if ( fileConfiguration.isInt(path + ".durability" ) ) {
                int newDurability = fileConfiguration.getInt( path + ".durability" );
                if ( newDurability > 0 ) {
                    durability = (short) newDurability;
                    result.setDurability( durability );
                }
            }
        }

        // Amount
        if ( fileConfiguration.contains( path + ".amount" ) ) {
            if ( fileConfiguration.isInt( path + ".amount" ) ) {
                int newAmount = fileConfiguration.getInt( path + ".amount" );
                if ( newAmount > 1 ) {
                    amount = newAmount;
                    result.setAmount( amount );
                }
            }
        }

        // DisplayName
        if ( fileConfiguration.contains( path + ".display_name" ) ) {
            if ( fileConfiguration.isString( path + ".display_name" ) ) {
                String newDisplayName = fileConfiguration.getString( path + ".display_name" );
                if ( ! newDisplayName.equals( "" ) ) {
                    displayName = newDisplayName;
                    displayName = ChatColor.translateAlternateColorCodes( '&' , displayName );
                    ItemMeta itemMeta = result.getItemMeta();
                    itemMeta.setDisplayName( displayName );
                    result.setItemMeta( itemMeta );
                }
            }
        }

        // Lore
        if ( fileConfiguration.contains( path + ".lore" ) ) {
            if ( fileConfiguration.isList( path + ".lore" ) ) {
                List<String> newLore = fileConfiguration.getStringList( path + ".lore" );
                if ( ! newLore.isEmpty() ) {
                    lore = newLore;
                    lore.replaceAll(textToTranslate -> ChatColor.translateAlternateColorCodes('&', textToTranslate));
                    ItemMeta itemMeta = result.getItemMeta();
                    itemMeta.setLore( lore );
                    result.setItemMeta( itemMeta );
                }
            }
        }

        // ItemFlag
        if ( fileConfiguration.contains( path + ".flags" ) ) {
            if ( fileConfiguration.isList( path + ".flags" ) ) {
                List<String> flagNames = fileConfiguration.getStringList( path + ".flags" );
                for (String flagName:flagNames) {
                    try {
                        ItemFlag itemFlag = ItemFlag.valueOf( flagName );
                        ItemMeta itemMeta = result.getItemMeta();
                        itemMeta.addItemFlags( itemFlag );
                        result.setItemMeta( itemMeta );
                    } catch (IllegalArgumentException ignore) {}
                }
            }
        }

        // Enchantments
        if ( fileConfiguration.contains( path + ".enchantments" ) ) {
            if ( fileConfiguration.isConfigurationSection( path + ".enchantments" ) ) {
                Set<String> enchantmentNames = fileConfiguration.getConfigurationSection( path + ".enchantments" ).getKeys( false );
                for (String enchantmentName:enchantmentNames) {
                    Enchantment enchantment = Enchantment.getByName(enchantmentName);
                    if (enchantment != null) {
                        if ( fileConfiguration.isInt( path + ".enchantments." + enchantmentName ) ) {
                            int enchantmentLevel = fileConfiguration.getInt( path + ".enchantments." + enchantmentName );
                            result.addUnsafeEnchantment( enchantment , enchantmentLevel );
                        }
                    }
                }
            }
        }

        return result;

    }

    public static ItemStack getItemStack( String path , ConfigurationSection configurationSection ) {

        ItemStack result = new ItemStack( Material.AIR );
        Material material;
        int amount;
        short durability;
        String displayName;
        List<String> lore;

        // Material
        if ( configurationSection.contains( path + ".material" ) ) {
            if ( configurationSection.isString( path + ".material" ) ) {
                String materialString = configurationSection.getString( path + ".material" );
                if ( materialString.startsWith( "basehead-" ) ) {
                    material = Material.PLAYER_HEAD;
                    result = new ItemStack( material );
                    String base64 = materialString.replaceFirst("basehead-", "");

                    SkullMeta skullMeta = ( SkullMeta ) result.getItemMeta();
                    GameProfile profile = new GameProfile( UUID.randomUUID() , null );

                    profile.getProperties().put( "textures" , new Property("textures", base64 ) );

                    try {
                        Method method = skullMeta.getClass().getDeclaredMethod( "setProfile" , GameProfile.class );
                        method.setAccessible( true );
                        method.invoke( skullMeta , profile );
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignore ) { }
                } else {
                    try {
                        material = Material.valueOf( materialString );
                        result = new ItemStack( material );
                    } catch (IllegalArgumentException ignore) {}
                }
            }
        }

        // Durability
        if ( configurationSection.contains( path + ".durability" ) ) {
            if ( configurationSection.isInt(path + ".durability" ) ) {
                int newDurability = configurationSection.getInt( path + ".durability" );
                if ( newDurability > 0 ) {
                    durability = (short) newDurability;
                    result.setDurability( durability );
                }
            }
        }

        // Amount
        if ( configurationSection.contains( path + ".amount" ) ) {
            if ( configurationSection.isInt( path + ".amount" ) ) {
                int newAmount = configurationSection.getInt( path + ".amount" );
                if ( newAmount > 1 ) {
                    amount = newAmount;
                    result.setAmount( amount );
                }
            }
        }

        // DisplayName
        if ( configurationSection.contains( path + ".display_name" ) ) {
            if ( configurationSection.isString( path + ".display_name" ) ) {
                String newDisplayName = configurationSection.getString( path + ".display_name" );
                if ( ! newDisplayName.equals( "" ) ) {
                    displayName = newDisplayName;
                    displayName = ChatColor.translateAlternateColorCodes( '&' , displayName );
                    ItemMeta itemMeta = result.getItemMeta();
                    itemMeta.setDisplayName( displayName );
                    result.setItemMeta( itemMeta );
                }
            }
        }

        // Lore
        if ( configurationSection.contains( path + ".lore" ) ) {
            if ( configurationSection.isList( path + ".lore" ) ) {
                List<String> newLore = configurationSection.getStringList( path + ".lore" );
                if ( ! newLore.isEmpty() ) {
                    lore = newLore;
                    lore.replaceAll(textToTranslate -> ChatColor.translateAlternateColorCodes('&', textToTranslate));
                    ItemMeta itemMeta = result.getItemMeta();
                    itemMeta.setLore( lore );
                    result.setItemMeta( itemMeta );
                }
            }
        }

        // ItemFlag
        if ( configurationSection.contains( path + ".flags" ) ) {
            if ( configurationSection.isList( path + ".flags" ) ) {
                List<String> flagNames = configurationSection.getStringList( path + ".flags" );
                for (String flagName:flagNames) {
                    try {
                        ItemFlag itemFlag = ItemFlag.valueOf( flagName );
                        ItemMeta itemMeta = result.getItemMeta();
                        itemMeta.addItemFlags( itemFlag );
                        result.setItemMeta( itemMeta );
                    } catch (IllegalArgumentException ignore) {}
                }
            }
        }

        // Enchantments
        if ( configurationSection.contains( path + ".enchantments" ) ) {
            if ( configurationSection.isConfigurationSection( path + ".enchantments" ) ) {
                Set<String> enchantmentNames = configurationSection.getConfigurationSection( path + ".enchantments" ).getKeys( false );
                for (String enchantmentName:enchantmentNames) {
                    Enchantment enchantment = Enchantment.getByName(enchantmentName);
                    if (enchantment != null) {
                        if ( configurationSection.isInt( path + ".enchantments." + enchantmentName ) ) {
                            int enchantmentLevel = configurationSection.getInt( path + ".enchantments." + enchantmentName );
                            result.addUnsafeEnchantment( enchantment , enchantmentLevel );
                        }
                    }
                }
            }
        }

        return result;

    }

    public static ItemStack getItemStack( ConfigurationSection configurationSection ) {

        ItemStack result = new ItemStack( Material.AIR );
        Material material;
        int amount;
        short durability;
        String displayName;
        List<String> lore;

        // Material
        if ( configurationSection.contains( "material" ) ) {

            if ( configurationSection.isString( "material" ) ) {

                String materialString = configurationSection.getString( "material" );

                if ( materialString.startsWith( "basehead-" ) ) {

                    String base64 = materialString.replaceFirst("basehead-", "");

                    result = getBase64Skull( base64 );

                } else {

                    try {

                        material = Material.valueOf( materialString );

                        result = new ItemStack( material );

                    } catch ( IllegalArgumentException ignore ) { }

                }

            }

        }

        // Durability
        if ( configurationSection.contains( "durability" ) ) {

            if ( configurationSection.isInt("durability" ) ) {

                int newDurability = configurationSection.getInt( "durability" );

                if ( newDurability > 0 ) {

                    durability = (short) newDurability;

                    result.setDurability( durability );

                }

            }

        }

        // Amount
        if ( configurationSection.contains( "amount" ) ) {

            if ( configurationSection.isInt( "amount" ) ) {

                int newAmount = configurationSection.getInt( "amount" );

                if ( newAmount > 1 ) {

                    amount = newAmount;

                    result.setAmount( amount );

                }

            }

        }

        // DisplayName
        if ( configurationSection.contains( "displayName" ) ) {

            if ( configurationSection.isString( "displayName" ) ) {

                String newDisplayName = configurationSection.getString( "displayName" );

                if ( ! newDisplayName.equals( "" ) ) {

                    displayName = newDisplayName;

                    displayName = ChatColor.translateAlternateColorCodes( '&' , displayName );

                    ItemMeta itemMeta = result.getItemMeta();

                    itemMeta.setDisplayName( displayName );

                    result.setItemMeta( itemMeta );

                }

            }

        }

        // Lore
        if ( configurationSection.contains( "lore" ) ) {

            if ( configurationSection.isList( "lore" ) ) {

                List<String> newLore = configurationSection.getStringList( "lore" );

                if ( ! newLore.isEmpty() ) {

                    lore = newLore;

                    lore.replaceAll( textToTranslate -> ChatColor.translateAlternateColorCodes( '&' , textToTranslate ) );

                    ItemMeta itemMeta = result.getItemMeta();

                    itemMeta.setLore( lore );

                    result.setItemMeta( itemMeta );

                }

            }

        }

        // ItemFlag
        if ( configurationSection.contains( "flags" ) ) {

            if ( configurationSection.isList( "flags" ) ) {

                List<String> flagNames = configurationSection.getStringList( "flags" );

                for (String flagName:flagNames) {

                    try {

                        ItemFlag itemFlag = ItemFlag.valueOf( flagName );

                        ItemMeta itemMeta = result.getItemMeta();

                        itemMeta.addItemFlags( itemFlag );

                        result.setItemMeta( itemMeta );

                    } catch ( IllegalArgumentException ignore ) { }

                }

            }

        }

        // Enchantments
        if ( configurationSection.contains( "enchantments" ) ) {

            if ( configurationSection.isConfigurationSection( "enchantments" ) ) {

                Set<String> enchantmentNames = configurationSection.getConfigurationSection( "enchantments" ).getKeys( false );

                for ( String enchantmentName : enchantmentNames ) {

                    Enchantment enchantment = Enchantment.getByName(enchantmentName);

                    if (enchantment == null)   continue;

                    if ( ! configurationSection.isInt( "enchantments." + enchantmentName ) )   continue;

                    result.addUnsafeEnchantment( enchantment , configurationSection.getInt( "enchantments." + enchantmentName ) );

                }

            }

        }

        return result;

    }

    public static ItemStack getBase64Skull( String base64 ) {

        ItemStack itemStack = new ItemStack( Material.PLAYER_HEAD );

        SkullMeta skullMeta = ( SkullMeta ) itemStack.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put( "textures" , new Property("textures" , base64 ) );

        try {

            Method method = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);

            method.setAccessible(true);

            method.invoke( skullMeta , profile );

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignore) { }

        itemStack.setItemMeta( skullMeta );

        return itemStack;

    }

}