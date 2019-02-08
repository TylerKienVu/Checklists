package com.tylerkv.ui.frames.creation;

import com.tylerkv.ui.views.ToDoListView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AddToDoListFrame extends JFrame {
    private ToDoListView ToDoListView;
    private JPanel container;
    private GroupLayout groupLayout;
    private JLabel listNameLabel;
    private JLabel priorityLabel;
    private JTextField listNameTextField;
    private JSpinner numberSpinner;
    private JButton createButton;

    public AddToDoListFrame(ToDoListView ToDoListView) {
        this.ToDoListView = ToDoListView;
        this.initFrame();
        this.initPanel();
    }

    private void initFrame() {
        this.setTitle("Create Todo List");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setPreferredSize(new Dimension(300,150));
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

        SpinnerModel model = new SpinnerNumberModel(0, 0, 1, 0.01);
        numberSpinner = new JSpinner(model);
        numberSpinner.setMaximumSize(new Dimension(50,5));
        priorityLabel = new JLabel("Priority");

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
            .addGroup(groupLayout.createSequentialGroup()
                .addComponent(priorityLabel)
                .addComponent(numberSpinner))
            .addComponent(createButton)
        );

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(listNameLabel)
                .addComponent(listNameTextField))
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(priorityLabel)
                .addComponent(numberSpinner))
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
                ToDoListView.createList(listNameTextField.getText(), (double)numberSpinner.getValue());
                addToDoListFrame.dispose();
            }
        }
    }

}
