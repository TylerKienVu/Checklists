package com.tylerkv.ui.frames.creation;

import com.toedter.calendar.JCalendar;
import com.tylerkv.ui.views.TeamListView;
import com.tylerkv.ui.views.ToDoListView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AddTeamItemFrame extends JFrame {
    private TeamListView teamListView;
    private JPanel container;
    private GroupLayout groupLayout;
    private JLabel itemNameLabel;
    private JLabel itemDescLabel;
    private JLabel priorityLabel;
    private JLabel endDateLabel;
    private JLabel assignedUsersLabel;
    private JTextField itemNameTextField;
    private JTextField itemDescTextField;
    private JTextField assignedUsersTextField;
    private JSpinner numberSpinner;
    private JButton createButton;
    private JCalendar endDateCalendar;

    public AddTeamItemFrame(TeamListView teamListView) {
        this.teamListView = teamListView;
        this.initFrame();
        this.initPanel();
    }

    private void initFrame() {
        this.setTitle("Create Todo List Item");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setPreferredSize(new Dimension(600,800));
        this.setLayout(new BorderLayout());

        //Link "Register" key to create action
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),"CREATE");
        this.getRootPane().getActionMap().put("CREATE", new CreateItemAction(this));
    }

    private void initPanel() {
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        container = new JPanel();

        itemNameLabel = new JLabel("Item Name");
        itemDescLabel = new JLabel("Item Description");
        priorityLabel = new JLabel("Priority");
        endDateLabel = new JLabel("Deadline");
        assignedUsersLabel = new JLabel("Assigned Users");

        itemNameTextField = new JTextField();
        itemDescTextField = new JTextField();
        assignedUsersTextField = new JTextField();

        createButton = new JButton("Create Item");
        createButton.setActionCommand("CREATE ITEM");
        createButton.addActionListener(new CreateItemAction(this));

        SpinnerModel model = new SpinnerNumberModel(0, 0, 1, .01);
        numberSpinner = new JSpinner(model);
        numberSpinner.setMaximumSize(new Dimension(50,5));

        endDateCalendar = new JCalendar();

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
        groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(itemNameLabel)
                .addComponent(itemDescLabel)
                .addComponent(priorityLabel)
                .addComponent(assignedUsersLabel)
                .addComponent(endDateLabel))
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(itemNameTextField)
                .addComponent(itemDescTextField)
                .addComponent(numberSpinner)
                .addComponent(assignedUsersTextField)
                .addComponent(endDateCalendar)
                .addComponent(createButton))
            .addGap(40)
        );

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(itemNameLabel)
                .addComponent(itemNameTextField))
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(itemDescLabel)
                .addComponent(itemDescTextField))
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(priorityLabel)
                .addComponent(numberSpinner))
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(assignedUsersLabel)
                .addComponent(assignedUsersTextField))
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(endDateLabel)
                .addComponent(endDateCalendar))
            .addGap(10)
            .addComponent(createButton)
        );
    }

    private class CreateItemAction extends AbstractAction {
        private AddTeamItemFrame addToDoItemFrame;

        public CreateItemAction(AddTeamItemFrame addToDoItemFrame) {
            this.addToDoItemFrame = addToDoItemFrame;
        }

        /** @noinspection deprecation*/
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: Add tooltip if null

            if(!itemNameTextField.getText().equals("")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz uuuu");
                LocalDateTime endDate = LocalDateTime.parse(endDateCalendar.getDate().toString(), formatter);

                // TODO: Figure out why date days are not making sense

                teamListView.createItem(itemNameTextField.getText()
                        , itemDescTextField.getText(), endDate
                        , (double) numberSpinner.getValue(), assignedUsersTextField.getText());
                addToDoItemFrame.dispose();
            }
        }
    }
}
