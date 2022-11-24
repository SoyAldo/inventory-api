package org.kayteam.inventoryapi.actions;

import org.bukkit.entity.Player;
import org.kayteam.actionapi.Action;

public class CloseAction extends Action {

    protected CloseAction() {

        super( "close" , "" );

    }

    @Override
    public void execute( Player player ) {

        player.closeInventory();

    }

}