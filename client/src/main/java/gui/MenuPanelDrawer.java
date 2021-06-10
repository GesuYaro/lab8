package gui;

import client.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuPanelDrawer implements PanelDrawer {

    private ActionListener frameManager;

    private JPanel panel;
    private UserManager userManager;

    private JButton authButton = new JButton("Сменить пользователя");
    private JButton commandsButton = new JButton("Команды");
    private JButton tableButton = new JButton("Таблица");
    private JButton visualizeButton = new JButton("Визуализировать");
    private JLabel authLabel = new JLabel("Авторизован как:");
    private JLabel userNameLabel = new JLabel();
    private JLabel versionLabel = new JLabel("ver. 0.8.3");
    private Container userNameContainer = new Container();


    public MenuPanelDrawer(ActionListener frameManager, UserManager userManager) {
        this.frameManager = frameManager;
        this.userManager = userManager;
    }

    private JPanel initPanel() {
        authButton.addActionListener(frameManager);
        commandsButton.addActionListener(frameManager);
        tableButton.addActionListener(frameManager);
        visualizeButton.addActionListener(frameManager);
        JPanel pane = new JPanel(new GridBagLayout());
        pane.setBackground(new Color(0xED9CDE));
        userNameContainer.setLayout(new BoxLayout(userNameContainer, BoxLayout.Y_AXIS));
        authLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        if (userManager.getUser() != null) {
            userNameLabel.setText(userManager.getUser().getLogin());
        }
        userNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userNameContainer.add(authLabel);
        userNameContainer.add(userNameLabel);
        pane.add(userNameContainer, getAuthLabelCons());
        pane.add(authButton, getButtonCons(2));
        pane.add(commandsButton, getButtonCons(3));
        pane.add(tableButton, getButtonCons(4));
        pane.add(visualizeButton, getButtonCons(5));
        versionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        Container versionContainer = new Container();
        versionContainer.setLayout(new BoxLayout(versionContainer, BoxLayout.Y_AXIS));
        versionContainer.add(versionLabel);
        pane.add(versionContainer, getButtonCons(7));
        return pane;
    }

    @Override
    public JPanel drawPanel() {
        if (panel == null) {
            panel = initPanel();
        }
        if (userManager.getUser() != null) {
            if (!userManager.getUser().getLogin().equals(userNameLabel.getText())) {
                updateUsername();
            }
        }
        return panel;
    }

    private GridBagConstraints getAuthLabelCons() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1.0;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.insets = new Insets(5, 5, 20, 5);
        constraints.gridx = 1;
        constraints.gridy = 0;
        return constraints;
    }

    private GridBagConstraints getButtonCons(int gridy) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weighty = 0.5;
        constraints.insets = new Insets(2, 2, 2, 2);
        constraints.gridy = gridy;
        constraints.ipady = 40;
        constraints.ipadx = 300;
        constraints.gridx = 1;
        return constraints;
    }

    private void updateUsername() {
        if (userManager.getUser() != null) {
            panel.remove(userNameContainer);
            authLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            userNameLabel.setText(userManager.getUser().getLogin());
            userNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            userNameContainer.add(authLabel);
            userNameContainer.add(userNameLabel);
            panel.add(userNameContainer, getAuthLabelCons());
        }
    }

}
