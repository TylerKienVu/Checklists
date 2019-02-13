package com.tylerkv.ui.views;

import com.tylerkv.application.baseobjects.ListItem;
import com.tylerkv.application.listitems.GoalListItem;
import com.tylerkv.application.listitems.ToDoListItem;
import com.tylerkv.application.lists.GoalList;
import com.tylerkv.application.lists.ToDoList;
import com.tylerkv.application.utilities.ListDriver;
import com.tylerkv.application.utilities.ListType;
import com.tylerkv.application.utilities.Status;
import com.tylerkv.ui.frames.creation.AddGoalItemFrame;
import com.tylerkv.ui.frames.creation.AddGoalListFrame;
import com.tylerkv.ui.frames.creation.AddToDoItemFrame;
import com.tylerkv.ui.frames.creation.AddToDoListFrame;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class GoalListView extends JPanel {
    private ListDriver listDriver;
    private MainView parentView;
    private GroupLayout groupLayout;
    private JButton addListButton;
    private JButton deleteListButton;
    private JButton addItemButton;
    private JButton deleteItemButton;
    private JButton toggleItemButton;
    private JScrollPane itemListScrollPane;
    private JTree goalListTree;
    private DefaultMutableTreeNode root;
    private JLabel goalListLabel;
    private JLabel itemNameLabel;
    private JLabel itemDescLabel;
    private JLabel itemPriorityLabel;
    private JLabel startDateLabel;
    private JLabel endDateLabel;

    // TODO: Add sequential date checking

    public GoalListView(MainView parentView, ListDriver listDriver) {
        this.listDriver = listDriver;
        this.parentView = parentView;
        this.initPanel();
    }

    public void createList(String listName) {
        try {
            this.listDriver.addGoalList(listName);
            this.loadGoalList();
            this.loadGoalListItems();
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    public void createItem(String itemName, String desc, LocalDateTime start, LocalDateTime end, double priority) {
        if(listDriver.getGoalList() == null) {
            return;
        }

        GoalListItem itemToAdd = constructGoalListItem(itemName, desc, start, end, priority);
        try {
            // Decide whether to add to root or to add to a child node
            DefaultMutableTreeNode selectedItem = (DefaultMutableTreeNode) goalListTree.getLastSelectedPathComponent();
            if(selectedItem == null  || selectedItem.getUserObject().equals(listDriver.getGoalList().getListName())) {

                this.listDriver.addItemToList(listDriver.getGoalList().getListName(), ListType.GOAL, itemToAdd);
            }
            else {
                Object[] paths = getCleanPath();

                // Get rid of root directory in path
                Object[] splicedPaths = Arrays.copyOfRange(paths, 1, paths.length);

                // TODO: Change listdriver to be friends with lists so only it can get direct access
                this.listDriver.getGoalList().addItemWithChildPath(itemToAdd, splicedPaths);
            }
            this.loadGoalListItems();
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private GoalListItem constructGoalListItem(String itemName, String desc, LocalDateTime start, LocalDateTime end, double priority) {
        if(end == null) {
            return new GoalListItem(itemName, desc, priority);
        }
        else if(start == null) {
            return new GoalListItem(itemName, desc, end, priority);
        }
        else {
            return new GoalListItem(itemName, desc, start, end, priority);
        }
    }

    private void deleteList() {
        this.listDriver.deleteList(listDriver.getGoalList().getListName(), ListType.GOAL);
        goalListLabel.setText("No list found!");
        this.loadGoalList();
        this.loadGoalListItems();
    }

    private void deleteSelectedItem() {
        String itemToDelete = getSelectedValueString();
        Object[] paths = getCleanPath();
        Object[] splicedPaths = Arrays.copyOfRange(paths, 1, paths.length);

        this.listDriver.getGoalList().deleteGoalListItem(itemToDelete, splicedPaths);
        this.loadGoalListItems();
    }

    private void toggleSelectedItem() {
        String itemToComplete = getSelectedValueString();
        Object[] paths = getCleanPath();
        Object[] splicedPaths = Arrays.copyOfRange(paths, 1, paths.length);
        GoalListItem selectedItem = listDriver.getGoalList().getGoalListItem(itemToComplete, splicedPaths);
        selectedItem.toggleComplete();

        this.loadGoalListItems();
    }

    private String getSelectedValueString() {
        if (goalListTree.getLastSelectedPathComponent() != null) {
            // In case item has completed text
            String rawString =  goalListTree.getLastSelectedPathComponent().toString();
            int indexOfComplete = rawString.indexOf("COMPLETE");
            if (indexOfComplete == -1) {
                return rawString.trim();
            }
            return rawString.substring(0, indexOfComplete).trim();
        }
        return "";
    }

    // Cleans the paths so that they don't have COMPLETE
    private Object[] getCleanPath() {
        Object[] paths = goalListTree.getSelectionPath().getPath();
        for(int i = 0; i < paths.length; i++) {
            String rawString =  paths[i].toString();
            int indexOfComplete = rawString.indexOf("COMPLETE");
            if (indexOfComplete == -1) {
                paths[i] = rawString.trim();
            }
            else {
                paths[i] = rawString.substring(0, indexOfComplete).trim();
            }
        }
        return paths;
    }

    private void loadGoalList() {
        if(this.listDriver.getGoalList() != null){
            goalListLabel.setText("Goal List: " + this.listDriver.getGoalList().getListName());
            addListButton.setEnabled(false);
            deleteListButton.setEnabled(true);
            addItemButton.setEnabled(true);
        }
        else{
            goalListLabel.setText("No list found!");
            addListButton.setEnabled(true);
            deleteListButton.setEnabled(false);
            addItemButton.setEnabled(false);
            deleteItemButton.setEnabled(false);
        }
    }

    private void loadGoalListItems() {
        root.removeAllChildren();
        root.setUserObject("empty");
        if (listDriver.getGoalList() != null) {
            root.setUserObject(listDriver.getGoalList().getListName());
            GoalList selectedGoalList = (GoalList) this.listDriver.getList(listDriver.getGoalList().getListName(), ListType.GOAL);
            ArrayList<ListItem> itemList = selectedGoalList.getItemList();
            this.createNodes(root, itemList);
        }
        recursiveReloadTreeModel(root);
        goalListTree.repaint();
    }

    private void recursiveReloadTreeModel(DefaultMutableTreeNode node) {
        DefaultTreeModel model = (DefaultTreeModel) goalListTree.getModel();
        model.reload(node);
        if(model.getChildCount(node) == 0) {
            return;
        }

        for(int i = 0; i < model.getChildCount(node); i++) {
            DefaultMutableTreeNode nextNode = (DefaultMutableTreeNode) model.getChild(node, i);
            recursiveReloadTreeModel(nextNode);
        }
    }

    // Recursively travels down goal list item connections and creates nodes for the JTree
    private void createNodes(DefaultMutableTreeNode parent, ArrayList<? extends ListItem> children) {
        if(children.size() == 0) {
            return;
        }
        for(int i = 0; i < children.size(); i++) {
            GoalListItem child = (GoalListItem) children.get(i);
            String nodeText;
            if (child.getStatus() == Status.COMPELTE) {
                nodeText = String.format("%-5s COMPLETE", child.getItemName());
            }
            else {
                nodeText = child.getItemName();
            }
            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(nodeText);
            parent.add(newNode);
            createNodes(newNode, child.getChildren());
        }
    }

    private void initPanel() {
        this.initComponents();
        this.initLayout();
        this.loadGoalList();
        this.loadGoalListItems();
    }

    private void initComponents() {
        goalListLabel = new JLabel("No list found!");

        itemNameLabel = new JLabel("<html>Name: </html>");
        itemDescLabel = new JLabel("<html>Description: </html>");
        itemPriorityLabel = new JLabel("<html>Priority: </html>");
        startDateLabel = new JLabel("<html>Completion Range Date Start: </html>");
        endDateLabel = new JLabel("<html>Completion Range Date End: </html>");

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


        root = new DefaultMutableTreeNode("empty");
        goalListTree = new JTree(root);
        goalListTree.addTreeSelectionListener(new TreeItemSelectedListener());

        itemListScrollPane = new JScrollPane();
        itemListScrollPane.setPreferredSize(new Dimension(250, 200));
        itemListScrollPane.getViewport().add(goalListTree);
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
                .addComponent(goalListLabel)
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
                    .addComponent(startDateLabel)
                    .addComponent(endDateLabel)
                    .addComponent(itemDescLabel)))
        );

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
            .addGap(15)
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(addListButton)
                .addComponent(deleteListButton))
            .addGap(15)
            .addComponent(goalListLabel)
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
                .addComponent(startDateLabel)
                .addGap(5)
                .addComponent(itemDescLabel)
                .addGap(30))
        );
    }

    private class AddListAction extends AbstractAction {
        private GoalListView GoalListView;

        private AddListAction(GoalListView GoalListView) {
            this.GoalListView = GoalListView;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            AddGoalListFrame addGoalListFrame = new AddGoalListFrame(GoalListView);
        }
    }

    private class DeleteListAction extends AbstractAction {
        private GoalListView goalListView;

        private DeleteListAction(GoalListView goalListView) {
            this.goalListView = goalListView;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(goalListView.listDriver.getGoalList() != null) {
                goalListView.deleteList();
            }
        }
    }

    private class AddItemAction extends AbstractAction {
        private GoalListView goalListView;

        private AddItemAction(GoalListView goalListView) {
            this.goalListView = goalListView;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if( goalListView.listDriver.getGoalList() != null) {
                AddGoalItemFrame addGoalItemFrame = new AddGoalItemFrame(goalListView);
            }
        }
    }

    private class DeleteItemAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(goalListTree.getLastSelectedPathComponent() != null) {
                deleteSelectedItem();
            }
        }
    }

    private class ToggleItemAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(goalListTree.getLastSelectedPathComponent() != null && !getSelectedValueString().equals(listDriver.getGoalList().getListName())) {
                toggleSelectedItem();
            }
        }
    }

    private class TreeItemSelectedListener implements TreeSelectionListener {
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) goalListTree.getLastSelectedPathComponent();
            if(selectedNode != null && !selectedNode.toString().equals("empty")) {
                if(selectedNode.toString().equals(listDriver.getGoalList().getListName())) {
                    deleteItemButton.setEnabled(false);
                    toggleItemButton.setEnabled(false);
                    return;
                }
                toggleItemButton.setEnabled(true);
                deleteItemButton.setEnabled(true);
                GoalListItem selectedItem = getSelectedItem();
                loadLabels(selectedItem);
            }
            else {
                // Reset labels if not targeting anything
                deleteItemButton.setEnabled(false);
                resetLabels();
            }
        }

        private GoalListItem getSelectedItem() {
            String selectedString = getSelectedValueString();
            GoalList selectedGoalList = (GoalList) listDriver.getList(listDriver.getGoalList().getListName(), ListType.GOAL);
            Object[] paths = getCleanPath();

            // Get rid of root directory in path
            Object[] splicedPaths = Arrays.copyOfRange(paths, 1, paths.length);
            return selectedGoalList.getGoalListItem(selectedString, splicedPaths);
        }

        private void loadLabels(GoalListItem selectedItem) {
            LocalDateTime startDate = selectedItem.getCompletionRangeStartDate();
            LocalDateTime endDate = selectedItem.getCompletionRangeEndDate();

            itemNameLabel.setText("<html>Name: " + selectedItem.getItemName() + "</html>");
            if(startDate != null){
                startDateLabel.setText(String.format("<html>Completion Range Date Start: %d-%d-%d </html>", startDate.getMonthValue(), startDate.getDayOfMonth(), startDate.getYear()));
            }
            if(endDate != null) {
                endDateLabel.setText(String.format("<html>Completion Range Date End: %d-%d-%d </html>", endDate.getMonthValue(), endDate.getDayOfMonth(), endDate.getYear()));
            }
            itemPriorityLabel.setText("<html>Priority: " + selectedItem.getItemPriority() + "</html>");
            itemDescLabel.setText("<html>Description: " + selectedItem.getDescription() + "</html>");
        }

        private void resetLabels() {
            itemNameLabel.setText("<html>Name: </html>");
            itemDescLabel.setText("<html>Description: </html>");
            itemPriorityLabel.setText("<html>Priority: </html>");
            startDateLabel.setText("<html>Completion Range Date Start: </html>");
            endDateLabel.setText("<html>Completion Range Date End: </html>");
        }
    }
}
