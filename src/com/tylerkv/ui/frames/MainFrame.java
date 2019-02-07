package com.tylerkv.ui.frames;

import com.tylerkv.application.utilities.ListDriver;
import com.tylerkv.ui.Driver;
import com.tylerkv.ui.views.MainView;
import com.tylerkv.ui.views.SideBarView;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    private Driver guiDriver;
    private ListDriver listDriver;
    private MainView mainView;
    private SideBarView sideBarView;

    public MainFrame(Driver guiDriver, ListDriver listDriver) {
        this.guiDriver = guiDriver;
        this.listDriver = listDriver;
        this.initFrame();
        this.initViews();
    }

    public void showPanel(String panelName) {
        mainView.showPanel(panelName);
    }

    private void initFrame() {
        this.setTitle("Checklists Application");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
    }

    private void initViews() {
        mainView = new MainView(this, listDriver);
        sideBarView = new SideBarView(this);

        this.add(sideBarView, BorderLayout.WEST);
        this.add(mainView, BorderLayout.CENTER);
        this.pack();
    }
}
