package com.tylerkv.application.listitems;

import com.tylerkv.application.baseobjects.ListItem;
import com.tylerkv.application.utilities.ItemDetails;
import com.tylerkv.application.utilities.ListType;

public class ShoppingListItem extends ListItem {
    private int quantity;

    public ShoppingListItem(ItemDetails itemDetails, int quantity) {
        super(itemDetails.getItemName(), itemDetails.getItemDescription(), ListType.SHOPPING);
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
