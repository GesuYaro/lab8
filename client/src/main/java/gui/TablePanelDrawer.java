package gui;

import musicband.Coordinates;
import musicband.Label;
import musicband.MusicBand;
import musicband.MusicGenre;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Vector;

public class TablePanelDrawer implements PanelDrawer {

    private ActionListener frameManager;
    private JPanel panel;
    private ArrayList<MusicBand> musicbands = new ArrayList<>();
    private Vector<String> columnNames = new Vector<>();


    private JTable table = new MusicTable();
    private JButton backButton = new JButton("Вернуться в меню");
    private JScrollPane scrollPane;

    public TablePanelDrawer(ActionListener frameManager) {
        this.frameManager = frameManager;
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
        musicbands.add(new MusicBand(1,"ddd", new Coordinates(1L,1.0), LocalDate.now(), 2, 5, MusicGenre.RAP, new Label("popit")));
        musicbands.add(new MusicBand(2,"www", new Coordinates(1L,1.0), LocalDate.now(), 7, 7, MusicGenre.RAP, new Label("popit")));
        backButton.addActionListener(frameManager);
        table.setModel(buildTableModel());
        table.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(table);
        JPanel pane = new JPanel(new GridBagLayout());
        pane.setBackground(new Color(0xED9CDE));
        pane.add(backButton, getBackButtonCons());
        pane.add(scrollPane, getTableCons());
        return pane;
    }

    @Override
    public JPanel drawPanel() {
        if (panel == null) {
            panel = initPanel();
        }
        return panel;
    }

    public void updateTable() {
        panel.remove(scrollPane);
        table.setModel(buildTableModel());
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
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

    public DefaultTableModel buildTableModel() {
        Vector<Vector<Object>> data = new Vector<>();
        for (MusicBand musicBand : musicbands){
            Vector<Object> vector = musicBand.toVector();
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }

    public void setColumnNames(Vector<String> columnNames) {
        this.columnNames = columnNames;
    }

    private class MusicTable extends JTable {
        public MusicTable() {
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    /*private class Table extends AbstractTableModel {

        private int rowCount;
        private int columnCount;
        private String[] columnNames;
        private Object[][] data;

        @Override
        public int getRowCount() {
            return rowCount;
        }

        @Override
        public int getColumnCount() {
            return columnCount;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return getValueAt(0, columnIndex).getClass();
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            data[rowIndex][columnIndex] = aValue;
            fireTableCellUpdated(rowIndex, columnIndex);
        }

        public void setRowCount(int rowCount) {
            this.rowCount = rowCount;
        }

        public void setColumnCount(int columnCount) {
            this.columnCount = columnCount;
        }

        public String[] getColumnNames() {
            return columnNames;
        }

        public void setColumnNames(String[] columnNames) {
            this.columnNames = columnNames;
        }

        public Object[][] getData() {
            return data;
        }

        public void setData(Object[][] data) {
            this.data = data;
        }
    }*/

}
