package org.kayteam.inventoryapi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Slot {

    private final int slot;
    private final List< Item > items;

    public Slot( int slot ) {

        this.slot = slot;

        items = new ArrayList<>();

    }

    /**
     * Get the slot number
     * @return The slot number
     */
    public int getSlot() {

        return slot;

    }

    /**
     * Get the items
     * @return List with items
     */
    public List<Item> getItems() {

        return items;

    }

    /**
     * Add item
     * @param item The item
     */
    public void addItem( Item item ) {

        if ( item.getPriority() == -1 )   item.setPriority( 2147483647 - items.size() );

        items.add( item );

    }

    /**
     * Sort the items with the priority
     */
    public void sortItems() {

        items.sort( Comparator.comparingInt( Item :: getPriority ) );

    }

}
