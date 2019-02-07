package com.tylerkv.ui;

import com.tylerkv.application.utilities.ListDriver;
import com.tylerkv.application.utilities.ListUser;
import com.tylerkv.ui.frames.LoginFrame;
import com.tylerkv.ui.frames.MainFrame;

// TODO: Save the user data to disk
// TODO: Check if login matches a user and then grab data for that user

public class Driver {
    private LoginFrame loginFrame;
    private MainFrame mainFrame;

    //Local Storage (This is temp, should be stored to file in future);
    private ListUser currentUser;
    private ListDriver listDriver;

    public Driver() {
    }

    public void start() {
        loginFrame = new LoginFrame(this);
    }

    public void test() {
        mainFrame = new MainFrame(this);
    }

    public void login(String username, String password) {
        this.initAppInstance(username, password);
        loginFrame.dispose();
        mainFrame = new MainFrame(this);
    }

    private void initAppInstance(String username, String password) {
        currentUser = new ListUser(username, password);
        listDriver = new ListDriver(currentUser);
    }
}
