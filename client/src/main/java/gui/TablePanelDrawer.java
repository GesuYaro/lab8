package gui;

import client.Console;
import client.UserManager;
import musicband.MusicBand;
import musicband.MusicGenre;
import network.Response;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TablePanelDrawer implements PanelDrawer {

    private ActionListener frameManager;
    private Console console;
    private ListenerFactory listenerFactory;
    private UserManager userManager;
    private String panelName = "Таблица";

    private JPanel panel;
    private ArrayList<MusicBand> musicbands = new ArrayList<>();
    private Vector<String> columnNames = new Vector<>();


    private volatile JTable table = new MusicTable();
    private JScrollPane scrollPane;

    public TablePanelDrawer(ActionListener frameManager, Console console, ListenerFactory listenerFactory, UserManager userManager) {
        this.frameManager = frameManager;
        this.console = console;
        this.listenerFactory = listenerFactory;
        this.userManager = userManager;
    }

    private JPanel initPanel() {
        columnNames.add("id");
        columnNames.add("name");
        columnNames.add("X");
        columnNames.add("Y");
        columnNames.add("date");
        columnNames.add("participants");
        columnNames.add("singles");
        columnNames.add("genre");
        columnNames.add("label");
//        table.setModel(buildTableModel());
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(table);
        JPanel pane = new JPanel(new GridBagLayout());
        pane.setBackground(new Color(0xED9CDE));
        pane.add(scrollPane, getTableCons());
        Timer timer = new Timer(3000, listenerFactory.createUpdateTableListener(this));
        timer.start();
        return pane;
    }

    @Override
    public String getPanelName() {
        return panelName;
    }

    @Override
    public synchronized JPanel drawPanel() {
        if (panel == null) {
            panel = initPanel();
        }

        return panel;
    }

    public void updateTable(ArrayList<MusicBand> newMusicBands) {
        SwingWorker swingWorker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                synchronized (table) {
                    synchronized (columnNames) {
                        synchronized (musicbands) {
                            if (musicbands.size() != newMusicBands.size()) {
                                musicbands = newMusicBands;
                                TableModel tableModel = buildTableModel(musicbands);
                                table.setModel(tableModel);
                                tableModel.addTableModelListener(listenerFactory.createTableModelListener("update", table));
                                StreamAPITableRowSorter<TableModel> sorter
                                        = new StreamAPITableRowSorter<TableModel>(tableModel);
                                table.setRowSorter(sorter);
                                TableColumn genreColumn = table.getColumnModel().getColumn(7);
                                JComboBox<String> comboBox = new JComboBox<>();
                                for (MusicGenre genre : MusicGenre.values()) {
                                    comboBox.addItem(genre.name());
                                }
                                genreColumn.setCellEditor(new DefaultCellEditor(comboBox));
                            } else {
                                try {
                                    if (!musicbands.equals(newMusicBands) && !musicbands.isEmpty()) {
                                        ArrayList<MusicBand> changed = new ArrayList<>(newMusicBands);
                                        changed.removeAll(musicbands);
                                        for (int row = 0; row < table.getRowCount(); row++) {
                                            for (int changedRow = 0; changedRow < changed.size(); changedRow++) {
                                                if (Long.valueOf(changed.get(changedRow).getId()).equals(table.getModel().getValueAt(row, 0))) {
                                                    MusicBand newMusicBand = changed.get(changedRow);
                                                    table.getModel().setValueAt(newMusicBand.getName(), row, 1);
                                                    table.getModel().setValueAt(newMusicBand.getCoordinates().getX(), row, 2);
                                                    table.getModel().setValueAt(newMusicBand.getCoordinates().getY(), row, 3);
                                                    table.getModel().setValueAt(newMusicBand.getCreationDate(), row, 4);
                                                    table.getModel().setValueAt(newMusicBand.getNumberOfParticipants(), row, 5);
                                                    table.getModel().setValueAt(newMusicBand.getSinglesCount(), row, 6);
                                                    table.getModel().setValueAt(newMusicBand.getGenre().name(), row, 7);
                                                    table.getModel().setValueAt(newMusicBand.getLabel().getName(), row, 8);
                                                }
                                            }
                                        }
                                        musicbands = newMusicBands;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

//                        table.repaint();
                        }
                    }
                }
                return null;
            }
        };
        swingWorker.execute();
        try {
            swingWorker.get(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Interrupted");
            e.printStackTrace();
        } catch (TimeoutException ignored) {}
    }

    private GridBagConstraints getBackButtonCons() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.weighty = 0.5;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.weightx = 0.5;
        return constraints;
    }

    private GridBagConstraints getTableCons() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.SOUTH;
        constraints.weighty = 0.5;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.weightx = 0.5;
        return constraints;
    }

    public MusicTableModel buildTableModel(ArrayList<MusicBand> musicbands) {
        Vector<Vector<Object>> data = new Vector<>();
        for (MusicBand musicBand : musicbands){
            Vector<Object> vector = musicBand.toVector();
            data.add(vector);
        }
        return new MusicTableModel(data, columnNames);
    }

    public void setColumnNames(Vector<String> columnNames) {
        this.columnNames = columnNames;
    }

    public ArrayList<MusicBand> getMusicBands() {
        return musicbands;
    }

    public void setMusicBands(ArrayList<MusicBand> musicbands) {
        this.musicbands = musicbands;
    }

    private class MusicTable extends JTable {
        public MusicTable() {
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            if (column != 0 && column != 4) {
                if (userManager.getUser() != null) {
                    return getMusicBands().get(row).getOwner().equals(userManager.getUser().getLogin());
                }
            }
            return false;
        }
    }

    class MusicTableModel extends AbstractTableModel {

        private Vector<Vector<Object>> data;
        private Vector<String> columnNames;

        public MusicTableModel(Vector<Vector<Object>> data, Vector<String> columnNames) {
            super();
            this.data = data;
            this.columnNames = columnNames;
        }

        public int getColumnCount() {
            return columnNames.size();
        }

        public int getRowCount() {
            return data.size();
        }

        public String getColumnName(int col) {
            return columnNames.get(col);
        }

        public Object getValueAt(int row, int col) {
            return data.get(row).get(col);
        }

        public Class getColumnClass(int c) {
            Class clazz = Object.class;
            switch (c) {
                case 0 :
                case 2 :
                    clazz = Long.class;
                    break;
                case 1 :
                case 7 :
                case 8 :
                    clazz = String.class;
                    break;
                case 3 :
                    clazz = Double.class;
                    break;
                case 4 :
                    clazz = LocalDate.class;
                    break;
                case 5 :
                case 6 :
                    clazz = Integer.class;
                    break;
            }
            return clazz;
        }

        public boolean isCellEditable(int row, int col) {
            if (col != 0 && col != 4)
            if (userManager.getUser() != null) {
                boolean o = getMusicBands().get(row).getOwner().equals(userManager.getUser().getLogin());
                System.out.println(o);
                return o;
            }
            return false;
        }

        public void setValueAt(Object value, int row, int col) {
            Vector<Object> element = data.get(row);
            element.set(col, value);
            data.set(row, element);
            fireTableCellUpdated(row, col);
        }
    }
}
