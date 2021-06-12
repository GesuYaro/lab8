package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class MusicBandButton extends JComponent implements MouseListener {

    private int x;
    private int y;
    private int w;
    private int h;

    private Ellipse2D ellipse;
    private Timer timer;
    private int r;
    private int g;
    private int b;
    private int a;
    private boolean isAnimationDone;

    public MusicBandButton(int x, int y, int w, int h, int r, int g ,int b) {
        super();
        super.setSize(w, h);
        super.setLocation(x, y);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
//        enableInputMethods(true);
        addMouseListener(this);
        a = 0;
        this.r = r;
        this.g = g;
        this.b = b;
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAnimationDone) {
                    updateColor();
                    repaint();
                    if (a >= 254) {
                        isAnimationDone = true;
                    }
                }
            }
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("clicked " + e.getButton());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("pressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("released");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("entered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("exited");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        ellipse = new Ellipse2D.Double(0, 0, w, h);
        timer.start();
        drawCircle(g2);
    }

    private void drawCircle(Graphics2D g2) {
        g2.setPaint(new Color(r, g, b, a));
        g2.fill(ellipse);
    }

    private void updateColor() {
        a+=2;
    }

    @Override
    public Dimension getMinimumSize() {
        return super.getMinimumSize();
    }

    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return super.getMaximumSize();
    }

}
