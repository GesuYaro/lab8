package gui;

import app.ClientMain;
import client.Authenticator;

import java.util.HashMap;
import java.util.Map;

public class StartGUI implements Runnable {

    @Override
    public void run() {
        Map<String, PanelDrawer> map = new HashMap<>();
        FrameManager frameManager = new FrameManager(map, new MainFrame());
        ListenerFactory listenerFactory = new ListenerFactory(ClientMain.getConsole());
        TablePanelDrawer tablePanelDrawer = new TablePanelDrawer(frameManager, ClientMain.getConsole(), listenerFactory);
        map.put("Сменить пользователя", new SignInPanelDrawer(frameManager, listenerFactory, ClientMain.getUserManager(), new Authenticator()));
        map.put("Вернуться в меню", new MenuPanelDrawer(frameManager, ClientMain.getUserManager()));
        map.put("Таблица", tablePanelDrawer);
        map.put("Команды", new CommandsPanelDrawer(frameManager, listenerFactory));
        map.put("Show", tablePanelDrawer);
        map.put("Визуализировать", new VisualizationPanelDrawer(frameManager, listenerFactory));
        frameManager.start();
    }
}
