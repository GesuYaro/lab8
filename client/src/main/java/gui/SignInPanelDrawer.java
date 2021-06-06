package gui;

import javax.swing.*;
import java.awt.*;

public class SignInPanelDrawer implements PanelDrawer {

    private FrameManager frameManager;

    private JTextField loginField = new JTextField("");
    private JPasswordField passwordField = new JPasswordField("");
    private JLabel loginLabel = new JLabel("Логин:");
    private JLabel passwordLabel = new JLabel("Пароль:");
    private JButton signInButton = new JButton("Войти");
    private JButton signUpButton = new JButton("Зарегистрироваться");
    private JButton backButton = new JButton("Вернуться в меню");

    public SignInPanelDrawer(FrameManager frameManager) {
        this.frameManager = frameManager;
    }

    @Override
    public JPanel drawPanel() {
        backButton.addActionListener(frameManager);
        loginField.setPreferredSize(new Dimension(200, 30));
        passwordField.setPreferredSize(new Dimension(200, 30));
        Container loginContainer = new Container();
        loginContainer.setLayout(new GridBagLayout());
        loginContainer.add(loginLabel, getFieldsCons(0));
        loginContainer.add(loginField, getFieldsCons(0));
        loginContainer.add(passwordLabel, getFieldsCons(1));
        loginContainer.add(passwordField, getFieldsCons(1));
        Container buttonsContainer = new Container();
        buttonsContainer.setLayout(new GridBagLayout());
        buttonsContainer.add(signInButton, getButtonCons(0));
        buttonsContainer.add(signUpButton, getButtonCons(1));
        JPanel pane = new JPanel(new GridBagLayout());
        pane.setBackground(new Color(0xED9CDE));
        pane.add(loginContainer, getTextFieldCons(0));
        pane.add(buttonsContainer, getButtonContainerCons(1));
        pane.add(backButton, getBackButtonCons());
        return pane;
    }

    private GridBagConstraints getFieldsCons(int gridy) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = gridy;
        constraints.insets = new Insets(5, 5, 5, 5);
        return constraints;
    }

    private GridBagConstraints getButtonCons(int gridx) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridx;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 5, 5, 5);
        return constraints;
    }

    private GridBagConstraints getTextFieldCons(int gridy) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weighty = 0.5;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridy = gridy;
        constraints.gridx = 1;
        constraints.ipadx = 50;
        constraints.ipady = 10;
        return constraints;
    }

    private GridBagConstraints getButtonContainerCons(int gridx) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weighty = 0.5;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 2;
        constraints.gridy = 1;
        constraints.gridx = gridx;
        return constraints;
    }

    private GridBagConstraints getBackButtonCons() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.weighty = 0.5;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridy = 0;
        constraints.gridx = 1;
        return constraints;
    }
}
