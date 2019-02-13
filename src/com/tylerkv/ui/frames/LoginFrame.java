package com.tylerkv.ui.frames;

import com.tylerkv.ui.Driver;
import com.tylerkv.ui.frames.creation.AddToDoListFrame;
import com.tylerkv.ui.views.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

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

        //Link "Register" key to create action
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),"CREATE");
        this.getRootPane().getActionMap().put("CREATE", new LoginAction());
    }

    private void initPanel() {
        loginView = new LoginView(this);
        this.add(loginView);
        this.pack();
    }

    private class LoginAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            loginView.login();
        }
    }
}
