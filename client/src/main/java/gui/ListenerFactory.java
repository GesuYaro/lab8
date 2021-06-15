package gui;

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
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerFactory {

    private Console console;
    private FrameManager frameManager;

    public ListenerFactory(Console console, FrameManager frameManager) {
        this.console = console;
        this.frameManager = frameManager;
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

    public ActionListener createAskingDialogListener(Component panel, String commandName) {
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


    public ActionListener createExtendedAskingDialog(Component panel, String commandName, boolean hasArgument) {
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
                            JOptionPane.showMessageDialog(panel, response.getMessage());
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

    public ActionListener createFilterListener(JPanel panel, StreamAPITableRowSorter<TableModel> sorter, TablePanelDrawer.FilterKeyStore filterKeyStore) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker swingWorker = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        try {
                            String result = JOptionPane.showInputDialog(panel, "Фильтр:", filterKeyStore.getNameFilter());
                            RowFilter<TableModel, Object> rf = null;
                            try {
                                rf = RowFilter.regexFilter(result, 1);
                            } catch (java.util.regex.PatternSyntaxException e) {
                                return null;
                            }
                            filterKeyStore.setNameFilter(result);
                            sorter.setRowFilter(rf);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
