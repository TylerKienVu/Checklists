package com.tylerkv.ui.frames;

import com.tylerkv.ui.Driver;
import com.tylerkv.ui.views.LoginView;

import javax.swing.*;

public class LoginFrame extends JFrame {
    private Driver driver;
    private LoginView loginView;

    public LoginFrame(Driver driver) {
        this.driver = driver;
        initLoginPanel();
    }

    public void login(String username, String password) {
        driver.login(username, password);
    }

    private void initLoginPanel() {
        this.initFrame();
        this.initPanel();
    }

    private void initFrame() {
        this.setTitle("Checklists Application");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }

    private void initPanel() {
        loginView = new LoginView(this);
        this.add(loginView);
        this.pack();
    }
}
