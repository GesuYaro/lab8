package gui;

import app.ClientMain;
import app.LocaleManager;
import client.Authenticator;
import client.UserManager;
import sun.awt.image.ImageWatched;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class StartGUI implements Runnable {

    @Override
    public void run() {
        LinkedList<LanguageChangeable> languageChangeableList = new LinkedList<>();
        LocaleManager localeManager = ClientMain.getLocaleManager();
        Map<String, PanelDrawer> map = new HashMap<>();
        JButton logoutButton = new JButton(localeManager.getBundle().getString("sign out"));
        MainFrame mainFrame = new MainFrame(logoutButton, localeManager);
        FrameManager frameManager = new FrameManager(map, mainFrame, localeManager);
        logoutButton.addActionListener(frameManager);
        ListenerFactory listenerFactory = new ListenerFactory(ClientMain.getConsole(), frameManager, localeManager);
        CommandsMenu commandsMenu = new CommandsMenu(localeManager.getBundle().getString("commands"), listenerFactory, localeManager);
        mainFrame.setCommandsMenu(commandsMenu);
        languageChangeableList.add(commandsMenu);
        mainFrame.addMenu(new LanguageMenu("\u2690", new LanguageChangeListener(languageChangeableList), localeManager));
        TablePanelDrawer tablePanelDrawer = new TablePanelDrawer(frameManager, ClientMain.getConsole(), listenerFactory, ClientMain.getUserManager(), localeManager);
        languageChangeableList.add(tablePanelDrawer);
        VisualizationPanelDrawer visualizationPanelDrawer = new VisualizationPanelDrawer(frameManager, listenerFactory, ClientMain.getUserManager(), localeManager);
        languageChangeableList.add(visualizationPanelDrawer);
        LinkedList<PanelDrawer> panelDrawers = new LinkedList<>();
        panelDrawers.add(tablePanelDrawer);
        panelDrawers.add(visualizationPanelDrawer);
        TabbedPanelDrawer tabbedPanelDrawer = new TabbedPanelDrawer(panelDrawers);
        languageChangeableList.add(tabbedPanelDrawer);
        SignInPanelDrawer signInPanelDrawer = new SignInPanelDrawer(frameManager, listenerFactory, ClientMain.getUserManager(), new Authenticator(), localeManager);
        languageChangeableList.add(signInPanelDrawer);
        map.put(localeManager.getBundle().getString("sign out"), signInPanelDrawer);
        map.put(localeManager.getBundle().getString("sign in"), tabbedPanelDrawer);
        languageChangeableList.add(mainFrame);
        frameManager.start();
    }
}
