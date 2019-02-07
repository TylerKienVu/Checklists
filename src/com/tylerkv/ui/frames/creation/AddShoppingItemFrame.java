package com.tylerkv.ui.frames.creation;

import com.tylerkv.ui.views.ShoppingListView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AddShoppingItemFrame extends JFrame {
    private ShoppingListView shoppingListView;
    private JPanel container;
    private GroupLayout groupLayout;
    private JLabel itemNameLabel;
    private JLabel itemDescLabel;
    private JLabel quantityLabel;
    private JTextField itemNameTextField;
    private JTextField itemDescTextField;
    private JSpinner numberSpinner;
    private JButton createButton;

    public AddShoppingItemFrame(ShoppingListView shoppingListView) {
        this.shoppingListView = shoppingListView;
        this.initFrame();
        this.initPanel();
    }

    private void initFrame() {
        this.setTitle("Create Shopping List Item");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setPreferredSize(new Dimension(300,170));
        this.setLayout(new BorderLayout());
    }

    private void initPanel() {
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        container = new JPanel();

        itemNameLabel = new JLabel("Item Name");
        itemDescLabel = new JLabel("Item Description");
        quantityLabel = new JLabel("Quantity");
        itemNameTextField = new JTextField();
        itemDescTextField = new JTextField();

        createButton = new JButton("Create Item");
        createButton.setActionCommand("CREATE ITEM");
        createButton.addActionListener(new CreateItemAction(this));

        SpinnerModel model = new SpinnerNumberModel(1, 1, 100, 1);
        numberSpinner = new JSpinner(model);
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
                .addComponent(quantityLabel))
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(itemNameTextField)
                .addComponent(itemDescTextField)
                .addComponent(numberSpinner)
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
                .addComponent(quantityLabel)
                .addComponent(numberSpinner))
            .addGap(15)
            .addComponent(createButton)
        );
    }

    private class CreateItemAction extends AbstractAction {
        private AddShoppingItemFrame addShoppingItemFrame;

        public CreateItemAction(AddShoppingItemFrame addShoppingItemFrame) {
            this.addShoppingItemFrame = addShoppingItemFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: Add tooltip if null

            if(itemNameTextField.getText() != null && itemDescTextField != null) {
                shoppingListView.createItem(itemNameTextField.getText(), itemDescTextField.getText(), (int)numberSpinner.getValue());
                addShoppingItemFrame.dispose();
            }
        }
    }

}
