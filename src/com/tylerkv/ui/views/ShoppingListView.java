package com.tylerkv.ui.views;

import com.tylerkv.application.baseobjects.ListItem;
import com.tylerkv.application.listitems.ShoppingListItem;
import com.tylerkv.application.lists.ShoppingList;
import com.tylerkv.application.utilities.ItemDetails;
import com.tylerkv.application.utilities.ListDriver;
import com.tylerkv.application.utilities.ListType;
import com.tylerkv.application.utilities.Status;
import com.tylerkv.ui.frames.creation.AddShoppingItemFrame;
import com.tylerkv.ui.frames.creation.AddShoppingListFrame;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ShoppingListView extends JPanel {
    private ListDriver listDriver;
    private MainView parentView;
    private GroupLayout groupLayout;
    private JButton addListButton;
    private JButton deleteListButton;
    private JButton addItemButton;
    private JButton deleteItemButton;
    private JButton completeItemButton;
    private JComboBox<String> listsComboBox;
    private JScrollPane itemListScrollPane;
    private JList itemList;
    private DefaultListModel listModel;
    private JLabel itemNameLabel;
    private JLabel itemQuantityLabel;
    private JLabel itemDescLabel;

    public ShoppingListView(MainView parentView, ListDriver listDriver) {
        this.listDriver = listDriver;
        this.parentView = parentView;
        this.initPanel();
    }

    public void createList(String listName) {
        try {
            this.listDriver.addShoppingList(listName);
            this.loadShoppingLists();
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    public void createItem(String itemName, String desc, int quantity) {
        try {
            ShoppingListItem itemToAdd = new ShoppingListItem(new ItemDetails(itemName, desc), quantity);
            this.listDriver.addItemToList(listsComboBox.getSelectedItem().toString(), ListType.SHOPPING, itemToAdd);
            this.loadShoppingListItems();
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    private void deleteSelectedList() {
        String listToDelete = (String) listsComboBox.getSelectedItem();
        this.listDriver.deleteList(listToDelete, ListType.SHOPPING);
        this.loadShoppingLists();
        this.loadShoppingListItems();
    }

    private void deleteSelectedItem() {
        String itemToDelete = getSelectedItemString();

        this.listDriver.deleteItemFromList(listsComboBox.getSelectedItem().toString(), ListType.SHOPPING,itemToDelete);
        this.loadShoppingListItems();
    }

    private void toggleSelectedItem() {
        String itemToComplete = getSelectedItemString();

        this.listDriver.toggleItemComplete(listsComboBox.getSelectedItem().toString(), ListType.SHOPPING, itemToComplete);
        this.loadShoppingListItems();
    }

    private String getSelectedItemString() {
        String rawString = (String)itemList.getSelectedValue();
        String splitString = rawString.split("-")[0];
        return splitString.trim();
    }

    private void loadShoppingLists() {
        listsComboBox.removeAllItems();
        ArrayList<ShoppingList> shoppingLists = this.listDriver.getShoppingLists();
        for(int i = 0; i < shoppingLists.size(); i++) {
            listsComboBox.addItem(shoppingLists.get(i).getListName());
        }
    }

    private void loadShoppingListItems() {
        listModel.removeAllElements();
        if (listsComboBox.getSelectedItem() != null) {
            ShoppingList selectedShoppingList = (ShoppingList) this.listDriver.getList(listsComboBox.getSelectedItem().toString(), ListType.SHOPPING);
            ArrayList<ListItem> itemList = selectedShoppingList.getItemList();
            for(int i = 0; i < itemList.size(); i++) {
                ShoppingListItem currentItem = (ShoppingListItem) itemList.get(i);
                String stringToAdd;
                if (currentItem.getStatus() == Status.COMPELTE) {
                    stringToAdd = String.format("%-20s - Qty: %-5s COMPLETE", currentItem.getItemName(), currentItem.getQuantity());
                }
                else {
                    stringToAdd = String.format("%-20s - Qty: %s", currentItem.getItemName(), currentItem.getQuantity());
                }
                listModel.addElement(stringToAdd);
            }
        }
    }

    private void initPanel() {
        this.initComponents();
        this.initLayout();
        this.loadShoppingLists();
        this.loadShoppingListItems();
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

        completeItemButton = new JButton("Toggle Complete");
        completeItemButton.setActionCommand("COMPLETE");
        completeItemButton.addActionListener(new ToggleItemAction());

        listsComboBox = new JComboBox<>();
        listsComboBox.setMaximumSize(new Dimension(400,5));
        listsComboBox.addActionListener(new NewListSelectedAction());

        itemList = new JList();
        listModel = new DefaultListModel();
        itemList.setModel(listModel);
        itemList.addListSelectionListener(new NewListItemSelectedListener());

        itemListScrollPane = new JScrollPane();
        itemListScrollPane.setPreferredSize(new Dimension(250, 300));
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
                    .addComponent(completeItemButton))
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
                    .addComponent(deleteItemButton)
                    .addGap(5)
                    .addComponent(completeItemButton)))
            .addGroup(groupLayout.createSequentialGroup()
                .addGap(20)
                .addComponent(itemNameLabel)
                .addGap(5)
                .addComponent(itemQuantityLabel)
                .addGap(5)
                .addComponent(itemDescLabel)
                .addGap(30))
        );
    }

    private class AddListAction extends AbstractAction {
        private ShoppingListView shoppingListView;

        private AddListAction(ShoppingListView shoppingListView) {
            this.shoppingListView = shoppingListView;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            AddShoppingListFrame addShoppingListFrame = new AddShoppingListFrame(shoppingListView);
        }
    }

    private class DeleteListAction extends AbstractAction {
        private ShoppingListView shoppingListView;

        private DeleteListAction(ShoppingListView shoppingListView) {
            this.shoppingListView = shoppingListView;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(shoppingListView.listsComboBox.getSelectedItem() != null) {
                shoppingListView.deleteSelectedList();
            }
        }
    }

    private class AddItemAction extends AbstractAction {
        private ShoppingListView shoppingListView;

        private AddItemAction(ShoppingListView shoppingListView) {
            this.shoppingListView = shoppingListView;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(listsComboBox.getSelectedItem() != null) {
                AddShoppingItemFrame addShoppingItemFrame = new AddShoppingItemFrame(shoppingListView);
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
                loadShoppingListItems();
            }
        }
    }

    private class NewListItemSelectedListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(!e.getValueIsAdjusting() && itemList.getSelectedValue() != null) {
                String selectedItemString = getSelectedItemString();

                ShoppingList selectedShoppingList = (ShoppingList) listDriver.getList(listsComboBox.getSelectedItem().toString(), ListType.SHOPPING);
                ShoppingListItem selectedItem = (ShoppingListItem) selectedShoppingList.getItem(selectedItemString);

                itemNameLabel.setText("<html>Name: " + selectedItem.getItemName() + "</html>");
                itemDescLabel.setText("<html>Description: " + selectedItem.getDescription() + "</html>");
                itemQuantityLabel.setText("<html>Quantity: " + selectedItem.getQuantity()+ "</html>");
            }
            else {
                // Clear the labels if nothing selected
                itemNameLabel.setText("<html>Name: </html>");
                itemDescLabel.setText("<html>Description: </html>");
                itemQuantityLabel.setText("<html>Quantity: </html>");
            }
        }
    }
}
