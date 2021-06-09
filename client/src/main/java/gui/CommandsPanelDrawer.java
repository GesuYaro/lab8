package gui;

import client.Console;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class CommandsPanelDrawer implements PanelDrawer {

    private ActionListener frameManager;
    private JPanel panel;
    private ListenerFactory listenerFactory;

    private JButton addButton = new JButton("Add"); //---------------
    private JButton clearButton = new JButton("Clear");
    private JButton filterLessThanSinglesButton = new JButton("Filter less than Singles");
    private JButton helpButton = new JButton("Help");
    private JButton historyButton = new JButton("History");
    private JButton infoButton = new JButton("Info");
    private JButton insertAtButton = new JButton("Insert at"); // ----------------
    private JButton printFieldButton = new JButton("Print genre descending");
    private JButton removeButton = new JButton("Remove by id");
    private JButton removeLastButton = new JButton("Remove last");
    private JButton showButton = new JButton("Show");
    private JButton updateButton = new JButton("Update"); //--------------------
    private JButton backButton = new JButton("Вернуться в меню");

    public CommandsPanelDrawer(ActionListener frameManager, ListenerFactory listenerFactory) {
        this.frameManager = frameManager;
        this.listenerFactory = listenerFactory;
    }

    @Override
    public JPanel drawPanel() {
        if (panel == null) {
            panel = initPanel();
        }
        return panel;
    }

    private JPanel initPanel() {
        infoButton.addActionListener(listenerFactory.createSimpleDialogListener(panel, "info"));
        clearButton.addActionListener(listenerFactory.createSimpleDialogListener(panel, "clear"));
        helpButton.addActionListener(listenerFactory.createSimpleDialogListener(panel, "help"));
        historyButton.addActionListener(listenerFactory.createSimpleDialogListener(panel, "history"));
        printFieldButton.addActionListener(listenerFactory.createSimpleDialogListener(panel, "print_field_descending_genre"));
        removeLastButton.addActionListener(listenerFactory.createSimpleDialogListener(panel, "remove_last"));
        filterLessThanSinglesButton.addActionListener(listenerFactory.createAskingDialogListener(panel, "filter_less_than_singles_count"));
        removeButton.addActionListener(listenerFactory.createAskingDialogListener(panel, "remove_by_id"));
        updateButton.addActionListener(listenerFactory.createExtendedAskingDialog(panel, "update", true));
        addButton.addActionListener(listenerFactory.createExtendedAskingDialog(panel, "add", false));
        insertAtButton.addActionListener(listenerFactory.createExtendedAskingDialog(panel, "insert_at", true));
        showButton.addActionListener(frameManager);

        backButton.addActionListener(frameManager);
        JPanel pane = new JPanel(new GridBagLayout());
        Container buttons = new Container();
        buttons.setLayout(new GridLayout(6, 2, 5 ,5));
        buttons.add(addButton);
        buttons.add(clearButton);
        buttons.add(filterLessThanSinglesButton);
        buttons.add(helpButton);
        buttons.add(historyButton);
        buttons.add(infoButton);
        buttons.add(insertAtButton);
        buttons.add(printFieldButton);
        buttons.add(removeButton);
        buttons.add(removeLastButton);
        buttons.add(showButton);
        buttons.add(updateButton);
        pane.setBackground(new Color(0xED9CDE));
        pane.add(backButton, getBackButtonCons());
        pane.add(buttons, getButtonCons(0, 0));
        return pane;
    }

    private GridBagConstraints getButtonCons(int gridx, int gridy) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(40, 5, 5, 5);
        constraints.gridx = gridx;
        constraints.gridy = gridy;

        return constraints;
    }

    private GridBagConstraints getBackButtonCons() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.weighty = 0.5;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.weightx = 0.5;
        return constraints;
    }
}
