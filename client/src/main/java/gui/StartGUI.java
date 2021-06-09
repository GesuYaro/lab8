package gui;

import app.ClientMain;
import client.Console;
import client.RequestFabric;

import java.util.HashMap;
import java.util.Map;

public class StartGUI implements Runnable {
    public static void main(String[] args) {
        Map<String, PanelDrawer> map = new HashMap<>();
        FrameManager frameManager = new FrameManager(map, new MainFrame());
        TablePanelDrawer tablePanelDrawer = new TablePanelDrawer(frameManager);
        map.put("Сменить пользователя", new SignInPanelDrawer(frameManager));
        map.put("Вернуться в меню", new MenuPanelDrawer(frameManager));
        map.put("Таблица", tablePanelDrawer);
        map.put("Команды", new CommandsPanelDrawer(frameManager, new ListenerFactory(ClientMain.getConsole())));
        map.put("Show", tablePanelDrawer);
        frameManager.start();
    }

    @Override
    public void run() {
        Map<String, PanelDrawer> map = new HashMap<>();
        FrameManager frameManager = new FrameManager(map, new MainFrame());
        TablePanelDrawer tablePanelDrawer = new TablePanelDrawer(frameManager);
        map.put("Сменить пользователя", new SignInPanelDrawer(frameManager));
        map.put("Вернуться в меню", new MenuPanelDrawer(frameManager));
        map.put("Таблица", tablePanelDrawer);
        map.put("Команды", new CommandsPanelDrawer(frameManager, new ListenerFactory(ClientMain.getConsole())));
        map.put("Show", tablePanelDrawer);
        frameManager.start();
    }
}
