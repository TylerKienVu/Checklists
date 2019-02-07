package com.tylerkv.application.utilities;

import com.tylerkv.application.baseobjects.List;
import com.tylerkv.application.baseobjects.ListItem;
import com.tylerkv.application.lists.GoalList;
import com.tylerkv.application.lists.ShoppingList;
import com.tylerkv.application.lists.TeamList;
import com.tylerkv.application.lists.ToDoList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListDriver {
    private ArrayList<ShoppingList> shoppingLists;
    private ArrayList<ToDoList> toDoLists;
    private GoalList goalList; // Spec says user can only have one goal list
    private ArrayList<TeamList> teamLists;
    private ListUser owner;
    private Map<ListType, Boolean> enabledLists;

    // GoalList will not be instantiated until user decides they want one
    public ListDriver(ListUser owner) {
        this.setOwner(owner);
        this.setShoppingLists(new ArrayList<ShoppingList>());
        this.setToDoLists(new ArrayList<ToDoList>());
        this.setTeamLists(new ArrayList<TeamList>());
        this.initializeEnabledLists();
    }

    // GETTERS AND SETTERS

    public ArrayList<ShoppingList> getShoppingLists() {
        return shoppingLists;
    }

    private void setShoppingLists(ArrayList<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
    }

    public ArrayList<ToDoList> getToDoLists() {
        return toDoLists;
    }

    private void setToDoLists(ArrayList<ToDoList> toDoLists) {
        this.toDoLists = toDoLists;
    }

    public GoalList getGoalList() {
        return goalList;
    }

    private void setGoalList(GoalList goalList) {
        this.goalList = goalList;
    }

    public ArrayList<TeamList> getTeamLists() {
        return teamLists;
    }

    private void setTeamLists(ArrayList<TeamList> teamLists) {
        this.teamLists = teamLists;
    }

    public ListUser getOwner() {
        return owner;
    }

    private void setOwner(ListUser owner) {
        this.owner = owner;
    }

    public Map<ListType, Boolean> getEnabledLists() {
        return enabledLists;
    }

    private void setEnabledLists(Map<ListType, Boolean> enabledLists) {
        this.enabledLists = enabledLists;
    }
    // METHODS

    public void addShoppingList(String listName) throws IllegalArgumentException {
        enabledListCheck(ListType.SHOPPING);
        ArrayList<ShoppingList> currentList = this.getShoppingLists();
        ShoppingList listToAdd = new ShoppingList(listName, this.getOwner());
        duplicateListCheck(currentList, listToAdd);
        currentList.add(listToAdd);
        this.setShoppingLists(currentList);
    }

    public void addToDoList(String listName) throws IllegalArgumentException {
        enabledListCheck(ListType.TODO);
        ArrayList<ToDoList> currentList = this.getToDoLists();
        ToDoList listToAdd = new ToDoList(listName, this.getOwner());
        duplicateListCheck(currentList, listToAdd);
        currentList.add(listToAdd);
        this.setToDoLists(currentList);
    }

    public void addToDoList(String listName, double listPriority) throws IllegalArgumentException {
        enabledListCheck(ListType.TODO);
        ArrayList<ToDoList> currentList = this.getToDoLists();
        ToDoList listToAdd = new ToDoList(listName, this.getOwner(), listPriority);
        duplicateListCheck(currentList, listToAdd);
        currentList.add(listToAdd);
        this.setToDoLists(currentList);
    }

    public void addGoalList(String listName) throws IllegalArgumentException {
        enabledListCheck(ListType.GOAL);
        this.setGoalList(new GoalList(listName, this.getOwner()));
    }

    public void addTeamList(String listName) throws IllegalArgumentException {
        enabledListCheck(ListType.TEAM);
        ArrayList<TeamList> currentList = this.getTeamLists();
        TeamList listToAdd = new TeamList(listName, this.getOwner());
        duplicateListCheck(currentList, listToAdd);
        currentList.add(listToAdd);
        this.setTeamLists(currentList);
    }

    public void addTeamList(String listName, double listPriority) throws IllegalArgumentException {
        enabledListCheck(ListType.TEAM);
        ArrayList<TeamList> currentList = this.getTeamLists();
        TeamList listToAdd = new TeamList(listName, this.getOwner(), listPriority);
        duplicateListCheck(currentList, listToAdd);
        currentList.add(listToAdd);
        this.setTeamLists(currentList);
    }

    public void deleteList(String listName, ListType listType) {
        if (listType == ListType.GOAL) {
            this.setGoalList(null);
        }
        ArrayList<? extends List> listOfLists = this.getListsOfType(listType);

        for(int i = 0; i < listOfLists.size(); i++) {
            if (listOfLists.get(i).getListName().equals(listName)) {
                listOfLists.remove(i);
                return;
            }
        }
        throw new IllegalArgumentException("The list (" + listName + ") does not exist");
    }

    private ArrayList<? extends List> getListsOfType(ListType listType) {
        ArrayList<? extends List> listOfLists;
        switch(listType) {
            case SHOPPING:
                listOfLists = this.getShoppingLists();
                break;
            case TODO:
                listOfLists = this.getToDoLists();
                break;
            case GOAL:
                throw new IllegalArgumentException("Use the getGoalList() method");
            case TEAM:
                listOfLists = this.getTeamLists();
                break;
            default:
                throw new IllegalArgumentException("The list type (" + listType + ") does not exist");
        }
        return listOfLists;
    }

    public void toggleList(ListType listType) {
        Map<ListType, Boolean> currentMap = this.getEnabledLists();
        currentMap.put(listType, !currentMap.get(listType));
        this.setEnabledLists(currentMap);
    }

    // This method will go through every list of lists in the driver and clear any items that have been completed
    // for 24 hours
    public void cleanAllLists() {
        ArrayList<ShoppingList> shoppingLists = this.getShoppingLists();
        ArrayList<ToDoList> toDoLists = this.getToDoLists();
        ArrayList<TeamList> teamLists = this.getTeamLists();

        clearFinishedItems(shoppingLists);
        clearFinishedItems(toDoLists);
        clearFinishedItems(teamLists);

        //special case for goal because only one goal list
        GoalList currentGoalList = this.getGoalList();
        currentGoalList.clearFinishedListItems();
        this.setGoalList(currentGoalList);
    }

    // This method will go through a specific list of lists
    // this method is O(n^2)
    private void clearFinishedItems(ArrayList<? extends List> listOfLists) {
        for(int i = 0; i < listOfLists.size(); i++) {
            List currentList = listOfLists.get(i);
            currentList.clearFinishedListItems();
        }
    }

    public void addItemToList(String listName, ListType listType, ListItem itemToAdd) {
        enabledListCheck(listType);
        List targetList = this.getList(listName, listType);
        targetList.addItem(itemToAdd);
    }

    public void deleteItemFromList(String listName, ListType listType, String itemToDelete) {
        enabledListCheck(listType);
        List targetList = this.getList(listName, listType);
        targetList.deleteItem(itemToDelete);
    }

    public void toggleItemComplete(String listName, ListType listType, String itemToToggle) {
        enabledListCheck(listType);
        List targetList = this.getList(listName, listType);
        targetList.toggleItemComplete(itemToToggle);
    }

    private void duplicateListCheck(ArrayList<?> currentList, List listToAdd) throws IllegalArgumentException{
        if (currentList.contains(listToAdd)) {
            throw new IllegalArgumentException("The list (" + listToAdd.getListName() + ") already exists");
        }
    }

    private void enabledListCheck(ListType listType) throws IllegalArgumentException {
        Map<ListType, Boolean> currentMap = this.getEnabledLists();
        if (!currentMap.get(listType)) {
            throw new IllegalArgumentException("The list type (" + listType + ") is currently disabled");
        }
    }

    private void initializeEnabledLists() {
        Map<ListType, Boolean> mapToSet = new HashMap<ListType, Boolean>();
        mapToSet.put(ListType.SHOPPING, true);
        mapToSet.put(ListType.TODO, true);
        mapToSet.put(ListType.GOAL, true);
        mapToSet.put(ListType.TEAM, true);
        this.setEnabledLists(mapToSet);
    }

    public List getList(String listName, ListType listType) {
        if (listType == ListType.GOAL) {
            return this.getGoalList();
        }

        ArrayList<? extends List> listOfLists = this.getListsOfType(listType);

        for(int i = 0; i < listOfLists.size(); i++) {
            if (listOfLists.get(i).getListName().equals(listName)) {
                return listOfLists.get(i);
            }
        }
        throw new IllegalArgumentException("The list (" + listName + ") does not exist");
    }
}
