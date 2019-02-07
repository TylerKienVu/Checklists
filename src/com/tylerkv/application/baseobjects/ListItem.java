package com.tylerkv.application.baseobjects;

import com.tylerkv.application.utilities.Status;
import com.tylerkv.application.utilities.ListType;

import java.time.LocalDateTime;

//TODO: Implement isMutable functionality. This functionality has high coupling so trying to think of better way

public abstract class ListItem {
    private ListType listItemType;
    private Status status;
    private String itemName;
    private String description;
    private Boolean isMutable;
    private LocalDateTime itemCreated;
    private LocalDateTime completedTime;

    public ListItem(String itemName, String description, ListType listItemType) {
        this.setItemName(itemName);
        this.setDescription(description);
        this.setListItemType(listItemType);
        this.setStatus(Status.INCOMPLETE);
        this.setItemCreated(LocalDateTime.now());
        this.setIsMutable(true);
    }

    // GETTERS AND SETTERS

    public ListType getListItemType() {
        return this.listItemType;
    }

    public void setListItemType(ListType listItemType) {
        this.listItemType = listItemType;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsMutable() {
        return this.isMutable;
    }

    public void setIsMutable(Boolean isMutable) {
        this.isMutable = isMutable;
    }

    public LocalDateTime getItemCreated() {
        return this.itemCreated;
    }

    public void setItemCreated(LocalDateTime itemCreated) {
        this.itemCreated = itemCreated;
    }

    public LocalDateTime getCompletedTime() {
        return this.completedTime;
    }

    public void setCompletedTime(LocalDateTime completedTime) {
        this.completedTime = completedTime;
    }

    // METHODS

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if(!(obj instanceof ListItem)) {
            return false;
        }

        ListItem typeCastedObj = (ListItem) obj;

        return typeCastedObj.getItemName().equals(this.getItemName());
    }

    public void toggleComplete() {
        if (this.getStatus() == Status.INCOMPLETE) {
            this.setCompletedTime(LocalDateTime.now());
            this.setStatus(Status.COMPELTE);
        }
        else {
            this.setCompletedTime(null);
            this.setStatus(Status.INCOMPLETE);
        }

    }
}
