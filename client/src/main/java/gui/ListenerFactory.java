package gui;

import app.LocaleManager;
import client.Authenticator;
import client.Console;
import client.UserManager;
import musicband.MusicBand;
import musicband.MusicGenre;
import network.CurrentUser;
import network.Response;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerFactory {

    private final Console console;
    private final FrameManager frameManager;
    private final LocaleManager localeManager;

    public ListenerFactory(Console console, FrameManager frameManager, LocaleManager localeManager) {
        this.console = console;
        this.frameManager = frameManager;
        this.localeManager = localeManager;
    }

    public ActionListener createSimpleDialogListener(Component panel, String commandName) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Response, Response> swingWorker = new SwingWorker<Response, Response>() {
                    @Override
                    protected Response doInBackground() throws Exception {
                        Response response = console.request(commandName);
                        if (response != null) {
                            if (response.getMessage() != null && response.getMessage().trim().equals("")) {
                                String message = "";
                                for (MusicBand mb : response.getList()) {
                                    message += mb.toString() + "\n";
                                }
                                JOptionPane.showMessageDialog(panel, message);
                            } else {
                                JOptionPane.showMessageDialog(panel, response.getMessage());
                            }
                        }
                        return null;
                    }
                };
                swingWorker.execute();
            }
        };
    }

    public ActionListener createFileChoosingListener(Component panel) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Response, Response> swingWorker = new SwingWorker<Response, Response>() {
                    @Override
                    protected Response doInBackground() throws Exception {
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        int result = fileChooser.showOpenDialog(panel);
                        if (result == JFileChooser.APPROVE_OPTION ) {
                            Response response = console.request("execute_script " + fileChooser.getSelectedFile().getAbsolutePath());
                            if (response != null) {
                                JOptionPane.showMessageDialog(panel, response.getMessage());
                            }
                        }
                        return null;
                    }
                };
                swingWorker.execute();
            }
        };
    }

    public ActionListener createAskingDialogListener(Component panel, String commandName) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Response, Response> swingWorker = new SwingWorker<Response, Response>() {
                    @Override
                    protected Response doInBackground() throws Exception {
                        String arg = JOptionPane.showInputDialog(panel, localeManager.getBundle().getString("enter argument"));
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

    public ActionListener createDeletingListener (Component panel, long id) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(panel, localeManager.getBundle().getString("delete?"), localeManager.getBundle().getString("delete?"), JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    Response response = null;
                    response = console.request("remove_by_id " + id);
                }
            }
        };
    }


    public ActionListener createShowChangeListener(Component panel, String commandName, MusicBand musicBand, boolean ownerView) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Response, Response> swingWorker = new SwingWorker<Response, Response>() {
                    @Override
                    protected Response doInBackground() throws Exception {
                        JPanel fieldsPanel = new JPanel(new GridBagLayout());
                        if (ownerView) {
                            if (e.getActionCommand().equals("1")) {
                                JTextField nameTF = new JTextField(musicBand.getName());
                                JTextField xTF = new JTextField(musicBand.getCoordinates().getX().toString());
                                JTextField yTF = new JTextField(musicBand.getCoordinates().getY().toString());
                                JTextField participantsTF = new JTextField("" + musicBand.getNumberOfParticipants());
                                JTextField singlesTF = new JTextField("" + musicBand.getSinglesCount());
                                JComboBox<MusicGenre> genreCB = new JComboBox<>(MusicGenre.values());
                                genreCB.setSelectedItem(musicBand.getGenre());
                                JTextField labelTF = new JTextField(musicBand.getLabel().getName());

                                JLabel idLabel = new JLabel(localeManager.getBundle().getString("id") + ": " + musicBand.getId());
                                JLabel nameLabel = new JLabel(localeManager.getBundle().getString("name") + ":");
                                JLabel xLabel = new JLabel(localeManager.getBundle().getString("x") + ":");
                                JLabel yLabel = new JLabel(localeManager.getBundle().getString("y") + ":");
                                JLabel dateLabel = new JLabel(localeManager.getBundle().getString("date") + ": " + localeManager.formatDate(musicBand.getCreationDate()));
                                JLabel participantsLabel = new JLabel(localeManager.getBundle().getString("participants") + ":");
                                JLabel singlesLabel = new JLabel(localeManager.getBundle().getString("singles") + ":");
                                JLabel genreLabel = new JLabel(localeManager.getBundle().getString("genre") + ":");
                                JLabel labelLabel = new JLabel(localeManager.getBundle().getString("label") + ":");

                                fieldsPanel.add(idLabel, getLabelCons(0));
                                fieldsPanel.add(nameLabel, getLabelCons(1));
                                fieldsPanel.add(xLabel, getLabelCons(2));
                                fieldsPanel.add(yLabel, getLabelCons(3));
                                fieldsPanel.add(dateLabel, getLabelCons(4));
                                fieldsPanel.add(participantsLabel, getLabelCons(5));
                                fieldsPanel.add(singlesLabel, getLabelCons(6));
                                fieldsPanel.add(genreLabel, getLabelCons(7));
                                fieldsPanel.add(labelLabel, getLabelCons(8));

                                fieldsPanel.add(nameTF, getTextFieldCons(1));
                                fieldsPanel.add(xTF, getTextFieldCons(2));
                                fieldsPanel.add(yTF, getTextFieldCons(3));
                                fieldsPanel.add(participantsTF, getTextFieldCons(5));
                                fieldsPanel.add(singlesTF, getTextFieldCons(6));
                                fieldsPanel.add(genreCB, getTextFieldCons(7));
                                fieldsPanel.add(labelTF, getTextFieldCons(8));

                                int result = JOptionPane.showConfirmDialog(panel, fieldsPanel,
                                        "Object", JOptionPane.OK_CANCEL_OPTION);
                                if (result == JOptionPane.OK_OPTION) {
                                    if (!nameTF.getText().equals(musicBand.getName()) || !xTF.getText().equals(musicBand.getCoordinates().getX().toString())
                                    || !yTF.getText().equals(musicBand.getCoordinates().getY().toString()) || !participantsTF.getText().equals(Integer.valueOf(musicBand.getNumberOfParticipants()).toString())
                                    || !singlesTF.getText().equals(musicBand.getSinglesCount().toString()) || (genreCB.getSelectedItem() != null && !genreCB.getSelectedItem().equals(musicBand.getGenre()))
                                    || !labelTF.getText().equals(musicBand.getLabel().getName())) {
                                        Response response = null;
                                        response = console.extendedRequest(commandName + " " + musicBand.getId(), nameTF.getText(), xTF.getText(),
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
                                }
                            } else {
                                if (e.getActionCommand().equals("3")) {
                                    int result = JOptionPane.showConfirmDialog(panel, fieldsPanel,
                                            localeManager.getBundle().getString("delete?"), JOptionPane.OK_CANCEL_OPTION);
                                    if (result == JOptionPane.OK_OPTION) {
                                        Response response = null;
                                        response = console.request("remove_by_id " + musicBand.getId());
                                    }
                                }
                            }
                        } else {
                            JLabel idLabel = new JLabel(localeManager.getBundle().getString("id") + ": " + musicBand.getId());
                            JLabel nameLabel = new JLabel(localeManager.getBundle().getString("name") + ": " + musicBand.getName());
                            JLabel xLabel = new JLabel(localeManager.getBundle().getString("x") + ": " + musicBand.getCoordinates().getX());
                            JLabel yLabel = new JLabel(localeManager.getBundle().getString("y") + ": " + localeManager.formatNumber(musicBand.getCoordinates().getY()));
                            JLabel dateLabel = new JLabel(localeManager.getBundle().getString("date") + ": " + localeManager.formatDate(musicBand.getCreationDate()));
                            JLabel participantsLabel = new JLabel(localeManager.getBundle().getString("participants") + ": " + musicBand.getNumberOfParticipants());
                            JLabel singlesLabel = new JLabel(localeManager.getBundle().getString("singles") + ": " + musicBand.getSinglesCount());
                            JLabel genreLabel = new JLabel(localeManager.getBundle().getString("genre") + ": " + musicBand.getGenre().name());
                            JLabel labelLabel = new JLabel(localeManager.getBundle().getString("label") + ": " + musicBand.getLabel().getName());

                            fieldsPanel.add(idLabel, getLabelCons(0));
                            fieldsPanel.add(nameLabel, getLabelCons(1));
                            fieldsPanel.add(xLabel, getLabelCons(2));
                            fieldsPanel.add(yLabel, getLabelCons(3));
                            fieldsPanel.add(dateLabel, getLabelCons(4));
                            fieldsPanel.add(participantsLabel, getLabelCons(5));
                            fieldsPanel.add(singlesLabel, getLabelCons(6));
                            fieldsPanel.add(genreLabel, getLabelCons(7));
                            fieldsPanel.add(labelLabel, getLabelCons(8));

                            JOptionPane.showMessageDialog(panel, fieldsPanel);
                        }
                        return null;
                    }
                };
                swingWorker.execute();
            }
        };
    }


    public ActionListener createExtendedAskingDialog(Component panel, String commandName, boolean hasArgument) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Response, Response> swingWorker = new SwingWorker<Response, Response>() {
                    @Override
                    protected Response doInBackground() throws Exception {

                        String arg = "";
                        if (hasArgument) {
                            arg = JOptionPane.showInputDialog(panel, localeManager.getBundle().getString("enter argument"));
                        }

//                        JPanel fieldsPanel = new JPanel(new GridBagLayout());

                        JTextField nameTF = new JTextField();
                        JTextField xTF = new JTextField();
                        JTextField yTF = new JTextField();
                        JTextField participantsTF = new JTextField();
                        JTextField singlesTF = new JTextField();
                        JComboBox<MusicGenre> genreCB = new JComboBox<>(MusicGenre.values());
                        JTextField labelTF = new JTextField();

                        FieldDialog fieldDialog = new FieldDialog(localeManager, nameTF, xTF, yTF, participantsTF, singlesTF, genreCB, labelTF, console, commandName, arg);
                        fieldDialog.setVisible(true);

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

    public ActionListener createSigningDialog(JPanel panel, JTextField loginField, JTextField passwordField, UserManager userManager, Authenticator authenticator, String commandName) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Response, Response> swingWorker = new SwingWorker<Response, Response>() {
                    @Override
                    protected Response doInBackground() throws Exception {
                        CurrentUser prevUser = userManager.getUser();
                        CurrentUser newUser = new CurrentUser(loginField.getText(), authenticator.password(passwordField.getText()));
                        userManager.setUser(newUser);
                        Response response = console.request(commandName);
                        if (response != null) {
                            if (response.getMessage() != null && !response.getMessage().trim().equals("Successful")) {
                                userManager.setUser(prevUser);
                            }
                            if (commandName.trim().equals("sign_in") && response.getMessage() != null && response.getMessage().trim().equals("Successful")) {
                                frameManager.actionPerformed(e);
                                frameManager.showMenu();
                            }
                            String message = response.getMessage();
                            if (response.getMessage() != null && response.getMessage().trim().equals("Successful")) {
                                message = localeManager.getBundle().getString("successful");
                            }
                            JOptionPane.showMessageDialog(panel, message);
                        } else {
                            userManager.setUser(prevUser);
                        }
                        return null;
                    }
                };
                swingWorker.execute();
            }
        };
    }

    public TableModelListener createTableModelListener(String commandName, JTable table) {
        return new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                SwingWorker swingWorker = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        TableModel tableModel = table.getModel();
                        int changedRow = e.getFirstRow();
                        int changedColumn = e.getColumn();
                        String id = tableModel.getValueAt(changedRow, 0).toString();
                        String name = tableModel.getValueAt(changedRow, 1).toString();
                        String x = tableModel.getValueAt(changedRow, 2).toString();
                        String y = tableModel.getValueAt(changedRow, 3).toString();
                        String date = tableModel.getValueAt(changedRow, 4).toString();
                        String participants = tableModel.getValueAt(changedRow, 5).toString();
                        String singles = tableModel.getValueAt(changedRow, 6).toString();
                        String genre = tableModel.getValueAt(changedRow, 7).toString();
                        String label = tableModel.getValueAt(changedRow, 8).toString();
                        console.extendedRequest(commandName + " " + id, name, x, y, participants, singles, genre, label);
                        return null;
                    }
                };
                swingWorker.execute();
            }
        };
    }

    public ActionListener createUpdateListener(VisualizationPanelDrawer drawer) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker swingWorker = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        Response response = console.request("show");
                        if (response != null) {
                            if (!drawer.getMusicBands().equals(response.getList())) {
//                                drawer.setMusicBands(response.getList());
                                drawer.updateButtons(response.getList());
                            }
                        }
                        return null;
                    }
                };
                swingWorker.execute();
            }
        };
    }

    public ActionListener createUpdateTableListener(TablePanelDrawer drawer) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker swingWorker = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        Response response = console.request("show");
                        if (response != null) {
                            if (!drawer.getMusicBands().equals(response.getList())) {
                                drawer.updateTable(response.getList());
                            }
                        }
                        return null;
                    }
                };
                swingWorker.execute();
            }
        };
    }

    public ActionListener createFilterListener(JPanel panel, StreamAPITableRowSorter<TablePanelDrawer.MusicTableModel> sorter, TablePanelDrawer.FilterKeyStore filterKeyStore, JTable table, int columnNumber) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker swingWorker = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        String result = JOptionPane.showInputDialog(panel, "", filterKeyStore.getNameFilter());
                        RowFilter<TablePanelDrawer.MusicTableModel, Integer> rf = null;
                        try {
                            rf = RowFilter.regexFilter(result, columnNumber);
                        } catch (java.util.regex.PatternSyntaxException e) {
                            return null;
                        }
                        filterKeyStore.setNameFilter(result);
                        sorter.setRowFilter(rf);
                        table.setRowSorter(sorter);
                        return null;
                    }
                };
                swingWorker.execute();

            }
        };
    }

    private JDialog createDialog(String title, JFrame frame) {
        JDialog dialog = new JDialog(frame, title, true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(180, 90);
        dialog.setLayout(new BorderLayout());
        return dialog;
    }

}
