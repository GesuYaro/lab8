package gui;

import java.util.HashMap;
import java.util.Map;

public class StartGUI {
    public static void main(String[] args) {
        Map<String, PanelDrawer> map = new HashMap<>();
        FrameManager frameManager = new FrameManager(map, new MainFrame());
        map.put("Сменить пользователя", new SignInPanelDrawer(frameManager));
        map.put("Вернуться в меню", new MenuPanelDrawer(frameManager));
        frameManager.start();
    }
}
