package gui;

import client.PasswordCipher;
import musicband.MusicBand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class VisualizationPanelDrawer implements PanelDrawer {

    private JPanel panel;
    private ScrollableJPanel visualizationPanel;
    private ListenerFactory listenerFactory;
    private ActionListener frameManager;
    private String panelName = "Визуализация";

    private ArrayList<MusicBand> musicBands = new ArrayList<>();
    private volatile ConcurrentHashMap<MusicBand, MusicBandButton> buttonHashMap = new ConcurrentHashMap<>();
    private Timer timer;

    public VisualizationPanelDrawer(ActionListener frameManager, ListenerFactory listenerFactory) {
        this.listenerFactory = listenerFactory;
        this.frameManager = frameManager;
        visualizationPanel = new ScrollableJPanel();
        visualizationPanel.setLayout(null);
        visualizationPanel.setPreferredSize(new Dimension(1000, 1000));
        this.listenerFactory = listenerFactory;
        timer = new Timer(3000, listenerFactory.createUpdateListener(this));
    }

    private JPanel initPanel() {
        JPanel pane = new JPanel(new BorderLayout());
//        visualizationPanel.setSize(500, 500);
        JScrollPane scrollPane = new JScrollPane(visualizationPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
            int biggestX = 0;
            int biggestY = 0;

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
                    MusicBandButton button = create(mb);
                    if (button.getX() + button.getW() > biggestX) {
                        biggestX = button.getX() + button.getW();
                    }
                    if (button.getY() + button.getH() > biggestY) {
                        biggestY = button.getY() + button.getH();
                    }
                    newButtons.put(mb, button);
                }
            }

            for (MusicBand mb : buttonHashMap.keySet()) {
                if (!oldButtons.containsKey(mb)) {
                    visualizationPanel.remove(buttonHashMap.get(mb));
                    buttonHashMap.remove(mb);
                }
            }

            buttonHashMap.putAll(newButtons);

            for (MusicBand button : newButtons.keySet()) {
                visualizationPanel.add(newButtons.get(button));
            }
            visualizationPanel.setPreferredSize(new Dimension(biggestX, biggestY));
            visualizationPanel.repaint();
        }
    }

    public ArrayList<MusicBand> getMusicBands() {
        return musicBands;
    }

    public void setMusicBands(ArrayList<MusicBand> musicBands) {
        this.musicBands = musicBands;
    }

}
