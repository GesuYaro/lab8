package gui;

import app.LocaleManager;

import javax.swing.*;

public class CommandsMenu extends JMenu implements LanguageChangeable {

    private LocaleManager localeManager;

    private JMenuItem add;
    private JMenuItem clear;
    private JMenuItem filter;
    private JMenuItem help;
    private JMenuItem history;
    private JMenuItem info;
    private JMenuItem insert;
    private JMenuItem printField;
    private JMenuItem remove;
    private JMenuItem removeLast;
    private JMenuItem show;
    private JMenuItem update;
    private JMenuItem execute;

    public CommandsMenu(String name, ListenerFactory listenerFactory, LocaleManager localeManager) {
        super(name);
        this.localeManager = localeManager;
        add = new JMenuItem(localeManager.getBundle().getString("add"));
        clear = new JMenuItem(localeManager.getBundle().getString("clear"));
        filter = new JMenuItem(localeManager.getBundle().getString("filter less than singles"));
        help = new JMenuItem(localeManager.getBundle().getString("help"));
        history = new JMenuItem(localeManager.getBundle().getString("history"));
        info = new JMenuItem(localeManager.getBundle().getString("info"));
        insert = new JMenuItem(localeManager.getBundle().getString("insert at"));
        printField = new JMenuItem(localeManager.getBundle().getString("print genre descending"));
        remove = new JMenuItem(localeManager.getBundle().getString("remove by id"));
        removeLast = new JMenuItem(localeManager.getBundle().getString("remove last"));
        show = new JMenuItem(localeManager.getBundle().getString("show"));
        update = new JMenuItem(localeManager.getBundle().getString("update"));
        execute = new JMenuItem(localeManager.getBundle().getString("execute script"));
        info.addActionListener(listenerFactory.createSimpleDialogListener(this, "info"));
        clear.addActionListener(listenerFactory.createSimpleDialogListener(this, "clear"));
        help.addActionListener(listenerFactory.createSimpleDialogListener(this, "help"));
        history.addActionListener(listenerFactory.createSimpleDialogListener(this, "history"));
        printField.addActionListener(listenerFactory.createSimpleDialogListener(this, "print_field_descending_genre"));
        removeLast.addActionListener(listenerFactory.createSimpleDialogListener(this, "remove_last"));
        filter.addActionListener(listenerFactory.createAskingDialogListener(this, "filter_less_than_singles_count"));
        remove.addActionListener(listenerFactory.createAskingDialogListener(this, "remove_by_id"));
        update.addActionListener(listenerFactory.createExtendedAskingDialog(this, "update", true));
        add.addActionListener(listenerFactory.createExtendedAskingDialog(this, "add", false));
        insert.addActionListener(listenerFactory.createExtendedAskingDialog(this, "insert_at", true));
        show.addActionListener(listenerFactory.createSimpleDialogListener(this , "show"));
        execute.addActionListener(listenerFactory.createFileChoosingListener(this));
        this.add(add);
        this.add(clear);
        this.add(help);
        this.add(history);
        this.add(printField);
        this.add(removeLast);
        this.add(filter);
        this.add(remove);
        this.add(update);
        this.add(add);
        this.add(insert);
        this.add(show);
        this.add(execute);
    }

    @Override
    public void updateLanguage() {
        this.setName(localeManager.getBundle().getString("commands"));
        add.setText(localeManager.getBundle().getString("add"));
        clear.setText(localeManager.getBundle().getString("clear"));
        filter.setText(localeManager.getBundle().getString("filter less than singles"));
        help.setText(localeManager.getBundle().getString("help"));
        history.setText(localeManager.getBundle().getString("history"));
        info.setText(localeManager.getBundle().getString("info"));
        insert.setText(localeManager.getBundle().getString("insert at"));
        printField.setText(localeManager.getBundle().getString("print genre descending"));
        remove.setText(localeManager.getBundle().getString("remove by id"));
        removeLast.setText(localeManager.getBundle().getString("remove last"));
        show.setText(localeManager.getBundle().getString("show"));
        update.setText(localeManager.getBundle().getString("update"));
        execute.setText(localeManager.getBundle().getString("execute script"));
    }
}
