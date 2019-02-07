package com.tylerkv.application.listitems;

import com.tylerkv.application.baseobjects.ListItem;
import com.tylerkv.application.utilities.ListType;

public class ShoppingListItem extends ListItem {
    private int quantity;

    public ShoppingListItem(String itemName, String description, int quantity) {
        super(itemName, description, ListType.SHOPPING);
        this.setQuantity(quantity);
    }

    // GETTERS AND SETTERS

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
