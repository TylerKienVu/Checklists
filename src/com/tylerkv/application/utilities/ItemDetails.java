package com.tylerkv.application.utilities;

public class ItemDetails {
    private String itemName;
    private String itemDescription;

    public ItemDetails(String itemName, String itemDescription) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }
}
