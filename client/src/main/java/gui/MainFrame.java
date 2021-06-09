package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private int width = 800;
    private int height = 460;

    public MainFrame() {
        super("Music Bands");
        this.setSize(width, height);
        this.setMinimumSize(new Dimension(480, 440));
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        this.setLocation((screenSize.width - width)/2, (screenSize.height - height)/2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
    }


    public void updatePane(JPanel pane) {
        this.setContentPane(pane);
    }
}
