package gui;

import app.LocaleManager;
import client.Console;
import musicband.InputValueException;
import musicband.MusicBand;
import musicband.MusicBandFieldsChecker;
import musicband.MusicGenre;
import network.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FieldDialog extends JDialog {

    private final MusicBandFieldsChecker checker;

    public FieldDialog(LocaleManager localeManager, JTextField nameTF, JTextField xTF, JTextField yTF, JTextField participantsTF, JTextField singlesTF, JComboBox<MusicGenre> genreCB, JTextField labelTF, Console console, String commandName, String arg) {
        super(StartGUI.getMainFrame(), true);
        this.setSize(300, 400);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        this.setLocation(new Point((screenSize.width - this.getWidth())/2, (screenSize.height - this.getHeight())/2));

        checker = new MusicBandFieldsChecker();

        JPanel fieldsPanel = new JPanel(new GridBagLayout());


        JLabel nameLabel = new JLabel(localeManager.getBundle().getString("name") + ":");
        JLabel xLabel = new JLabel(localeManager.getBundle().getString("x") + ":");
        JLabel yLabel = new JLabel(localeManager.getBundle().getString("y") + ":");
        JLabel participantsLabel = new JLabel(localeManager.getBundle().getString("participants") + ":");
        JLabel singlesLabel = new JLabel(localeManager.getBundle().getString("singles") + ":");
        JLabel genreLabel = new JLabel(localeManager.getBundle().getString("genre") + ":");
        JLabel labelLabel = new JLabel(localeManager.getBundle().getString("label") + ":");

        JButton okButton = new JButton("OK");

        fieldsPanel.add(nameLabel, getLabelCons(0));
        fieldsPanel.add(xLabel, getLabelCons(1));
        fieldsPanel.add(yLabel, getLabelCons(2));
        fieldsPanel.add(participantsLabel, getLabelCons(3));
        fieldsPanel.add(singlesLabel, getLabelCons(4));
        fieldsPanel.add(genreLabel, getLabelCons(5));
        fieldsPanel.add(labelLabel, getLabelCons(6));

        fieldsPanel.add(nameTF, getTextFieldCons(0));
        fieldsPanel.add(xTF, getTextFieldCons(1));
        fieldsPanel.add(yTF, getTextFieldCons(2));
        fieldsPanel.add(participantsTF, getTextFieldCons(3));
        fieldsPanel.add(singlesTF, getTextFieldCons(4));
        fieldsPanel.add(genreCB, getTextFieldCons(5));
        fieldsPanel.add(labelTF, getTextFieldCons(6));

        fieldsPanel.add(okButton, getTextFieldCons(7));

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    checker.readName(nameTF.getText());
                    checker.readX(xTF.getText());
                    checker.readY(yTF.getText());
                    checker.readNumberOfParticipants(participantsTF.getText());
                    checker.readSinglesCount(singlesTF.getText());
                    checker.readMusicGenre(genreCB.getSelectedItem().toString());
                    checker.readLabel(labelTF.getText());
                    Response response = null;
                    response = console.extendedRequest(commandName + " " + arg, nameTF.getText(), xTF.getText(),
                            yTF.getText(), participantsTF.getText(), singlesTF.getText(),
                            genreCB.getSelectedItem() != null ? genreCB.getSelectedItem().toString() : "",
                            labelTF.getText());
                    if (response != null) {
                        String message = "";
                        if (response.getMessage() != null) {
                            message += response.getMessage();
                        }
                        if (response.getList() != null) {
                            for (MusicBand mb : response.getList()) {
                                message += mb.toString() + "\n";
                            }
                        }
                        JOptionPane.showMessageDialog(FieldDialog.this, message);
                    }
                    FieldDialog.this.setVisible(false);
                } catch (InputValueException exception) {
                    JOptionPane.showMessageDialog(FieldDialog.this, localeManager.getBundle().getString(exception.getMessage()));
                }
            }
        });

        this.setContentPane(fieldsPanel);
    }

    private GridBagConstraints getLabelCons(int gridy) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = gridy;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.weightx = 0.1;
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
        constraints.weightx = 1.0;
        return constraints;
    }


}
