package com.tylerkv.application.listitems;

import com.tylerkv.application.utilities.ItemDetails;
import com.tylerkv.application.utilities.ListType;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GoalListItem extends ToDoListItem {
    private GoalListItem parent;
    private ArrayList<GoalListItem> children;

    public GoalListItem(ItemDetails itemDetails) {
        super(itemDetails);
        this.initializeGoalListItem();
    }

    public GoalListItem(ItemDetails itemDetails, double priority){
        super(itemDetails, priority);
        this.initializeGoalListItem();
    }

    // No option taken
    public GoalListItem(ItemDetails itemDetails
            , LocalDateTime completionRangeEndDate) {
        super(itemDetails, completionRangeEndDate);
        this.initializeGoalListItem();
    }

    // Optional range
    public GoalListItem(ItemDetails itemDetails
            , LocalDateTime completionRangeStartDate, LocalDateTime completionRangeEndDate) {
        super(itemDetails, completionRangeStartDate, completionRangeEndDate);
        this.initializeGoalListItem();
    }


    // Optional priority
    public GoalListItem(ItemDetails itemDetails
            , LocalDateTime completionRangeEndDate, double itemPriority) {
        super(itemDetails, completionRangeEndDate, itemPriority);
        this.initializeGoalListItem();
    }

    // Optional priority and range
    public GoalListItem(ItemDetails itemDetails
            , LocalDateTime completionRangeStartDate
            , LocalDateTime completionRangeEndDate, double itemPriority) {
        super(itemDetails, completionRangeStartDate
                , completionRangeEndDate, itemPriority);
        this.initializeGoalListItem();
    }

    // GETTERS AND SETTERS

    public GoalListItem getParent() {
        return parent;
    }

    public void setParent(GoalListItem parent) throws IllegalArgumentException {
        if (this.equals(parent)){
            throw new IllegalArgumentException("A GoalListItem cannot be a parent of itself");
        }
        this.parent = parent;
    }

    public ArrayList<GoalListItem> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<GoalListItem> children) {
        this.children = children;
    }

    // METHODS

    public void addChildGoal(GoalListItem child) throws IllegalArgumentException {
        if (this.equals(parent)){
            throw new IllegalArgumentException("A GoalListItem cannot be a child of itself");
        }

        // Check to make sure that the item being added is the right type
        if (child.getListItemType() != this.getListItemType()){
            throw new IllegalArgumentException("The list item is of type ("
                    + child.getListItemType()
                    + ") but the list is of type " + this.getListItemType());
        }

        ArrayList<GoalListItem> currentItemList = this.getChildren();
        if (!currentItemList.contains(child)){
            child.setParent(this);
            currentItemList.add(child);
            this.setChildren(currentItemList);
        }
        else {
            throw new IllegalArgumentException("The list item ("
                    + child.getItemName()
                    + ") is already in the item list");
        }
    }

    public GoalListItem getChildGoal(String childItem) {
        ArrayList<GoalListItem> currentChildList = this.getChildren();
        for(GoalListItem child : currentChildList) {
            if (child.getItemName().equals(childItem)) {
                return child;
            }
        }
        throw new IllegalArgumentException("The child (" + childItem + ") was not found");
    }

    public void deleteChildGoal(String itemToDelete) throws IllegalArgumentException {
        ArrayList<GoalListItem> currentItemList = this.getChildren();
        for(int i = 0; i < currentItemList.size(); i++) {
            if (currentItemList.get(i).getItemName().equals(itemToDelete)) {
                currentItemList.remove(i);
                this.setChildren(currentItemList);
                return;
            }
        }
        throw new IllegalArgumentException("The list item ("
                + itemToDelete
                + ") is not in the item list");
    }

    private void initializeGoalListItem() {
        this.setListItemType(ListType.GOAL);
        this.setChildren(new ArrayList<GoalListItem>());
    }
}
