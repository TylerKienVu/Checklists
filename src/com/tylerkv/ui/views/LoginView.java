package com.tylerkv.ui.views;

import com.tylerkv.ui.frames.LoginFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

// TODO: implement tool tip show when password or username blank

public class LoginView extends JPanel {
    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private JButton loginButton;
    private GroupLayout groupLayout;
    private LoginFrame parentFrame;

    public LoginView(LoginFrame parentFrame) {
        this.parentFrame = parentFrame;
        initLoginPanel();
    }

    public void login() {
        if (!usernameTextField.getText().equals("") || !passwordTextField.getText().equals("")) {
            parentFrame.login(usernameTextField.getText(), passwordTextField.getText());
        }
    }

    private void initLoginPanel() {
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        titleLabel = new JLabel("Checklists Application", JLabel.CENTER);
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), titleLabel.getFont().getStyle(), 30));

        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");

        usernameTextField = new JTextField();
        passwordTextField = new JTextField();

        loginButton = new JButton("Login");
        loginButton.setActionCommand("LOGIN");
        loginButton.addActionListener(new ClickAction());
    }

    private void initLayout() {
        groupLayout = new GroupLayout(this);
        this.setLayout(groupLayout);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        this.drawLayout();
    }

    private void drawLayout() {
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(titleLabel)
                .addGroup(groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(usernameLabel)
                                .addComponent(passwordLabel))
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(usernameTextField)
                                .addComponent(passwordTextField)))
                .addComponent(loginButton));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(usernameLabel)
                        .addComponent(usernameTextField))
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(passwordLabel)
                        .addComponent(passwordTextField))
                .addComponent(loginButton));
    }

    private class ClickAction extends AbstractAction{

        @Override
        public void actionPerformed(ActionEvent e) {
            login();
        }
    }
}
