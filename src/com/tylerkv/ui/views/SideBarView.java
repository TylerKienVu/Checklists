package com.tylerkv.ui.views;

import com.tylerkv.ui.frames.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// TODO: Attach listeners to buttons

public class SideBarView extends JPanel {
    private JLabel profilePictureLabel;
    private JButton shoppingButton;
    private JButton todoButton;
    private JButton goalButton;
    private JButton teamButton;
    private GroupLayout groupLayout;
    private MainFrame parentFrame;

    public SideBarView(MainFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.initSideBarView();
    }

    public void initSideBarView() {
        this.initPanel();
        this.initComponents();
        this.initLayout();
    }

    private void initPanel() {
        Border panelBorder = BorderFactory.createMatteBorder(0,0,0,1,Color.BLACK);
        setBorder(panelBorder);
    }

    private void initComponents() {
        initProfilePicture();
        shoppingButton = new JButton("Shopping Lists");
        shoppingButton.setActionCommand("SHOP");
        shoppingButton.addActionListener(new ShopClickAction());

        todoButton = new JButton("Todo Lists");
        todoButton.setActionCommand("TODO");
        todoButton.addActionListener(new ToDoClickAction());

        goalButton = new JButton("Goal Lists");
        goalButton.setActionCommand("GOAL");

        teamButton = new JButton("Team Lists");
        teamButton.setActionCommand("TEAM");
    }

    private void initProfilePicture() {
        profilePictureLabel = new JLabel();
        Border profileBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
        profilePictureLabel.setBorder(profileBorder);
        profilePictureLabel.setSize(100,100);

        // Process profile picture to fit label
        BufferedImage rawImage;
        try {
            rawImage = ImageIO.read(new File("src/resources/default-profile-pic.png"));
            // Resize buffered image
            Image resizedImage = rawImage.getScaledInstance(profilePictureLabel.getWidth(), profilePictureLabel.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon profilePicture = new ImageIcon(resizedImage);
            profilePictureLabel.setIcon(profilePicture);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void initLayout() {
        groupLayout = new GroupLayout(this);
        this.setLayout(groupLayout);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.linkSize(SwingConstants.HORIZONTAL, shoppingButton, todoButton, goalButton, teamButton);
        this.drawLayout();
    }

    private void drawLayout() {
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(profilePictureLabel)
            .addGap(50)
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(shoppingButton)
                .addGap(25)
                .addComponent(todoButton)
                .addGap(25)
                .addComponent(goalButton)
                .addGap(25)
                .addComponent(teamButton)
            ));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
            .addComponent(profilePictureLabel)
            .addGap(50)
            .addComponent(shoppingButton)
            .addGap(25)
            .addComponent(todoButton)
            .addGap(25)
            .addComponent(goalButton)
            .addGap(25)
            .addComponent(teamButton));
    }

    private class ShopClickAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            parentFrame.showPanel("SHOPPING");
        }
    }

    private class ToDoClickAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            parentFrame.showPanel("TODO");
        }
    }
}
