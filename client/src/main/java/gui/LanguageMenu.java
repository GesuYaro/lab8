package gui;

import app.LocaleManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LanguageMenu extends JMenu {

    private final LocaleManager localeManager;
    private final ActionListener languageListener;

    public LanguageMenu(String name, ActionListener languageListener, LocaleManager localeManager) {
        super(name);
        this.localeManager = localeManager;
        this.languageListener = languageListener;
        JMenuItem ru = new JMenuItem("русский");
        JMenuItem ro = new JMenuItem("română");
        JMenuItem sq = new JMenuItem("shqiptare");
        JMenuItem es_CO = new JMenuItem("colombia española");
        ru.addActionListener(createListener("ru"));
        ro.addActionListener(createListener("ro"));
        sq.addActionListener(createListener("sq"));
        es_CO.addActionListener(createListener("es-CO"));
        this.add(ru);
        this.add(ro);
        this.add(sq);
        this.add(es_CO);
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
