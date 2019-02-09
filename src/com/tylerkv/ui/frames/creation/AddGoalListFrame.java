package com.tylerkv.ui.frames.creation;

import com.tylerkv.ui.views.GoalListView;
import com.tylerkv.ui.views.ShoppingListView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AddGoalListFrame extends JFrame {
    private GoalListView goalListView;
    private JPanel container;
    private GroupLayout groupLayout;
    private JLabel listNameLabel;
    private JTextField listNameTextField;
    private JButton createButton;

    public AddGoalListFrame(GoalListView goalListView) {
        this.goalListView = goalListView;
        this.initFrame();
        this.initPanel();
    }

    private void initFrame() {
        this.setTitle("Create Goal List");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setPreferredSize(new Dimension(300,120));
        this.setLayout(new BorderLayout());

        //Link "Register" key to create action
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),"CREATE");
        this.getRootPane().getActionMap().put("CREATE", new CreateListAction(this));
    }

    private void initPanel() {
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        container = new JPanel();

        listNameLabel = new JLabel("List Name");
        listNameTextField = new JTextField();

        createButton = new JButton("Create List");
        createButton.setActionCommand("CREATE LIST");
        createButton.addActionListener(new CreateListAction(this));
    }

    private void initLayout() {
        groupLayout = new GroupLayout(container);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        container.setLayout(groupLayout);
        this.add(container, BorderLayout.CENTER);
        this.drawLayout();
        this.pack();
    }

    private void drawLayout() {
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
            .addGroup(groupLayout.createSequentialGroup()
                .addComponent(listNameLabel)
                .addComponent(listNameTextField))
            .addComponent(createButton)
        );

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(listNameLabel)
                .addComponent(listNameTextField))
            .addComponent(createButton)
        );

    }

    private class CreateListAction extends AbstractAction {
        private AddGoalListFrame addGoalListFrame;

        public CreateListAction(AddGoalListFrame addGoalListFrame) {
            this.addGoalListFrame = addGoalListFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: Add tooltip if null

            if(!listNameTextField.getText().equals("")) {
                goalListView.createList(listNameTextField.getText());
                addGoalListFrame.dispose();
            }
        }
    }

}
