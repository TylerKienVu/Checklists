package com.tylerkv.ui.views;

// This view will hold two different panels
// one panel is the static side bar
// the other is the dynamic card layout list view

import com.tylerkv.application.utilities.ListDriver;

import javax.swing.*;
import java.awt.*;

public class MainView extends JPanel {
    private CardLayout cardLayout;
    private JFrame parentFrame;
    private ShoppingListView shoppingListView;
    private ListDriver listDriver;

    public MainView(JFrame parentFrame, ListDriver listDriver) {
        this.parentFrame = parentFrame;
        this.listDriver = listDriver;
        initMainView();
    }

    private void initMainView() {
        this.initLayout();
        this.initPanels();
    }

    private void initLayout() {
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
    }

    private void initPanels() {
        // Init list details panels here
        shoppingListView = new ShoppingListView(this, listDriver);

        this.add(shoppingListView);
    }

    public void showPanel(String panelName) {
        cardLayout.show(this, panelName);
        parentFrame.pack();
    }

}
