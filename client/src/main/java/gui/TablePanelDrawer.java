package gui;

import client.Console;
import musicband.MusicBand;
import network.Response;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
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

    private JPanel panel;
    private ArrayList<MusicBand> musicbands = new ArrayList<>();
    private volatile Vector<String> columnNames = new Vector<>();


    private volatile JTable table = new MusicTable();
    private JButton backButton = new JButton("Вернуться в меню");
    private JScrollPane scrollPane;

    public TablePanelDrawer(ActionListener frameManager, Console console, ListenerFactory listenerFactory) {
        this.frameManager = frameManager;
        this.console = console;
        this.listenerFactory = listenerFactory;
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
        backButton.addActionListener(frameManager);
//        table.setModel(buildTableModel());
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(table);
        JPanel pane = new JPanel(new GridBagLayout());
        pane.setBackground(new Color(0xED9CDE));
        pane.add(backButton, getBackButtonCons());
        pane.add(scrollPane, getTableCons());
        return pane;
    }

    @Override
    public synchronized JPanel drawPanel() {
        if (panel == null) {
            panel = initPanel();
        }
        updateTable();

        return panel;
    }

    public void updateTable() {
        SwingWorker swingWorker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                Response response = console.request("show");
                synchronized (table) {
                    synchronized (columnNames) {
                        if (response != null) {
                            TableModel tableModel = buildTableModel(response.getList());
                            tableModel.addTableModelListener(listenerFactory.createTableModelListener("update", table));
                            table.setModel(tableModel);
                            TableRowSorter<TableModel> sorter
                                    = new TableRowSorter<TableModel>(table.getModel());
                            table.setRowSorter(sorter);
                            table.repaint();
                        }
                    }
                }
                return null;
            }
        };
        swingWorker.execute();
        try {
            swingWorker.get(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException e) {
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
        constraints.insets = new Insets(40, 5, 5, 5);
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

    private class MusicTable extends JTable {
        public MusicTable() {
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return true;
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
            return true;
        }

        public void setValueAt(Object value, int row, int col) {
            Vector<Object> element = data.get(row);
            element.set(col, value);
            data.set(row, element);
            fireTableCellUpdated(row, col);
        }
    }
}
