package gui;

import app.LocaleManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LanguageMenu extends JMenu {

    private LocaleManager localeManager;
    private ActionListener languageListener;

    public LanguageMenu(String name, ActionListener languageListener, LocaleManager localeManager) {
        super(name);
        this.localeManager = localeManager;
        this.languageListener = languageListener;
        JMenuItem ru = new JMenuItem("русский");
        JMenuItem ro = new JMenuItem("română");
        ru.addActionListener(createListener("ru"));
        ro.addActionListener(createListener("ro"));
        this.add(ru);
        this.add(ro);
    }

    private ActionListener createListener(String localeName) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                localeManager.changeLocale(localeName);
                languageListener.actionPerformed(e);
            }
        };
    }

}
