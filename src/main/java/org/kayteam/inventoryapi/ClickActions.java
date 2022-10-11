package org.kayteam.inventoryapi;

import org.bukkit.event.inventory.ClickType;
import org.kayteam.actionapi.Actions;
import org.kayteam.requirementapi.Requirements;

public class ClickActions {

    private final ClickType clickType;
    private Requirements requirements;
    private Actions actions;

    public ClickActions( ClickType clickType ) {
        this.clickType = clickType;
    }

    /**
     * Get the click type
     * @return The click type
     */
    public ClickType getClickType() {
        return clickType;
    }

    /**
     * Get the requirements
     * @return The requirements
     */
    public Requirements getRequirements() {
        return requirements;
    }

    /**
     * Set the requirements
     * @param requirements The requirements
     */
    public void setRequirements( Requirements requirements ) {
        this.requirements = requirements;
    }

    /**
     * Get the actions
     * @return The actions
     */
    public Actions getActions() {
        return actions;
    }

    /**
     * Set the actions
     * @param actions The actions
     */
    public void setActions( Actions actions ) {
        this.actions = actions;
    }

}