package com.tylerkv.application.listitems;

import com.tylerkv.application.baseobjects.ListItem;
import com.tylerkv.application.utilities.ListType;
import com.tylerkv.application.utilities.ListUser;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class TeamListItem extends ListItem {
    private LocalDateTime deadline;
    private double itemPriority;
    private ArrayList<ListUser> assignedUsers;

    public TeamListItem(String itemName, String description, LocalDateTime deadline, double itemPriority) {
        super(itemName, description, ListType.TEAM);
        this.setDeadline(deadline);
        this.setItemPriority(itemPriority);
        this.setAssignedUsers(new ArrayList<ListUser>());
    }

    // GETTERS AND SETTERS

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public double getItemPriority() {
        return itemPriority;
    }

    public void setItemPriority(double itemPriority) throws IllegalArgumentException {
        if (itemPriority < 0.0 || itemPriority > 1.0) {
            throw new IllegalArgumentException("The priority must be between 0.0 and 1.0 inclusive");
        }
        this.itemPriority = itemPriority;
    }

    public ArrayList<ListUser> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(ArrayList<ListUser> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    // METHODS

    public double getDaysUntilDue() {
        long hoursUntil =  ChronoUnit.HOURS.between(LocalDateTime.now(), this.getDeadline());
        return hoursUntil / 24.0;
    }

    public boolean isOverDue() {
        return this.getDaysUntilDue() <= 0;
    }

    public void addUser(ListUser user) throws IllegalArgumentException {
        ArrayList<ListUser> currentUserList = this.getAssignedUsers();
        if (!currentUserList.contains(user)) {
            currentUserList.add(user);
            this.setAssignedUsers(currentUserList);
        }
        else {
            throw new IllegalArgumentException("The user ("
                    + user.getUserName()
                    + ") is already assigned to this TeamListItem");
        }
    }

    public void deleteUser(String userName) throws IllegalArgumentException {
        ArrayList<ListUser> currentUserList = this.getAssignedUsers();
        ListUser comparisonObject = new ListUser(userName, "pass");
        if (currentUserList.contains(comparisonObject)) {
            currentUserList.remove(comparisonObject);
            this.setAssignedUsers(currentUserList);
        }
        else {
            throw new IllegalArgumentException("The user ("
                    + comparisonObject.getUserName()
                    + ") is not in the user list");
        }
    }
}
