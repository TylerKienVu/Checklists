package com.tylerkv.application.listitems;

import com.tylerkv.application.baseobjects.ListItem;
import com.tylerkv.application.utilities.ListType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ToDoListItem extends ListItem {

    private double itemPriority;
    private LocalDateTime completionRangeStartDate;
    private LocalDateTime completionRangeEndDate; //Uses endDate as "Due date" if startDate == null

    // No options taken
    public ToDoListItem(String itemName, String description
            , LocalDateTime completionRangeEndDate) {
        super(itemName, description, ListType.TODO);
        this.setCompletionRangeEndDate(completionRangeEndDate);
        this.setItemPriority(0);
    }

    // Optional range
    public ToDoListItem(String itemName, String description
            , LocalDateTime completionRangeStartDate, LocalDateTime completionRangeEndDate) {
        super(itemName, description, ListType.TODO);
        this.setCompletionRangeEndDate(completionRangeEndDate);
        this.setCompletionRangeStartDate(completionRangeStartDate);
        this.setItemPriority(0);
    }

    // Optional priority
    public ToDoListItem(String itemName, String description
            , LocalDateTime completionRangeEndDate, double itemPriority) {
        super(itemName, description, ListType.TODO);
        this.setCompletionRangeEndDate(completionRangeEndDate);
        this.setItemPriority(itemPriority);
    }

    // Optional priority and range
    public ToDoListItem(String itemName, String description
            , LocalDateTime completionRangeStartDate
            , LocalDateTime completionRangeEndDate, double itemPriority) {
        super(itemName, description, ListType.TODO);
        this.setCompletionRangeStartDate(completionRangeStartDate);
        this.setCompletionRangeEndDate(completionRangeEndDate);
        this.setItemPriority(itemPriority);
    }

    // GETTERS AND SETTERS

    public double getItemPriority() {
        return itemPriority;
    }

    public void setItemPriority(double itemPriority) throws IllegalArgumentException {
        if (itemPriority < 0.0 || itemPriority > 1.0) {
            throw new IllegalArgumentException("The priority must be between 0.0 and 1.0 inclusive");
        }
        this.itemPriority = itemPriority;
    }

    public LocalDateTime getCompletionRangeStartDate() {
        return completionRangeStartDate;
    }

    public void setCompletionRangeStartDate(LocalDateTime completionRangeStartDate) {
        this.completionRangeStartDate = completionRangeStartDate;
    }

    public LocalDateTime getCompletionRangeEndDate() {
        return completionRangeEndDate;
    }

    public void setCompletionRangeEndDate(LocalDateTime completionRangeEndDate) {
        this.completionRangeEndDate = completionRangeEndDate;
    }

    // METHODS

    public double getDaysUntilDue() {
        long hoursUntil =  ChronoUnit.HOURS.between(LocalDateTime.now(), this.getCompletionRangeEndDate());
        return hoursUntil / 24.0;
    }

    public boolean isOverDue() {
        return this.getDaysUntilDue() <= 0;
    }

}
