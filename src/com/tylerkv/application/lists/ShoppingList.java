package com.tylerkv.application.lists;

import com.tylerkv.application.baseobjects.List;
import com.tylerkv.application.utilities.ListType;
import com.tylerkv.application.utilities.ListUser;

// This class acts simply as an instantiation object for the List Abstract Class.
// Specifies the list type as SHOPPING which protects the methods and attributes

public class ShoppingList extends List {
    public ShoppingList(String listName, ListUser owner){
        super(listName, ListType.SHOPPING, owner);
    }
}
