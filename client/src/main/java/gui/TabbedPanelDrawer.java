package gui;

import javax.swing.*;
import java.util.List;
import java.util.ListIterator;

public class TabbedPanelDrawer implements PanelDrawer, LanguageChangeable {

    private JTabbedPane tabbedPane = new JTabbedPane();
    private final List<PanelDrawer> panelDrawers;
    private final String panelName = "tabs";


    public TabbedPanelDrawer(List<PanelDrawer> panelDrawers) {
        this.panelDrawers = panelDrawers;
    }

    public JTabbedPane drawPanel() {
        tabbedPane = new JTabbedPane();
        for (ListIterator<PanelDrawer> it = panelDrawers.listIterator(); it.hasNext(); ) {
            PanelDrawer drawer = it.next();
            if (drawer != null) {
                tabbedPane.addTab(drawer.getPanelName(), drawer.drawPanel());
            }
        }
        return tabbedPane;
    }

    @Override
    public String getPanelName() {
        return panelName;
    }

    @Override
    public void updateLanguage() {
        tabbedPane.removeAll();
        for (ListIterator<PanelDrawer> it = panelDrawers.listIterator(); it.hasNext(); ) {
            PanelDrawer drawer = it.next();
            if (drawer != null) {
                tabbedPane.addTab(drawer.getPanelName(), drawer.drawPanel());
            }
        }
    }
}
