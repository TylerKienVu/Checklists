package com.tylerkv.application.lists;

import com.tylerkv.application.baseobjects.List;
import com.tylerkv.application.utilities.ListType;
import com.tylerkv.application.utilities.ListUser;

// This class acts simply as an instantiation object for the List Abstract Class.
// Specifies the list type as GOAL which protects the methods and attributes

public class GoalList extends List {
    public GoalList(String listName, ListUser owner) {
        super(listName, ListType.GOAL, owner);
    }
}
