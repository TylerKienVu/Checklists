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
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class HomeView extends JPanel {
    private ListDriver listDriver;
    private MainView parentView;
    private GroupLayout groupLayout;
    private ChartPanel chartPanel;
    private JButton refreshButton;

    public HomeView(MainView parentView, ListDriver listDriver) {
        this.listDriver = listDriver;
        this.parentView = parentView;
        this.initPanel();
    }

    public void updateChart() {
        listDriver.updateChart();
        chartPanel.repaint();
    }

    private void initPanel() {
        this.initComponents();
        this.initLayout();
    }

    private void initLayout() {
        groupLayout = new GroupLayout(this);
        this.setLayout(groupLayout);
//        groupLayout.linkSize(SwingConstants.HORIZONTAL, addListButton, deleteListButton, addItemButton, deleteItemButton);
        this.drawLayout();
    }

    private void initComponents() {
        chartPanel = listDriver.createChart();

//        refreshButton = new JButton("Refresh");
//        refreshButton.setActionCommand("REFRESH");
//        refreshButton.addActionListener(new RefreshAction());
    }

    private void drawLayout() {
        groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
            .addComponent(chartPanel));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
            .addComponent(chartPanel));
    }

//    private class RefreshAction extends AbstractAction {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            chartPanel = listDriver.createChart();
//        }
//    }
}
