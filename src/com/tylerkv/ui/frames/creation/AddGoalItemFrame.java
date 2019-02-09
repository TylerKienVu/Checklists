package com.tylerkv.ui.frames.creation;

import com.toedter.calendar.JCalendar;
import com.tylerkv.ui.views.GoalListView;
import com.tylerkv.ui.views.ToDoListView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AddGoalItemFrame extends JFrame {
    private GoalListView goalListView;
    private JPanel container;
    private GroupLayout groupLayout;
    private JLabel itemNameLabel;
    private JLabel itemDescLabel;
    private JLabel priorityLabel;
    private JLabel startDateLabel;
    private JLabel endDateLabel;
    private JLabel addStartDateLabel;
    private JLabel addEndDateLabel;
    private JTextField itemNameTextField;
    private JTextField itemDescTextField;
    private JSpinner numberSpinner;
    private JButton createButton;
    private JCalendar startDateCalendar;
    private JCalendar endDateCalendar;
    private JCheckBox startCheck;
    private JCheckBox endCheck;

    public AddGoalItemFrame(GoalListView goalListView) {
        this.goalListView = goalListView;
        this.initFrame();
        this.initPanel();
    }

    private void initFrame() {
        this.setTitle("Create Goal List Item");
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
        startDateLabel = new JLabel("Completion Range Start Date");
        endDateLabel = new JLabel("Completion Range End Date");
        addStartDateLabel = new JLabel("Add Start Date");
        addEndDateLabel = new JLabel("Add End Date");

        startCheck = new JCheckBox();
        startCheck.setEnabled(false);

        endCheck = new JCheckBox();
        endCheck.addActionListener(new EndDateCheckAction());

        itemNameTextField = new JTextField();
        itemDescTextField = new JTextField();

        createButton = new JButton("Create Item");
        createButton.setActionCommand("CREATE ITEM");
        createButton.addActionListener(new CreateItemAction(this));

        SpinnerModel model = new SpinnerNumberModel(0, 0, 1, .01);
        numberSpinner = new JSpinner(model);
        numberSpinner.setMaximumSize(new Dimension(50,5));

        startDateCalendar = new JCalendar();
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
                .addComponent(addStartDateLabel)
                .addComponent(startDateLabel)
                .addComponent(addEndDateLabel)
                .addComponent(endDateLabel))
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(itemNameTextField)
                .addComponent(itemDescTextField)
                .addComponent(numberSpinner)
                .addComponent(startCheck)
                .addComponent(startDateCalendar)
                .addComponent(endCheck)
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
                .addComponent(addStartDateLabel)
                .addComponent(startCheck))
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(startDateLabel)
                .addComponent(startDateCalendar))
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(addEndDateLabel)
                .addComponent(endCheck))
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(endDateLabel)
                .addComponent(endDateCalendar))
            .addGap(10)
            .addComponent(createButton)
        );
    }

    private class CreateItemAction extends AbstractAction {
        private AddGoalItemFrame addToDoItemFrame;

        public CreateItemAction(AddGoalItemFrame addToDoItemFrame) {
            this.addToDoItemFrame = addToDoItemFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: Add tooltip if null

            if(!itemNameTextField.getText().equals("")) {
                LocalDateTime startDate = null;
                LocalDateTime endDate = null;

                // TODO: Figure out why date days are not making sense

                if(endCheck.isSelected()) {
                    //convert to LocalDateTime for constructors
                    endDate = convertDateStringToLocalDateTime(endDateCalendar.getDate().toString());
                }
                if(startCheck.isSelected()) {
                    startDate = convertDateStringToLocalDateTime(startDateCalendar.getDate().toString());
                }
                goalListView.createItem(itemNameTextField.getText()
                        , itemDescTextField.getText()
                        , startDate, endDate
                        , (double) numberSpinner.getValue());
                addToDoItemFrame.dispose();
            }
        }
    }

    private LocalDateTime convertDateStringToLocalDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz uuuu");
        LocalDateTime convertedDateTime = LocalDateTime.parse(dateString,formatter);
        return LocalDateTime.of(convertedDateTime.getYear()
                , convertedDateTime.getMonthValue()
                , convertedDateTime.getDayOfMonth(), convertedDateTime.getHour(), convertedDateTime.getMinute());
    }

    private class EndDateCheckAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(endCheck.isSelected()) {
                startCheck.setEnabled(true);
            }
            else {
                startCheck.setEnabled(false);
            }

        }
    }

}
