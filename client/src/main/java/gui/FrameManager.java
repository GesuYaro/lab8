package gui;

import app.LocaleManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Map;

public class FrameManager implements ActionListener {

    private Map<String, PanelDrawer> panelDrawers;
    private MainFrame frame;
    private LocaleManager localeManager;

    public FrameManager(Map<String, PanelDrawer> panelDrawers, MainFrame frame, LocaleManager localeManager) {
        this.panelDrawers = panelDrawers;
        this.frame = frame;
        this.localeManager = localeManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        newPane(e.getActionCommand());
    }

    public void newPane(String string) {
        PanelDrawer drawer = panelDrawers.get(string);
        if (drawer != null) {
            frame.updatePane(drawer.drawPanel());
            if (string.equals(localeManager.getBundle().getString("sign out"))) {
                hideMenu();
            }
            frame.setVisible(true);
        } else {
            System.out.println("no drawer found: " + string);
        }
    }

    public void start() {
        frame.updatePane(panelDrawers.get(localeManager.getBundle().getString("sign out")).drawPanel());
        frame.setVisible(true);
    }

    public void showMenu() {
        frame.showMenu();
    }

    public void hideMenu() {
        frame.hideMenu();
    }
}
