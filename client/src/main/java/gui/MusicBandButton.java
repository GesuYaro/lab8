package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class MusicBandButton extends JComponent implements MouseListener {

    private int x;
    private int y;
    private int w;
    private int h;

    private Ellipse2D leftCircle;
    private Ellipse2D rightCircle;
    private RoundRectangle2D bodyRectangle;
    private Rectangle2D stickerRectangle;
    private RoundRectangle2D innerRectangle;
    private Line2D upperLine;
    private Line2D leftLine;
    private Line2D rightLine;

    private Timer timer;
    private int r;
    private int g;
    private int b;
    private int a;
    private boolean isAnimationDone;

    public MusicBandButton(int x, int y, int w, int h, int r, int g ,int b) {
        super();
        super.setSize(w, Double.valueOf(w * 0.65).intValue());
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
                    if (a >= 200) {
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
        bodyRectangle = new RoundRectangle2D.Double(0, 0, w, w * 0.65, w * 0.2, w * 0.2);
        stickerRectangle = new Rectangle2D.Double(w * 0.09, w * 0.09, w * 0.83, w * 0.65 * 0.48);
        innerRectangle = new RoundRectangle2D.Double(w * 0.2, w * 0.2, w * 0.6, w * 0.15, w * 0.2, w * 0.2);
        leftCircle = new Ellipse2D.Double(w * 0.25, w * 0.22, w * 0.12, w * 0.12);
        rightCircle = new Ellipse2D.Double(w * 0.62, w * 0.22, w * 0.12, w * 0.12);
        upperLine = new Line2D.Double(w * 0.2, w * 0.43, w * 0.8, w * 0.43);
        leftLine = new Line2D.Double(w * 0.16, w * 0.65, w * 0.2, w * 0.43);
        rightLine = new Line2D.Double(w * 0.8, w * 0.43, w * 0.84, w * 0.65);
        timer.start();
        draw(g2);
    }

    private void draw(Graphics2D g2) {
        Color bodyColor = new Color(r, g, b, a);
        Color stickerColor = new Color(b, r, g, a);
        Color circleColor = new Color(g, b, r ,a);
        g2.setPaint(bodyColor);
        g2.fill(bodyRectangle);
        g2.setPaint(stickerColor);
        g2.fill(stickerRectangle);
        g2.setPaint(bodyColor);
        g2.fill(innerRectangle);
        g2.setPaint(circleColor);
        g2.fill(leftCircle);
        g2.fill(rightCircle);
        g2.draw(upperLine);
        g2.draw(leftLine);
        g2.draw(rightLine);
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

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
}
