package gui;

import client.Console;
import musicband.MusicBand;
import musicband.MusicGenre;
import network.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerFactory {

    private Console console;

    public ListenerFactory(Console console) {
        this.console = console;
    }

    public ActionListener createSimpleDialogListener(JPanel panel, String commandName) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Response, Response> swingWorker = new SwingWorker<Response, Response>() {
                    @Override
                    protected Response doInBackground() throws Exception {
                        Response response = console.request(commandName);
                        if (response != null) {
                            JOptionPane.showMessageDialog(panel, response.getMessage());
                        }
                        return null;
                    }
                };
                swingWorker.execute();
            }
        };
    }

    public ActionListener createAskingDialogListener(JPanel panel, String commandName) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Response, Response> swingWorker = new SwingWorker<Response, Response>() {
                    @Override
                    protected Response doInBackground() throws Exception {
                        String arg = JOptionPane.showInputDialog(panel, "Введите аргумент");
                        if (arg != null && !arg.equals("")) {
                            Response response = console.request(commandName + " " + arg);
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
                                JOptionPane.showMessageDialog(panel, message);
                            }
                        }
                        return null;
                    }
                };
                swingWorker.execute();
            }
        };
    }


    public ActionListener createExtendedAskingDialog(JPanel panel, String commandName, boolean hasArgument) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Response, Response> swingWorker = new SwingWorker<Response, Response>() {
                    @Override
                    protected Response doInBackground() throws Exception {

                        String arg = "";
                        if (hasArgument) {
                            arg = JOptionPane.showInputDialog(panel, "Введите аргумент");
                        }

                        JPanel fieldsPanel = new JPanel(new GridBagLayout());

                        JTextField nameTF = new JTextField();
                        JTextField xTF = new JTextField();
                        JTextField yTF = new JTextField();
                        JTextField participantsTF = new JTextField();
                        JTextField singlesTF = new JTextField();
                        JComboBox<MusicGenre> genreCB = new JComboBox<>(MusicGenre.values());
                        JTextField labelTF = new JTextField();

                        JLabel nameLabel = new JLabel("name:");
                        JLabel xLabel = new JLabel("x:");
                        JLabel yLabel = new JLabel("y:");
                        JLabel participantsLabel = new JLabel("number of participants:");
                        JLabel singlesLabel = new JLabel("singles count:");
                        JLabel genreLabel = new JLabel("genre:");
                        JLabel labelLabel = new JLabel("label:");

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

                        int result = JOptionPane.showConfirmDialog(panel, fieldsPanel,
                                "Please Enter Fields", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
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
                                JOptionPane.showMessageDialog(panel, message);
                            }
                        }
                        return null;
                    }
                };
                swingWorker.execute();
            }
        };
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
