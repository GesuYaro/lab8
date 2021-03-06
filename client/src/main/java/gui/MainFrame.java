package gui;

import app.ClientMain;
import app.LocaleManager;
import javafx.scene.control.SeparatorMenuItem;

import javax.swing.*;
import javax.xml.stream.FactoryConfigurationError;
import java.awt.*;

public class MainFrame extends JFrame implements LanguageChangeable {

    private final int width = 800;
    private final int height = 460;
    private final JMenuBar jMenuBar = new JMenuBar();
    private final JLabel userLabel;
    private final LocaleManager localeManager;
    private final JButton logoutButton;
    private CommandsMenu commandsMenu;


    public MainFrame(JButton logoutButton, LocaleManager localeManager) {
        super("Music Bands");
        this.setSize(width, height);
        this.setMinimumSize(new Dimension(480, 440));
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        this.setLocation((screenSize.width - width)/2, (screenSize.height - height)/2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.logoutButton = logoutButton;
        jMenuBar.add(logoutButton);
        userLabel = new JLabel();
        jMenuBar.add(userLabel);
//        jMenuBar.add(new JSeparator());
        jMenuBar.setVisible(false);
        this.setJMenuBar(jMenuBar);
        this.localeManager = localeManager;
        jMenuBar.setVisible(true);
    }


    public void updatePane(Container pane) {
        this.setContentPane(pane);
    }

    public void showMenu() {
        userLabel.setText(localeManager.getBundle().getString("logged as") + ": " + ClientMain.getUserManager().getUser().getLogin());
        commandsMenu.setVisible(true);
        userLabel.setVisible(true);
        logoutButton.setVisible(true);
    }

    public void hideMenu() {
        logoutButton.setVisible(false);
        commandsMenu.setVisible(false);
        userLabel.setVisible(false);
//        jMenuBar.setVisible(false);
    }

    public void addMenu(JMenu menu) {
        jMenuBar.add(menu);
    }

    public void setCommandsMenu(CommandsMenu commandsMenu) {
        if (this.commandsMenu != null) {
            jMenuBar.remove(this.commandsMenu);
        }
        this.commandsMenu = commandsMenu;
        jMenuBar.add(commandsMenu);
    }

    @Override
    public void updateLanguage() {
        commandsMenu.setText(localeManager.getBundle().getString("commands"));
        logoutButton.setText(localeManager.getBundle().getString("sign out"));
        if (ClientMain.getUserManager().getUser() != null) {
            userLabel.setText(localeManager.getBundle().getString("logged as") + ": " + ClientMain.getUserManager().getUser().getLogin());
        }
    }
}
