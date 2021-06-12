package gui;

import client.PasswordCipher;
import musicband.MusicBand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class VisualizationPanelDrawer implements PanelDrawer {

    private JPanel panel;
    private JPanel visualizationPanel;
    private ListenerFactory listenerFactory;
    private ActionListener frameManager;
    private ArrayList<MusicBand> musicBands = new ArrayList<>();
    private volatile ConcurrentHashMap<MusicBand, MusicBandButton> buttonHashMap = new ConcurrentHashMap<>();
    private Timer timer;

    private JButton backButton = new JButton("Вернуться в меню");

    public VisualizationPanelDrawer(ActionListener frameManager, ListenerFactory listenerFactory) {
        this.listenerFactory = listenerFactory;
        this.frameManager = frameManager;
        visualizationPanel = new JPanel(null);
        this.listenerFactory = listenerFactory;
        timer = new Timer(3000, listenerFactory.createUpdateListener(this));
    }

    private JPanel initPanel() {
        JPanel pane = new JPanel(new BorderLayout());

        visualizationPanel.setSize(500, 500);
        JScrollPane scrollPane = new JScrollPane(visualizationPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setBackground(new Color(0xED9CDE));
        pane.add(scrollPane, BorderLayout.CENTER);
        backButton.addActionListener(frameManager);
        pane.add(backButton, BorderLayout.NORTH);
        timer.start();
        return pane;
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
            System.out.println("inside updateButtons");
            HashMap<MusicBand, MusicBandButton> newButtons = new HashMap<>();
            HashMap<MusicBand, MusicBandButton> oldButtons = new HashMap<>();
            for (MusicBand mb : list) {
                if (buttonHashMap.containsKey(mb)) {
                    System.out.println("old----------");
                    oldButtons.put(mb, buttonHashMap.get(mb));
                } else {
                    System.out.println("new");
                    newButtons.put(mb, create(mb));
                }
            }

            for (MusicBand mb : buttonHashMap.keySet()) {
                if (!oldButtons.containsKey(mb)) {
                    System.out.println("REMOVING=====");
                    visualizationPanel.remove(buttonHashMap.get(mb));
                    buttonHashMap.remove(mb);
                }
            }

            buttonHashMap.putAll(newButtons);

            for (MusicBand button : newButtons.keySet()) {
                System.out.println("ADDING++++++++++++");
                visualizationPanel.add(newButtons.get(button));
            }
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
