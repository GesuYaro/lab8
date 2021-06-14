package gui;

import javax.swing.*;

public class CommandsMenu extends JMenu {

    public CommandsMenu(String s, ListenerFactory listenerFactory) {
        super(s);
        JMenuItem add = new JMenuItem("Add");
        JMenuItem clear = new JMenuItem("Clear");
        JMenuItem filter = new JMenuItem("Filter less than Singles");
        JMenuItem help = new JMenuItem("Help");
        JMenuItem history = new JMenuItem("History");
        JMenuItem info = new JMenuItem("Info");
        JMenuItem insert = new JMenuItem("Insert at");
        JMenuItem printField = new JMenuItem("Print genre descending");
        JMenuItem remove = new JMenuItem("Remove by ID");
        JMenuItem removeLast = new JMenuItem("Remove Last");
        JMenuItem show = new JMenuItem("Show");
        JMenuItem update = new JMenuItem("Update");
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
