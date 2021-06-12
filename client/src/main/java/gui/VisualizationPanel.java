package gui;

import musicband.MusicBand;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class VisualizationPanel extends JPanel {

    private ArrayList<MusicBand> musicBands;

    public VisualizationPanel(ArrayList<MusicBand> musicBands) {
        this.musicBands = musicBands;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        for (MusicBand mb : musicBands) {
//            create(mb).paintComponent(g);
//        }
    }

    private MusicBandButton create(MusicBand mb) {
        int x = mb.getCoordinates().getX().intValue() + 200;
        int y = mb.getCoordinates().getY().intValue() + 200;
        int size = mb.getNumberOfParticipants();
        int r = Double.valueOf(Math.random() * 255).intValue();
        int g = Double.valueOf(Math.random() * 255).intValue();
        int b = Double.valueOf(Math.random() * 255).intValue();
        return new MusicBandButton(x, y, size, size, r, g, b);
    }


}
