package com.tylerkv.ui.views;

import com.tylerkv.application.lists.ShoppingList;
import com.tylerkv.application.utilities.ListDriver;

import javax.swing.*;
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
    private JComboBox<String> listsComboBox;
    private JScrollPane itemListScrollPane;
    private JList itemList;
    private DefaultListModel listModel;

    public ShoppingListView(MainView parentView, ListDriver listDriver) {
        this.listDriver = listDriver;
        this.parentView = parentView;
        this.initPanel();
    }

    private void initPanel() {
        this.initComponents();
        this.initLayout();
        this.loadShoppingLists();
    }

    private void initComponents() {
        addListButton = new JButton("Add List");
        addListButton.setActionCommand("ADD LIST");

        deleteListButton = new JButton("Delete List");
        deleteListButton.setActionCommand("DELETE LIST");

        addItemButton = new JButton("Add Item");
        addItemButton.setActionCommand("ADD ITEM");

        deleteItemButton = new JButton("Delete Item");
        deleteItemButton.setActionCommand("DELETE ITEM");

        listsComboBox = new JComboBox<>();

        itemList = new JList();
        listModel = new DefaultListModel();
        itemList.setModel(listModel);


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
        );
    }

    private void loadShoppingLists() {
        ArrayList<ShoppingList> shoppingLists = this.listDriver.getShoppingLists();
        for(int i = 0; i < shoppingLists.size(); i++) {
            listsComboBox.addItem(shoppingLists.get(i).getListName());
        }
    }

    private class AddListAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
