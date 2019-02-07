package com.tylerkv.application.baseobjects;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;

import com.tylerkv.application.utilities.ListUser;
import com.tylerkv.application.utilities.ListType;
import com.tylerkv.application.utilities.Status;

// TODO: Refactor code so that copies of the objects are returned;

public abstract class List {
    private String listName;
    private ArrayList<ListUser> userList;
    private ArrayList<ListItem> itemList;
    private int maxListUsers;
    private ListType listType;
    private ListUser owner;

    public List(String listName, ListType listType, ListUser owner) {
        this.setListName(listName);
        this.setListType(listType);
        this.setOwner(owner);
        this.setUserList(new ArrayList<ListUser>());
        this.setItemList(new ArrayList<ListItem>());
        this.addUser(owner);
    }

    // GETTERS AND SETTERS

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public ArrayList<ListUser> getUserList() {
        return this.userList;
    }

    public void setUserList(ArrayList<ListUser> userList) {
        this.userList = userList;
    }

    public ArrayList<ListItem> getItemList() {
        return this.itemList;
    }

    public void setItemList(ArrayList<ListItem> itemList) {
        this.itemList = itemList;
    }

    public int getMaxListUsers() {
        return maxListUsers;
    }

    public void setMaxListUsers(int maxListUsers) {
        this.maxListUsers = maxListUsers;
    }

    public ListType getListType() {
        return this.listType;
    }

    public void setListType(ListType listType) {
        this.listType = listType;
    }

    public ListUser getOwner() {
        return this.owner;
    }

    public void setOwner(ListUser owner) {
        this.owner = owner;
    }

    // METHODS

    public void addItem(ListItem item) throws IllegalArgumentException {
        // Check to make sure that the item being added is the right type
        if (item.getListItemType() != this.getListType()){
            throw new IllegalArgumentException("The list item is of type ("
                    + item.getListItemType()
                    + ") but the list is of type " + this.getListType());
        }

        ArrayList<ListItem> currentItemList = this.getItemList();
        if (!currentItemList.contains(item)){
            currentItemList.add(item);
            this.setItemList(currentItemList);
        }
        else {
            throw new IllegalArgumentException("The list item ("
                    + item.getItemName()
                    + ") is already in the item list");
        }

    }

    public void deleteItem(String itemName) throws IllegalArgumentException {
        ArrayList<ListItem> currentItemList = this.getItemList();
        for(int i = 0; i < currentItemList.size(); i++) {
            if (currentItemList.get(i).getItemName().equals(itemName)) {
                currentItemList.remove(i);
                this.setItemList(currentItemList);
                return;
            }
        }
        throw new IllegalArgumentException("The list item ("
                + itemName
                + ") is not in the item list");
    }

    public void addUser(ListUser user) throws IllegalArgumentException {
        ArrayList<ListUser> currentUserList = this.getUserList();
        if (!currentUserList.contains(user)) {
            currentUserList.add(user);
            this.setUserList(currentUserList);
        }
        else {
            throw new IllegalArgumentException("The user ("
                    + user.getUserName()
                    + ") is already in the user list");
        }
    }

    public void deleteUser(String userName) throws IllegalArgumentException {
        ArrayList<ListUser> currentUserList = this.getUserList();
        ListUser comparisonObject = new ListUser(userName, "pass");
        if (currentUserList.contains(comparisonObject)) {
            currentUserList.remove(comparisonObject);
            this.setUserList(currentUserList);
        }
        else {
            throw new IllegalArgumentException("The user ("
                    + comparisonObject.getUserName()
                    + ") is not in the user list");
        }
    }

    public void clearFinishedListItems() {
        ArrayList<ListItem> currentItemList = this.getItemList();

        //Using an iterator because it will remove items while iterating
        Iterator iter = currentItemList.iterator();
        while(iter.hasNext()) {
            ListItem currentItem = (ListItem) iter.next();

            //If the item is complete and has been completed for at least 24 hours, remove
            if (currentItem.getStatus() == Status.COMPELTE) {
                long timeSinceCompletion = ChronoUnit.HOURS.between(currentItem.getCompletedTime(), LocalDateTime.now());
                if (timeSinceCompletion >= 24) {
                    iter.remove();
                }
            }
        }
        this.setItemList(currentItemList);
    }

    public void toggleItemComplete(String itemName) {
        this.getItem(itemName).toggleComplete();
    }

    private ListItem getItem(String itemName) {
        ArrayList<ListItem> currentItemList = this.getItemList();
        for(int i = 0; i < currentItemList.size(); i++) {
            if (currentItemList.get(i).getItemName().equals(itemName)) {
                return currentItemList.get(i);
            }
        }
        throw new IllegalArgumentException("The item (" + itemName + ") does not exist");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if(!(obj instanceof List)) {
            return false;
        }

        List typeCastedObj = (List) obj;

        return typeCastedObj.getListName().equals(this.getListName());
    }
}
