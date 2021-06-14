package gui;

import app.ClientMain;
import javafx.scene.control.SeparatorMenuItem;

import javax.swing.*;
import javax.xml.stream.FactoryConfigurationError;
import java.awt.*;

public class MainFrame extends JFrame {

    private int width = 800;
    private int height = 460;
    private JMenuBar jMenuBar = new JMenuBar();
    private JLabel userLabel;


    public MainFrame(JButton logoutButton) {
        super("Music Bands");
        this.setSize(width, height);
        this.setMinimumSize(new Dimension(480, 440));
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        this.setLocation((screenSize.width - width)/2, (screenSize.height - height)/2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jMenuBar.add(logoutButton);
        userLabel = new JLabel();
        jMenuBar.add(userLabel);
//        jMenuBar.add(new JSeparator());
        jMenuBar.setVisible(false);
        this.setJMenuBar(jMenuBar);
    }


    public void updatePane(Container pane) {
        this.setContentPane(pane);
    }

    public void showMenu() {
        userLabel.setText("Авторизован как: " + ClientMain.getUserManager().getUser().getLogin());
        jMenuBar.setVisible(true);
    }

    public void hideMenu() {
        jMenuBar.setVisible(false);
    }

    public void addMenu(JMenu menu) {
        jMenuBar.add(menu);
//        menu.setMargin(new Insets(0, 30 ,0 ,0));
    }
}
