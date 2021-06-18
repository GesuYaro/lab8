package gui;

import app.LocaleManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ListIterator;

public class LanguageChangeListener implements ActionListener {

    private List<LanguageChangeable> languageChangeableList;

    public LanguageChangeListener(List<LanguageChangeable> languageChangeableList) {
        this.languageChangeableList = languageChangeableList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (ListIterator<LanguageChangeable> it = languageChangeableList.listIterator(); it.hasNext(); ) {
            it.next().updateLanguage();
        }
    }
}
