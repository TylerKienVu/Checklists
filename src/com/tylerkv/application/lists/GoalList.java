package com.tylerkv.application.lists;

import com.tylerkv.application.baseobjects.List;
import com.tylerkv.application.baseobjects.ListItem;
import com.tylerkv.application.listitems.GoalListItem;
import com.tylerkv.application.utilities.ListType;
import com.tylerkv.application.utilities.ListUser;

import java.util.ArrayList;
import java.util.Arrays;

// This class acts simply as an instantiation object for the List Abstract Class.
// Specifies the list type as GOAL which protects the methods and attributes

public class GoalList extends List {
    public GoalList(String listName, ListUser owner) {
        super(listName, ListType.GOAL, owner);
    }

    // Created specific getItem method for goal list in order to look for goal items recursively
    public GoalListItem getGoalListItem(String itemName, Object[] paths) throws IllegalArgumentException{
        ArrayList<ListItem> currentItemList = this.getItemList();
        for(ListItem item : currentItemList) {

            //If the item is in the base list, return it
            if ( paths.length == 1) {
                if (item.getItemName().equals(itemName)) {
                    return (GoalListItem) item;
                }
            }

            // Else, start searching recursively using path
            else if(item.getItemName().equals(paths[0].toString())){
                Object[] splicedPaths = Arrays.copyOfRange(paths, 1, paths.length);
                GoalListItem result = recursiveSearch(itemName, (GoalListItem) item, splicedPaths);
                if (result == null) {
                    throw new IllegalArgumentException("The item (" + itemName + ") does not exist");
                }
                return result;
            }
        }
        throw new IllegalArgumentException("The item (" + itemName + ") does not exist");
    }

    public void deleteGoalListItem(String itemName, Object[] paths) {
        ArrayList<ListItem> currentItemList = this.getItemList();
        for(ListItem item: currentItemList) {
            if (paths.length == 1) {
                this.deleteItem(itemName);
                return;
            }
            else if (paths.length == 2) {
                if (item.getItemName().equals(paths[0].toString())) {
                    GoalListItem goalListItem = (GoalListItem) item;
                    goalListItem.deleteChildGoal(itemName);
                    return;
                }
            }
            else if(item.getItemName().equals(paths[0].toString())) {
                String nextChild = paths[1].toString();
                GoalListItem currentItem = (GoalListItem) item;
                Object[] splicedPaths = Arrays.copyOfRange(paths, 1, paths.length);
                recursiveDelete(itemName, currentItem.getChildGoal(nextChild), splicedPaths);
                return;
            }
        }
        throw new IllegalArgumentException("The item (" + itemName + ") does not exist");
    }

    private void recursiveDelete(String itemName, GoalListItem currentItem, Object[] paths) {
        if(paths.length == 2) {
            currentItem.deleteChildGoal(itemName);
            return;
        }
        String nextChild = paths[0].toString();
        Object[] splicedPaths = Arrays.copyOfRange(paths, 1, paths.length);
        recursiveSearch(itemName, currentItem.getChildGoal(nextChild), splicedPaths);
    }

    private GoalListItem recursiveSearch(String itemName, GoalListItem currentItem, Object[] paths) {
        if(paths.length == 1) {
            return currentItem.getChildGoal(itemName);
        }
        String nextChild = paths[0].toString();
        Object[] splicedPaths = Arrays.copyOfRange(paths, 1, paths.length);
        return recursiveSearch(itemName, currentItem.getChildGoal(nextChild), splicedPaths);
    }

    public void addItemWithChildPath(GoalListItem itemToAdd, Object[] paths) {
        ArrayList<ListItem> currentItemList = this.getItemList();
        for(int i = 0; i < currentItemList.size(); i++) {
            if (currentItemList.get(i).getItemName().equals(paths[0].toString())) {
                GoalListItem currentItem = (GoalListItem) currentItemList.get(i);

                // if an item of main list, just append right now instead of recursing
                if(paths.length == 1) {
                    currentItem.addChildGoal(itemToAdd);
                }
                else {
                    // else, recurse until you find the right item to add the child goal to
                    recurseToChild(itemToAdd, (GoalListItem) currentItemList.get(i), paths);
                }
                return;
            }
        }
        throw new IllegalArgumentException("The item path was not found");
    }

    private void recurseToChild(GoalListItem itemToAdd, GoalListItem currentItem, Object[] paths) {
        if(paths.length == 1) {
            currentItem.addChildGoal(itemToAdd);
            return;
        }
        String nextChild = paths[1].toString();
        Object[] splicedPaths = Arrays.copyOfRange(paths,1,paths.length);
        recurseToChild(itemToAdd, currentItem.getChildGoal(nextChild), splicedPaths);
    }
}
