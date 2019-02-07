package com.tylerkv.ui.views;

// This view will hold two different panels
// one panel is the static side bar
// the other is the dynamic card layout list view

import javax.swing.*;
import java.awt.*;

public class MainView extends JPanel {
    private CardLayout cardLayout;
    private JFrame parentFrame;
    private SideBarView sideBarView;

    public MainView(JFrame parentFrame) {
        this.parentFrame = parentFrame;
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
    }

    public void showPanel(String panelName) {
        cardLayout.show(this, panelName);
        parentFrame.pack();
    }

}
