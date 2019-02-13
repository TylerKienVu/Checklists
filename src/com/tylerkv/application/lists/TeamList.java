package com.tylerkv.application.lists;

import com.tylerkv.application.baseobjects.List;
import com.tylerkv.application.utilities.ListType;
import com.tylerkv.application.utilities.ListUser;
import com.tylerkv.application.utilities.PriorityList;

public class TeamList extends List implements PriorityList{
    private double listPriority;

    public TeamList(String listName, ListUser owner) {
        super(listName, ListType.TEAM, owner);
        this.setListPriority(0);
    }

    public TeamList(String listName, ListUser owner, double listPriority) {
        super(listName, ListType.TEAM, owner);
        this.setListPriority(listPriority);
    }

    // GETTERS AND SETTERS

    public double getListPriority() {
        return listPriority;
    }

    public void setListPriority(double listPriority) throws IllegalArgumentException {
        if (listPriority < 0.0 || listPriority > 1.0) {
            throw new IllegalArgumentException("The listPriority must be between 0.0 and 1.0 inclusive");
        }
        this.listPriority = listPriority;
    }
}
