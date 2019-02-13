package com.tylerkv.ui.views;

import com.tylerkv.application.baseobjects.ListItem;
import com.tylerkv.application.listitems.TeamListItem;
import com.tylerkv.application.listitems.ToDoListItem;
import com.tylerkv.application.lists.TeamList;
import com.tylerkv.application.lists.ToDoList;
import com.tylerkv.application.utilities.*;
import com.tylerkv.ui.frames.creation.AddTeamItemFrame;
import com.tylerkv.ui.frames.creation.AddTeamListFrame;
import com.tylerkv.ui.frames.creation.AddToDoItemFrame;
import com.tylerkv.ui.frames.creation.AddToDoListFrame;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TeamListView extends JPanel {
    private ListDriver listDriver;
    private MainView parentView;
    private GroupLayout groupLayout;
    private JButton addListButton;
    private JButton deleteListButton;
    private JButton addItemButton;
    private JButton deleteItemButton;
    private JButton toggleItemButton;
    private JComboBox<String> listsComboBox;
    private JScrollPane itemListScrollPane;
    private JList itemList;
    private DefaultListModel listModel;
    private JLabel itemNameLabel;
    private JLabel itemDescLabel;
    private JLabel itemPriorityLabel;
    private JLabel endDateLabel;
    private JLabel assignedUsersLabel;

    // TODO: Add sequential date checking

    public TeamListView(MainView parentView, ListDriver listDriver) {
        this.listDriver = listDriver;
        this.parentView = parentView;
        this.initPanel();
    }

    public void createList(String listName, double priority) {
        try {
            this.listDriver.addTeamList(listName, priority);
            this.loadTeamLists();
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    public void createItem(String itemName, String desc, LocalDateTime end, double priority, String assignedUsers) {
        TeamListItem itemToAdd;
        try {
            itemToAdd = new TeamListItem(new ItemDetails(itemName, desc), end, priority);
            String[] splitUsers = assignedUsers.split(",");
            for(String user : splitUsers) {
                ListUser newUser = new ListUser(user,"test");
                itemToAdd.addUser(newUser);
            }

            this.listDriver.addItemToList(listsComboBox.getSelectedItem().toString(), ListType.TEAM, itemToAdd);
            this.loadTeamListItems();
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    private void deleteSelectedList() {
        String listToDelete = (String) listsComboBox.getSelectedItem();
        this.listDriver.deleteList(listToDelete, ListType.TEAM);
        this.loadTeamLists();
        this.loadTeamListItems();
    }

    private void deleteSelectedItem() {
        String itemToDelete = getSelectedItemString();

        this.listDriver.deleteItemFromList(listsComboBox.getSelectedItem().toString(), ListType.TEAM,itemToDelete);
        this.loadTeamListItems();
    }

    private void toggleSelectedItem() {
        String itemToComplete = getSelectedItemString();

        this.listDriver.toggleItemComplete(listsComboBox.getSelectedItem().toString(), ListType.TEAM, itemToComplete);
        this.loadTeamListItems();
    }

    private String getSelectedItemString() {
        String rawString = (String)itemList.getSelectedValue();
        int indexOfComplete = rawString.indexOf("COMPLETE");
        if (indexOfComplete == -1) {
            return rawString.trim();
        }
        return rawString.substring(0, indexOfComplete).trim();
    }

    private void loadTeamLists() {
        listsComboBox.removeAllItems();
        ArrayList<TeamList> TeamLists = this.listDriver.getTeamLists();
        for(int i = 0; i < TeamLists.size(); i++) {
            listsComboBox.addItem(TeamLists.get(i).getListName());
        }
    }

    private void loadTeamListItems() {
        listModel.removeAllElements();
        if (listsComboBox.getSelectedItem() != null) {
            TeamList selectedTeamList = (TeamList) this.listDriver.getList(listsComboBox.getSelectedItem().toString(), ListType.TEAM);
            ArrayList<ListItem> itemList = selectedTeamList.getItemList();
            for(int i = 0; i < itemList.size(); i++) {
                TeamListItem currentItem = (TeamListItem) itemList.get(i);
                String stringToAdd;
                if(currentItem.getStatus() == Status.COMPELTE) {
                    stringToAdd = String.format("%-5s COMPLETE", currentItem.getItemName());
                }
                else {
                    stringToAdd = String.format("%s", currentItem.getItemName());
                }

                listModel.addElement(stringToAdd);
            }
        }
    }

    private void initPanel() {
        this.initComponents();
        this.initLayout();
        this.loadTeamLists();
        this.loadTeamListItems();
    }

    private void initComponents() {
        itemNameLabel = new JLabel("<html>Name: </html>");
        itemDescLabel = new JLabel("<html>Description: </html>");
        itemPriorityLabel = new JLabel("<html>Priority: </html>");
        endDateLabel = new JLabel("<html>Deadline: </html>");
        assignedUsersLabel = new JLabel("<htmL>Assigned Users: </html>");

        addListButton = new JButton("Add List");
        addListButton.setActionCommand("ADD LIST");
        addListButton.addActionListener(new AddListAction(this));

        deleteListButton = new JButton("Delete List");
        deleteListButton.setActionCommand("DELETE LIST");
        deleteListButton.addActionListener(new DeleteListAction(this));

        addItemButton = new JButton("Add Item");
        addItemButton.setActionCommand("ADD ITEM");
        addItemButton.addActionListener(new AddItemAction(this));

        deleteItemButton = new JButton("Delete Item");
        deleteItemButton.setActionCommand("DELETE ITEM");
        deleteItemButton.addActionListener(new DeleteItemAction());

        toggleItemButton = new JButton("Toggle Complete");
        toggleItemButton.setActionCommand("TOGGLE");
        toggleItemButton.addActionListener(new ToggleItemAction());

        listsComboBox = new JComboBox<>();
        listsComboBox.setMaximumSize(new Dimension(400,5));
        listsComboBox.addActionListener(new NewListSelectedAction());

        itemList = new JList();
        listModel = new DefaultListModel();
        itemList.setModel(listModel);
        itemList.addListSelectionListener(new NewListItemSelectedListener());

        itemListScrollPane = new JScrollPane();
        itemListScrollPane.setPreferredSize(new Dimension(250, 200));
        itemListScrollPane.getViewport().add(itemList);
    }

    private void initLayout() {
        groupLayout = new GroupLayout(this);
        this.setLayout(groupLayout);
        groupLayout.linkSize(SwingConstants.HORIZONTAL, addListButton, deleteListButton, addItemButton, deleteItemButton);
        this.drawLayout();
    }

    private void drawLayout() {
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(groupLayout.createSequentialGroup()
                .addGap(10)
                .addComponent(addListButton)
                .addGap(5)
                .addComponent(deleteListButton))
            .addGroup(groupLayout.createSequentialGroup()
                .addGap(10)
                .addComponent(listsComboBox)
                .addGap(10))
            .addGroup(groupLayout.createSequentialGroup()
                .addGap(10)
                .addComponent(itemListScrollPane)
                .addGap(10)
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(addItemButton)
                    .addComponent(deleteItemButton)
                    .addComponent(toggleItemButton))
                .addGap(10))
            .addGroup(groupLayout.createSequentialGroup()
                .addGap(10)
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(itemNameLabel)
                    .addComponent(itemPriorityLabel)
                    .addComponent(endDateLabel)
                    .addComponent(assignedUsersLabel)
                    .addComponent(itemDescLabel)))
        );

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
            .addGap(15)
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(addListButton)
                .addComponent(deleteListButton))
            .addGap(15)
            .addComponent(listsComboBox)
            .addGap(50)
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addComponent(itemListScrollPane)
                    .addGap(15))
                .addGroup(groupLayout.createSequentialGroup()
                    .addComponent(addItemButton)
                    .addGap(5)
                    .addComponent(deleteItemButton)
                    .addGap(5)
                    .addComponent(toggleItemButton)))
            .addGroup(groupLayout.createSequentialGroup()
                .addComponent(itemNameLabel)
                .addGap(5)
                .addComponent(endDateLabel)
                .addGap(5)
                .addComponent(itemPriorityLabel)
                .addGap(5)
                .addComponent(assignedUsersLabel)
                .addGap(5)
                .addComponent(itemDescLabel)
                .addGap(30))
        );
    }

    private class AddListAction extends AbstractAction {
        private TeamListView TeamListView;

        private AddListAction(TeamListView TeamListView) {
            this.TeamListView = TeamListView;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            AddTeamListFrame addTeamListFrame = new AddTeamListFrame(TeamListView);
        }
    }

    private class DeleteListAction extends AbstractAction {
        private TeamListView TeamListView;

        private DeleteListAction(TeamListView TeamListView) {
            this.TeamListView = TeamListView;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(TeamListView.listsComboBox.getSelectedItem() != null) {
                TeamListView.deleteSelectedList();
            }
        }
    }

    private class AddItemAction extends AbstractAction {
        private TeamListView teamListView;

        private AddItemAction(TeamListView teamListView) {
            this.teamListView = teamListView;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(listsComboBox.getSelectedItem() != null) {
                AddTeamItemFrame addTeamItemFrame = new AddTeamItemFrame(teamListView);
            }
        }
    }

    private class DeleteItemAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(itemList.getSelectedValue() != null) {
                deleteSelectedItem();
            }
        }
    }

    private class ToggleItemAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(itemList.getSelectedValue() != null) {
                toggleSelectedItem();
            }
        }
    }

    private class NewListSelectedAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(listsComboBox.getSelectedItem() != null) {
                loadTeamListItems();
            }
        }
    }

    // TODO: Add assigned users label

    private class NewListItemSelectedListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(!e.getValueIsAdjusting() && itemList.getSelectedValue() != null) {
                String selectedString = getSelectedItemString();

                TeamList selectedTeamList = (TeamList) listDriver.getList(listsComboBox.getSelectedItem().toString(), ListType.TEAM);
                TeamListItem selectedItem = (TeamListItem) selectedTeamList.getItem(selectedString);

                LocalDateTime endDate = selectedItem.getDeadline();

                itemNameLabel.setText("<html>Name: " + selectedItem.getItemName() + "</html>");
                if(endDate != null) {
                    endDateLabel.setText(String.format("<html>Deadline: %d-%d-%d </html>", endDate.getMonthValue(), endDate.getDayOfMonth(), endDate.getYear()));
                }

                // TODO: This is temporary, in the future, use actual users instead of string
                //Assigned Users
                ArrayList<ListUser> userList = selectedItem.getAssignedUsers();
                String userLabelText = "Assigned Users: ";
                for(int i = 0; i < userList.size(); i++) {
                    userLabelText += userList.get(i).getUserName();
                    if (userList.size() - 1 != i) {
                        userLabelText += ",";
                    }
                }
                assignedUsersLabel.setText(userLabelText);

                itemPriorityLabel.setText("<html>Priority: " + selectedItem.getItemPriority() + "</html>");
                itemDescLabel.setText("<html>Description: " + selectedItem.getDescription() + "</html>");
            }
            else {
                // Reset labels if not targeting anything
                itemNameLabel.setText("<html>Name: </html>");
                itemDescLabel.setText("<html>Description: </html>");
                itemPriorityLabel.setText("<html>Priority: </html>");
                endDateLabel.setText("<html>Deadline: </html>");
            }
        }
    }
}
