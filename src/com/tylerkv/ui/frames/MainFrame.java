package com.tylerkv.ui.frames;

import com.tylerkv.ui.Driver;
import com.tylerkv.ui.views.MainView;
import com.tylerkv.ui.views.SideBarView;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    private Driver driver;

    public MainFrame(Driver driver) {
        this.driver = driver;
        this.initFrame();
        this.initViews();
    }

    private void initFrame() {
        this.setTitle("Checklists Application");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLayout(new GridLayout(1,2));
        this.setResizable(false);
    }

    private void initViews() {
        MainView mainView = new MainView(this);
        SideBarView sideBarView = new SideBarView(this);

        this.add(sideBarView);
        this.add(mainView);
        this.pack();
    }
}
