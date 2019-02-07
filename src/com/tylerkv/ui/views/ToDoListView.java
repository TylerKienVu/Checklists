package com.tylerkv.ui.views;

import com.tylerkv.application.baseobjects.ListItem;
import com.tylerkv.application.listitems.ToDoListItem;
import com.tylerkv.application.lists.ToDoList;
import com.tylerkv.application.utilities.ListDriver;
import com.tylerkv.application.utilities.ListType;
import com.tylerkv.ui.frames.creation.AddShoppingItemFrame;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ToDoListView extends JPanel {
    private ListDriver listDriver;
    private MainView parentView;
    private GroupLayout groupLayout;
    private JButton addListButton;
    private JButton deleteListButton;
    private JButton addItemButton;
    private JButton deleteItemButton;
    private JComboBox<String> listsComboBox;
    private JScrollPane itemListScrollPane;
    private JList itemList;
    private DefaultListModel listModel;
    private JLabel itemNameLabel;
    private JLabel itemQuantityLabel;
    private JLabel itemDescLabel;

    public ToDoListView(MainView parentView, ListDriver listDriver) {
        this.listDriver = listDriver;
        this.parentView = parentView;
        this.initPanel();
    }

    public void createList(String listName) {
        try {
            this.listDriver.addToDoList(listName);
            this.loadToDoLists();
        }
        catch (IllegalArgumentException e) {
            return;
        }
    }

    public void createItem(String itemName, String desc, int quantity) {
        try {
            ToDoListItem itemToAdd = new ToDoListItem(itemName, desc, quantity);
            this.listDriver.addItemToList(listsComboBox.getSelectedItem().toString(), ListType.TODO, itemToAdd);
            this.loadToDoListItems();
        }
        catch (IllegalArgumentException e) {
            return;
        }
    }

    private void deleteSelectedList() {
        String listToDelete = (String) listsComboBox.getSelectedItem();
        this.listDriver.deleteList(listToDelete, ListType.TODO);
        this.loadToDoLists();
        this.loadToDoListItems();
    }

    private void deleteSelectedItem() {
        // Get rid of Qty part of string
        String itemString = (String)itemList.getSelectedValue();
        String itemToDelete = itemString.split(" ")[0];

        this.listDriver.deleteItemFromList(listsComboBox.getSelectedItem().toString(), ListType.TODO,itemToDelete);
        this.loadToDoListItems();
    }

    private void loadToDoLists() {
        listsComboBox.removeAllItems();
        ArrayList<ToDoList> ToDoLists = this.listDriver.getToDoLists();
        for(int i = 0; i < ToDoLists.size(); i++) {
            listsComboBox.addItem(ToDoLists.get(i).getListName());
        }
    }

    private void loadToDoListItems() {
        listModel.removeAllElements();
        if (listsComboBox.getSelectedItem() != null) {
            ToDoList selectedToDoList = (ToDoList) this.listDriver.getList(listsComboBox.getSelectedItem().toString(), ListType.TODO);
            ArrayList<ListItem> itemList = selectedToDoList.getItemList();
            for(int i = 0; i < itemList.size(); i++) {
                ToDoListItem currentItem = (ToDoListItem) itemList.get(i);
                String stringToAdd = String.format("%-20s Qty: %s", currentItem.getItemName(), currentItem.getQuantity());
                listModel.addElement(stringToAdd);
            }
        }
    }

    private void initPanel() {
        this.initComponents();
        this.initLayout();
        this.loadToDoLists();
        this.loadToDoListItems();
    }

    private void initComponents() {
        itemNameLabel = new JLabel("<html>Name: </html>");
        itemDescLabel = new JLabel("<html>Description: </html>");
        itemQuantityLabel = new JLabel("<html>Quantity: </html>");

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

        listsComboBox = new JComboBox<>();
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
                    .addComponent(deleteItemButton))
                .addGap(10))
            .addGroup(groupLayout.createSequentialGroup()
                .addGap(10)
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(itemNameLabel)
                    .addComponent(itemQuantityLabel)
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
                    .addComponent(deleteItemButton)))
            .addGroup(groupLayout.createSequentialGroup()
                .addComponent(itemNameLabel)
                .addGap(5)
                .addComponent(itemQuantityLabel)
                .addGap(5)
                .addComponent(itemDescLabel)
                .addGap(30))
        );
    }

    private class AddListAction extends AbstractAction {
        private com.tylerkv.ui.views.ToDoListView ToDoListView;

        private AddListAction(com.tylerkv.ui.views.ToDoListView ToDoListView) {
            this.ToDoListView = ToDoListView;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            AddToDoListFrame addToDoListFrame = new AddToDoListFrame(ToDoListView);
        }
    }

    private class DeleteListAction extends AbstractAction {
        private com.tylerkv.ui.views.ToDoListView ToDoListView;

        private DeleteListAction(com.tylerkv.ui.views.ToDoListView ToDoListView) {
            this.ToDoListView = ToDoListView;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(ToDoListView.listsComboBox.getSelectedItem() != null) {
                ToDoListView.deleteSelectedList();
            }
        }
    }

    private class AddItemAction extends AbstractAction {
        private com.tylerkv.ui.views.ToDoListView ToDoListView;

        private AddItemAction(com.tylerkv.ui.views.ToDoListView ToDoListView) {
            this.ToDoListView = ToDoListView;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(listsComboBox.getSelectedItem() != null) {
                AddShoppingItemFrame addShoppingItemFrame = new AddShoppingItemFrame(ToDoListView);
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

    private class NewListSelectedAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(listsComboBox.getSelectedItem() != null) {
                loadToDoListItems();
            }
        }
    }

    private class NewListItemSelectedListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(!e.getValueIsAdjusting() && itemList.getSelectedValue() != null) {
                //Process string and remove Qty
                String selectedString = (String) itemList.getSelectedValue();
                String selectedItemString = selectedString.split(" ")[0];

                ToDoList selectedToDoList = (ToDoList) listDriver.getList(listsComboBox.getSelectedItem().toString(), ListType.TODO);
                ToDoListItem selectedItem = (ToDoListItem) selectedToDoList.getItem(selectedItemString);

                itemNameLabel.setText("<html>Name: " + selectedItem.getItemName() + "</html>");
                itemDescLabel.setText("<html>Description: " + selectedItem.getDescription() + "</html>");
                itemQuantityLabel.setText("<html>Quantity: " + selectedItem.getQuantity()+ "</html>");
            }
        }
    }
}
