package com.tylerkv.application.lists;

import com.tylerkv.application.baseobjects.List;
import com.tylerkv.application.baseobjects.ListItem;
import com.tylerkv.application.utilities.ListType;
import com.tylerkv.application.utilities.ListUser;
import com.tylerkv.application.utilities.SortByDateAndPriority;

import java.util.ArrayList;

public class ToDoList extends List {
    private double listPriority;

    public ToDoList(String listName, ListUser owner){
        super(listName, ListType.TODO, owner);
        this.setListPriority(0);
    }

    public ToDoList(String listName, ListUser owner, double listPriority){
        super(listName, ListType.TODO, owner);
        this.setListPriority(listPriority);
    }

    // GETTERS AND SETTERS

    public double getListPriority() {
        return this.listPriority;
    }

    public void setListPriority(double listPriority) throws IllegalArgumentException {
        if (listPriority < 0.0 || listPriority > 1.0) {
            throw new IllegalArgumentException("The priority must be between 0.0 and 1.0 inclusive");
        }
        this.listPriority = listPriority;
    }

    // METHODS

    // Due date is taken into account first and then priority.
    // Returns an array list of the list items that represent the order
    // that they should be presented in.
    public ArrayList<ListItem> calculateListOrder() {
        ArrayList<ListItem> currentList = this.getItemList();
        currentList.sort(new SortByDateAndPriority());
        return currentList;
    }
}
