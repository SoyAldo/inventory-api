package org.kayteam.inventoryapi.actions;

import org.kayteam.actionapi.Action;
import org.kayteam.actionapi.ActionExpansion;
import org.kayteam.actionapi.util.ActionUtil;
import org.kayteam.inventoryapi.InventoryManager;

public class OpenGuiExpansion extends ActionExpansion {

    private final InventoryManager inventoryManager;

    public OpenGuiExpansion(InventoryManager inventoryManager ) {
        super( "opengui" );
        this.inventoryManager = inventoryManager;
    }

    @Override
    public Action generateAction( String format ) {

        String value = ActionUtil.getValue( format );

        return new OpenGuiAction( inventoryManager , value );

    }

}