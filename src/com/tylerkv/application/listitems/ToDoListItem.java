package com.tylerkv.application.listitems;

import com.tylerkv.application.baseobjects.ListItem;
import com.tylerkv.application.utilities.ItemDetails;
import com.tylerkv.application.utilities.ListType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ToDoListItem extends ListItem {

    private double itemPriority;
    private LocalDateTime completionRangeStartDate;
    private LocalDateTime completionRangeEndDate; //Uses endDate as "Due date" if startDate == null

    // Adding in two more constructors because forgot that deadline is optional

    public ToDoListItem(ItemDetails itemDetails){
        super(itemDetails.getItemName(), itemDetails.getItemDescription(), ListType.TODO);
    }

    public ToDoListItem(ItemDetails itemDetails, double itemPriority) {
        super(itemDetails.getItemName(), itemDetails.getItemDescription(), ListType.TODO);
        this.setItemPriority(itemPriority);
    }
    
    public ToDoListItem(ItemDetails itemDetails
            , LocalDateTime completionRangeEndDate) {
        super(itemDetails.getItemName(), itemDetails.getItemDescription(), ListType.TODO);
        this.setCompletionRangeEndDate(completionRangeEndDate);
        this.setItemPriority(0);
    }

    public ToDoListItem(ItemDetails itemDetails
            , LocalDateTime completionRangeStartDate, LocalDateTime completionRangeEndDate) {
        super(itemDetails.getItemName(), itemDetails.getItemDescription(), ListType.TODO);
        this.setCompletionRangeEndDate(completionRangeEndDate);
        this.setCompletionRangeStartDate(completionRangeStartDate);
        this.setItemPriority(0);
    }

    public ToDoListItem(ItemDetails itemDetails
            , LocalDateTime completionRangeEndDate, double itemPriority) {
        super(itemDetails.getItemName(), itemDetails.getItemDescription(), ListType.TODO);
        this.setCompletionRangeEndDate(completionRangeEndDate);
        this.setItemPriority(itemPriority);
    }

    public ToDoListItem(ItemDetails itemDetails
            , LocalDateTime completionRangeStartDate
            , LocalDateTime completionRangeEndDate, double itemPriority) {
        super(itemDetails.getItemName(), itemDetails.getItemDescription(), ListType.TODO);
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

    public double getDaysUntilDue() throws IllegalAccessException{
        if (this.getCompletionRangeEndDate() == null) {
            throw new IllegalAccessException("This item has no deadline");
        }
        long hoursUntil =  ChronoUnit.HOURS.between(LocalDateTime.now(), this.getCompletionRangeEndDate());
        return hoursUntil / 24.0;
    }

    public boolean isOverDue() throws IllegalAccessException{
        return this.getDaysUntilDue() <= 0;
    }

}
