package com.tylerkv.ui.frames.creation;

import com.tylerkv.ui.views.ToDoListView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AddToDoListFrame extends JFrame {
    private ToDoListView ToDoListView;
    private JPanel container;
    private GroupLayout groupLayout;
    private JLabel listNameLabel;
    private JTextField listNameTextField;
    private JButton createButton;

    public AddToDoListFrame(ToDoListView ToDoListView) {
        this.ToDoListView = ToDoListView;
        this.initFrame();
        this.initPanel();
    }

    private void initFrame() {
        this.setTitle("Create Shopping List");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setPreferredSize(new Dimension(300,120));
        this.setLayout(new BorderLayout());
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
        private AddToDoListFrame addToDoListFrame;

        public CreateListAction(AddToDoListFrame addToDoListFrame) {
            this.addToDoListFrame = addToDoListFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: Add tooltip if null

            if(listNameTextField.getText() != null) {
                ToDoListView.createList(listNameTextField.getText());
                addToDoListFrame.dispose();
            }
        }
    }

}
