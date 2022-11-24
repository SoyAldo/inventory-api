package org.kayteam.inventoryapi.actions;

import org.bukkit.entity.Player;
import org.kayteam.actionapi.Action;
import org.kayteam.inventoryapi.InventoryManager;
import org.kayteam.inventoryapi.InventoryView;

public class OpenGuiAction extends Action {

    private final InventoryManager inventoryManager;

    protected OpenGuiAction(InventoryManager inventoryManager , String gui ) {

        super( "opengui" , gui );

        this.inventoryManager = inventoryManager;

    }

    @Override
    public void execute( Player player ) {

        if ( ! inventoryManager.existInventory( getValue() ) ) return;

        InventoryView inventoryView = inventoryManager.getInventory( getValue() ).generateView( player );

        inventoryView.openInventory();

    }

}