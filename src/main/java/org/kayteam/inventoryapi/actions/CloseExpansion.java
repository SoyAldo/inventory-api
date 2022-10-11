package org.kayteam.inventoryapi.actions;

import org.kayteam.actionapi.Action;
import org.kayteam.actionapi.ActionExpansion;

public class CloseExpansion extends ActionExpansion {


    public CloseExpansion() {

        super( "close" );

    }

    @Override
    public Action generateAction( String format ) {

        return new CloseAction();

    }

}