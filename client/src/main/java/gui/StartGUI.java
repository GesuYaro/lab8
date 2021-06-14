package gui;

import app.ClientMain;
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
        Map<String, PanelDrawer> map = new HashMap<>();
        JButton logoutButton = new JButton("Выйти");
        MainFrame mainFrame = new MainFrame(logoutButton);
        FrameManager frameManager = new FrameManager(map, mainFrame);
        logoutButton.addActionListener(frameManager);
        ListenerFactory listenerFactory = new ListenerFactory(ClientMain.getConsole(), frameManager);
        mainFrame.addMenu(new CommandsMenu("Команды", listenerFactory));
        TablePanelDrawer tablePanelDrawer = new TablePanelDrawer(frameManager, ClientMain.getConsole(), listenerFactory, ClientMain.getUserManager());

        CommandsPanelDrawer commandsPanelDrawer = new CommandsPanelDrawer(frameManager, listenerFactory);
        VisualizationPanelDrawer visualizationPanelDrawer = new VisualizationPanelDrawer(frameManager, listenerFactory);

        LinkedList<PanelDrawer> panelDrawers = new LinkedList<>();
        panelDrawers.add(tablePanelDrawer);
//        panelDrawers.add(commandsPanelDrawer);
        panelDrawers.add(visualizationPanelDrawer);
        TabbedPanelDrawer tabbedPanelDrawer = new TabbedPanelDrawer(panelDrawers);
        map.put("Выйти", new SignInPanelDrawer(frameManager, listenerFactory, ClientMain.getUserManager(), new Authenticator()));
//        map.put("Вернуться в меню", menuPanelDrawer);
//        map.put("Таблица", tablePanelDrawer);
        map.put("Войти", tabbedPanelDrawer);
//        map.put("Команды", commandsPanelDrawer);
//        map.put("Show", tablePanelDrawer);
//        map.put("Визуализировать", visualizationPanelDrawer);
        frameManager.start();
    }
}
