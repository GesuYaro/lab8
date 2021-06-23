package gui;

import app.LocaleManager;
import client.PasswordCipher;
import client.UserManager;
import musicband.MusicBand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class VisualizationPanelDrawer implements PanelDrawer, LanguageChangeable {

    private JPanel panel;
    private final ScrollableJPanel visualizationPanel;
    private ListenerFactory listenerFactory;
    private final ActionListener frameManager;
    private final UserManager userManager;
    private final LocaleManager localeManager;
    private String panelName;

    private ArrayList<MusicBand> musicBands = new ArrayList<>();
    private final ConcurrentHashMap<MusicBand, MusicBandButton> buttonHashMap = new ConcurrentHashMap<>();
    private final Rule columnRule = new Rule(Rule.HORIZONTAL, true);
    private final Rule rowRule = new Rule(Rule.VERTICAL, true);
    private final Timer timer;
    private int biggestX = 0;
    private int biggestY = 0;

    public VisualizationPanelDrawer(ActionListener frameManager, ListenerFactory listenerFactory, UserManager userManager, LocaleManager localeManager) {
        this.listenerFactory = listenerFactory;
        this.frameManager = frameManager;
        visualizationPanel = new ScrollableJPanel();
        visualizationPanel.setLayout(null);
        visualizationPanel.setPreferredSize(new Dimension(1000, 1000));
        this.listenerFactory = listenerFactory;
        this.userManager = userManager;
        this.localeManager = localeManager;
        panelName = localeManager.getBundle().getString("visualization");
        timer = new Timer(3000, listenerFactory.createUpdateListener(this));
    }

    private JPanel initPanel() {
        JPanel pane = new JPanel(new BorderLayout());
//        visualizationPanel.setSize(500, 500);
        panelName = localeManager.getBundle().getString("visualization");
        JScrollPane scrollPane = new JScrollPane(visualizationPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setColumnHeaderView(columnRule);
        scrollPane.setRowHeaderView(rowRule);
        columnRule.setPreferredWidth(800);
        rowRule.setPreferredHeight(460);
        pane.add(scrollPane, BorderLayout.CENTER);
        pane.setBackground(new Color(0xED9CDE));
        timer.start();
        return pane;
    }

    @Override
    public String getPanelName() {
        return panelName;
    }

    @Override
    public JPanel drawPanel() {
        if (panel == null) {
            panel = initPanel();
        }
        return panel;
    }

    private MusicBandButton create(MusicBand mb) {
        int x = mb.getCoordinates().getX().intValue() + 200;
        int y = mb.getCoordinates().getY().intValue() + 200;
        int size = mb.getNumberOfParticipants();
        byte [] bytes = PasswordCipher.hashPassword(mb.getOwner());
        int r = bytes[13] + 128;
        int g = bytes[14] + 128;
        int b = bytes[15] + 128;
        return new MusicBandButton(x, y, size, size, r, g, b);
    }

    public synchronized void updateButtons(ArrayList<MusicBand> list) {
        synchronized (buttonHashMap) {

            HashMap<MusicBand, MusicBandButton> newButtons = new HashMap<>();
            HashMap<MusicBand, MusicBandButton> oldButtons = new HashMap<>();

            for (MusicBand mb : list) {
                if (buttonHashMap.containsKey(mb)) {
                    MusicBandButton button = buttonHashMap.get(mb);
                    if (button.getX() + button.getW() > biggestX) {
                        biggestX = button.getX() + button.getW();
                    }
                    if (button.getY() + button.getH() > biggestY) {
                        biggestY = button.getY() + button.getH();
                    }
                    oldButtons.put(mb, button);
                } else {
                    boolean isNew = true;
                    MusicBandButton button = create(mb);
                    if (button.getX() + button.getW() > biggestX) {
                        biggestX = button.getX() + button.getW();
                    }
                    if (button.getY() + button.getH() > biggestY) {
                        biggestY = button.getY() + button.getH();
                    }
                    for (MusicBand oldMB : musicBands) {
                        if (oldMB.getId() == mb.getId()) {
                            isNew = false;
                            MusicBandButton musicBandButton = buttonHashMap.get(oldMB);
                            buttonHashMap.remove(oldMB);
                            oldMB.setName(mb.getName());
                            oldMB.setCoordinates(mb.getCoordinates());
                            oldMB.setCreationDate(mb.getCreationDate());
                            oldMB.setNumberOfParticipants(mb.getNumberOfParticipants());
                            oldMB.setSinglesCount(mb.getSinglesCount());
                            oldMB.setGenre(mb.getGenre());
                            oldMB.setLabel(mb.getLabel());
                            musicBandButton.changeState(mb.getCoordinates().getX().intValue() + 200, mb.getCoordinates().getY().intValue() + 200, mb.getNumberOfParticipants());
                            oldButtons.put(oldMB, musicBandButton);
                            buttonHashMap.put(oldMB, musicBandButton);
                        }
                    }
                    if (isNew) {
                        newButtons.put(mb, button);
                    }
                }
            }

            for (MusicBand mb : buttonHashMap.keySet()) {
                if (!oldButtons.containsKey(mb)) {
                    visualizationPanel.remove(buttonHashMap.get(mb));
                    buttonHashMap.remove(mb);
                }
            }

            buttonHashMap.putAll(newButtons);
            musicBands = list;
            for (MusicBand musicBand : newButtons.keySet()) {
                MusicBandButton musicBandButton = newButtons.get(musicBand);
                musicBandButton.addActionListener(listenerFactory.createShowChangeListener(panel, "update", musicBand, musicBand.getOwner().equals(userManager.getUser().getLogin())));
                visualizationPanel.add(musicBandButton);
            }
            visualizationPanel.setPreferredSize(new Dimension(biggestX, biggestY));
            columnRule.setPreferredWidth(biggestX);
            rowRule.setPreferredHeight(biggestY);
            visualizationPanel.repaint();
            visualizationPanel.revalidate();
        }
    }

    public ArrayList<MusicBand> getMusicBands() {
        return musicBands;
    }

    public void setMusicBands(ArrayList<MusicBand> musicBands) {
        this.musicBands = musicBands;
    }

    @Override
    public void updateLanguage() {
        panelName = localeManager.getBundle().getString("visualization");
    }
}
