package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("Music Bands");
        this.setSize(800, 450);
        this.setMinimumSize(new Dimension(480, 440));
        this.setLocationByPlatform(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void updatePane(JPanel pane) {
        this.setContentPane(pane);
    }
}
