package gui;

import app.LocaleManager;

import javax.swing.*;

public class CommandsMenu extends JMenu {

    public CommandsMenu(String s, ListenerFactory listenerFactory, LocaleManager localeManager) {
        super(s);
        JMenuItem add = new JMenuItem(localeManager.getBundle().getString("add"));
        JMenuItem clear = new JMenuItem(localeManager.getBundle().getString("clear"));
        JMenuItem filter = new JMenuItem(localeManager.getBundle().getString("filter less than singles"));
        JMenuItem help = new JMenuItem(localeManager.getBundle().getString("help"));
        JMenuItem history = new JMenuItem(localeManager.getBundle().getString("history"));
        JMenuItem info = new JMenuItem(localeManager.getBundle().getString("info"));
        JMenuItem insert = new JMenuItem(localeManager.getBundle().getString("insert at"));
        JMenuItem printField = new JMenuItem(localeManager.getBundle().getString("print genre descending"));
        JMenuItem remove = new JMenuItem(localeManager.getBundle().getString("remove by id"));
        JMenuItem removeLast = new JMenuItem(localeManager.getBundle().getString("remove last"));
        JMenuItem show = new JMenuItem(localeManager.getBundle().getString("show"));
        JMenuItem update = new JMenuItem(localeManager.getBundle().getString("update"));
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
    }

}
