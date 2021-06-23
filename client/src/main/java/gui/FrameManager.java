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
        if (string.equals(localeManager.getBundle().getString("sign out"))) {
            string = "sign out";
        }
        if (string.equals(localeManager.getBundle().getString("sign in"))) {
            string = "sign in";
        }
        PanelDrawer drawer = panelDrawers.get(string);
        if (drawer != null) {
            frame.updatePane(drawer.drawPanel());
            if (string.equals("sign out")) {
                hideMenu();
            }
            frame.setVisible(true);
        } else {
            System.out.println("no drawer found: " + string);
        }
    }

    public void start() {
        frame.updatePane(panelDrawers.get("sign out").drawPanel());
        hideMenu();
        frame.setVisible(true);
    }

    public void showMenu() {
        frame.showMenu();
    }

    public void hideMenu() {
        frame.hideMenu();
    }
}
