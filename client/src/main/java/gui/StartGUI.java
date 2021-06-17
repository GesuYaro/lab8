package gui;

import app.ClientMain;
import app.LocaleManager;
import client.Authenticator;
import client.UserManager;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class StartGUI implements Runnable {

    @Override
    public void run() {
        LocaleManager localeManager = ClientMain.getLocaleManager();
        Map<String, PanelDrawer> map = new HashMap<>();
        JButton logoutButton = new JButton(localeManager.getBundle().getString("sign out"));
        MainFrame mainFrame = new MainFrame(logoutButton, localeManager);
        FrameManager frameManager = new FrameManager(map, mainFrame, localeManager);
        logoutButton.addActionListener(frameManager);
        ListenerFactory listenerFactory = new ListenerFactory(ClientMain.getConsole(), frameManager, localeManager);
        mainFrame.addMenu(new CommandsMenu(localeManager.getBundle().getString("commands"), listenerFactory, localeManager));
        TablePanelDrawer tablePanelDrawer = new TablePanelDrawer(frameManager, ClientMain.getConsole(), listenerFactory, ClientMain.getUserManager(), localeManager);

        VisualizationPanelDrawer visualizationPanelDrawer = new VisualizationPanelDrawer(frameManager, listenerFactory, ClientMain.getUserManager(), localeManager);

        LinkedList<PanelDrawer> panelDrawers = new LinkedList<>();
        panelDrawers.add(tablePanelDrawer);
        panelDrawers.add(visualizationPanelDrawer);
        TabbedPanelDrawer tabbedPanelDrawer = new TabbedPanelDrawer(panelDrawers);
        map.put(localeManager.getBundle().getString("sign out"), new SignInPanelDrawer(frameManager, listenerFactory, ClientMain.getUserManager(), new Authenticator(), localeManager));
        map.put(localeManager.getBundle().getString("sign in"), tabbedPanelDrawer);
        frameManager.start();
    }
}
