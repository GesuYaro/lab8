package gui;

import app.LocaleManager;
import client.Authenticator;
import client.UserManager;

import javax.swing.*;
import java.awt.*;

public class SignInPanelDrawer implements PanelDrawer, LanguageChangeable {

    private final FrameManager frameManager;
    private final ListenerFactory listenerFactory;
    private final UserManager userManager;
    private final Authenticator authenticator;
    private final LocaleManager localeManager;
    private String panelName;

    private JPanel panel;

    private JTextField loginField;
    private JPasswordField passwordField;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    private JButton signInButton;
    private JButton signUpButton;

    public SignInPanelDrawer(FrameManager frameManager, ListenerFactory listenerFactory, UserManager userManager, Authenticator authenticator, LocaleManager localeManager) {
        this.frameManager = frameManager;
        this.listenerFactory = listenerFactory;
        this.userManager = userManager;
        this.authenticator = authenticator;
        this.localeManager = localeManager;

    }

    private JPanel initPanel() {
        panelName = localeManager.getBundle().getString("sign in");
        loginField = new JTextField("");
        passwordField = new JPasswordField("");
        loginLabel = new JLabel(localeManager.getBundle().getString("login") + ":");
        passwordLabel = new JLabel(localeManager.getBundle().getString("password") + ":");
        signInButton = new JButton(localeManager.getBundle().getString("sign in"));
        signUpButton = new JButton(localeManager.getBundle().getString("sign up"));
        loginField.setPreferredSize(new Dimension(200, 30));
        passwordField.setPreferredSize(new Dimension(200, 30));
        signInButton.addActionListener(listenerFactory.createSigningDialog(panel, loginField, passwordField, userManager, authenticator, "sign_in"));
        signUpButton.addActionListener(listenerFactory.createSigningDialog(panel, loginField, passwordField, userManager, authenticator, "sign_up"));
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
        return pane;
    }

    @Override
    public JPanel drawPanel() {
        if (panel == null) {
            panel = initPanel();
        }
        return panel;
    }

    @Override
    public String getPanelName() {
        return panelName;
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

    @Override
    public void updateLanguage() {
        loginLabel.setText(localeManager.getBundle().getString("login") + ":");
        passwordLabel.setText(localeManager.getBundle().getString("password") + ":");
        signInButton.setText(localeManager.getBundle().getString("sign in"));
        signUpButton.setText(localeManager.getBundle().getString("sign up"));
    }
}
