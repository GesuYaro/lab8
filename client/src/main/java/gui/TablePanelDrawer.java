package gui;

import app.LocaleManager;
import client.Console;
import client.UserManager;
import musicband.MusicBand;
import musicband.MusicGenre;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class TablePanelDrawer implements PanelDrawer, LanguageChangeable {

    private final ActionListener frameManager;
    private final Console console;
    private final ListenerFactory listenerFactory;
    private final UserManager userManager;
    private final LocaleManager localeManager;
    private String panelName;
    private final String filterSign = "\u26DB";
    private volatile StreamAPITableRowSorter<MusicTableModel> sorter;

    private final JButton filterButton0 = new JButton(filterSign);
    private final JButton filterButton1 = new JButton(filterSign);
    private final JButton filterButton2 = new JButton(filterSign);
    private final JButton filterButton3 = new JButton(filterSign);
    private final JButton filterButton4 = new JButton(filterSign);
    private final JButton filterButton5 = new JButton(filterSign);
    private final JButton filterButton6 = new JButton(filterSign);
    private final JButton filterButton7 = new JButton(filterSign);
    private final JButton filterButton8 = new JButton(filterSign);

    private JPanel panel;
    private ArrayList<MusicBand> musicbands = new ArrayList<>();
    private Vector<String> columnNames = new Vector<>();

    private final FilterKeyStore nameFilterStore = new FilterKeyStore();
    private final FilterKeyStore idFilterStore = new FilterKeyStore();
    private final FilterKeyStore xFilterStore = new FilterKeyStore();
    private final FilterKeyStore yFilterStore = new FilterKeyStore();
    private final FilterKeyStore dateFilterStore = new FilterKeyStore();
    private final FilterKeyStore participantsFilterStore = new FilterKeyStore();
    private final FilterKeyStore singlesFilterStore = new FilterKeyStore();
    private final FilterKeyStore genreFilterStore = new FilterKeyStore();
    private final FilterKeyStore labelFilterStore = new FilterKeyStore();

    private final JTable table = new JTable();
    private JScrollPane scrollPane;

    public TablePanelDrawer(ActionListener frameManager, Console console, ListenerFactory listenerFactory, UserManager userManager, LocaleManager localeManager) {
        this.frameManager = frameManager;
        this.console = console;
        this.listenerFactory = listenerFactory;
        this.userManager = userManager;
        this.localeManager = localeManager;
        panelName = localeManager.getBundle().getString("table");
    }

    private JPanel initPanel() {
        columnNames.add(localeManager.getBundle().getString("id"));
        columnNames.add(localeManager.getBundle().getString("name"));
        columnNames.add(localeManager.getBundle().getString("x"));
        columnNames.add(localeManager.getBundle().getString("y"));
        columnNames.add(localeManager.getBundle().getString("date"));
        columnNames.add(localeManager.getBundle().getString("participants"));
        columnNames.add(localeManager.getBundle().getString("singles"));
        columnNames.add(localeManager.getBundle().getString("genre"));
        columnNames.add(localeManager.getBundle().getString("label"));
//        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(table);
        JPanel pane = new JPanel(new GridBagLayout());
        pane.setBackground(new Color(0xED9CDE));

        table.setDefaultRenderer(LocalDate.class, new DateRenderer());

        MusicTableModel tableModel = buildTableModel(musicbands);
        table.setModel(tableModel);
        tableModel.addTableModelListener(listenerFactory.createTableModelListener("update", table));

        pane.add(scrollPane, getTableCons());

        pane.add(filterButton0, getBackButtonCons(0, 0));
        pane.add(filterButton1, getBackButtonCons(1, 0));
        pane.add(filterButton2, getBackButtonCons(2, 0));
        pane.add(filterButton3, getBackButtonCons(3, 0));
        pane.add(filterButton4, getBackButtonCons(4, 0));
        pane.add(filterButton5, getBackButtonCons(5, 0));
        pane.add(filterButton6, getBackButtonCons(6, 0));
        pane.add(filterButton7, getBackButtonCons(7, 0));
        pane.add(filterButton8, getBackButtonCons(8, 0));

        panelName = localeManager.getBundle().getString("table");

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (e.getButton() == 3 && table.isCellEditable(row, 1)) {
                    Long id = (Long) table.getValueAt(row, 0);
                    listenerFactory.createDeletingListener(panel, id).actionPerformed(new ActionEvent(table, ActionEvent.ACTION_PERFORMED, "" + e.getButton(), e.getWhen(), e.getModifiers()));
                }
            }
        });

        Timer timer = new Timer(2000, listenerFactory.createUpdateTableListener(this));
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
                    synchronized (musicbands) {
                        if (musicbands.size() != newMusicBands.size() && newMusicBands.size() != 0) {
                            musicbands = newMusicBands;
                            MusicTableModel tableModel = buildTableModel(musicbands);
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    table.setModel(tableModel);
                                    tableModel.addTableModelListener(listenerFactory.createTableModelListener("update", table));
                                    sorter = new StreamAPITableRowSorter<MusicTableModel>(tableModel);
                                    filterButton0.addActionListener(listenerFactory.createFilterListener(panel, sorter, idFilterStore, table, 0));
                                    filterButton1.addActionListener(listenerFactory.createFilterListener(panel, sorter, nameFilterStore, table, 1));
                                    filterButton2.addActionListener(listenerFactory.createFilterListener(panel, sorter, xFilterStore, table, 2));
                                    filterButton3.addActionListener(listenerFactory.createFilterListener(panel, sorter, yFilterStore, table, 3));
                                    filterButton4.addActionListener(listenerFactory.createFilterListener(panel, sorter, dateFilterStore, table, 4));
                                    filterButton5.addActionListener(listenerFactory.createFilterListener(panel, sorter, participantsFilterStore, table, 5));
                                    filterButton6.addActionListener(listenerFactory.createFilterListener(panel, sorter, singlesFilterStore, table, 6));
                                    filterButton7.addActionListener(listenerFactory.createFilterListener(panel, sorter, genreFilterStore, table, 7));
                                    filterButton8.addActionListener(listenerFactory.createFilterListener(panel, sorter, labelFilterStore, table, 8));

                                    table.setRowSorter(sorter);
                                    TableColumn genreColumn = table.getColumnModel().getColumn(7);
                                    JComboBox<String> comboBox = new JComboBox<>();
                                    for (MusicGenre genre : MusicGenre.values()) {
                                        comboBox.addItem(genre.name());
                                    }
                                    genreColumn.setCellEditor(new DefaultCellEditor(comboBox));
                                }
                            });
                        } else {
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
                        }

                    }
                }
                return null;
            }
        };
        swingWorker.execute();
    }

    private GridBagConstraints getBackButtonCons(int gridx, int gridy) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.weighty = 0.5;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridy = gridy;
        constraints.gridx = gridx;
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
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        return constraints;
    }


    public MusicTableModel buildTableModel(ArrayList<MusicBand> musicbands) {
        Vector<Vector<Object>> data = new Vector<>();
        for (MusicBand musicBand : musicbands) {
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

    @Override
    public void updateLanguage() {
        if (panel != null) {
            try {
                panelName = localeManager.getBundle().getString("table");
                columnNames.clear();
                columnNames.add(localeManager.getBundle().getString("id"));
                columnNames.add(localeManager.getBundle().getString("name"));
                columnNames.add(localeManager.getBundle().getString("x"));
                columnNames.add(localeManager.getBundle().getString("y"));
                columnNames.add(localeManager.getBundle().getString("date"));
                columnNames.add(localeManager.getBundle().getString("participants"));
                columnNames.add(localeManager.getBundle().getString("singles"));
                columnNames.add(localeManager.getBundle().getString("genre"));
                columnNames.add(localeManager.getBundle().getString("label"));
                MusicTableModel tableModel = buildTableModel(musicbands);
                table.setModel(tableModel);
                table.repaint();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class MusicTableModel extends AbstractTableModel {

        private final Vector<Vector<Object>> data;
        private final Vector<String> columnNames;

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
            if (row <= data.size() - 1 && col <= columnNames.size() - 1) {
                return data.get(row).get(col);
            } else {
                return null;
            }
        }

        public Class getColumnClass(int c) {
            Class clazz = Object.class;
            switch (c) {
                case 0:
                case 2:
                    clazz = Long.class;
                    break;
                case 1:
                case 7:
                case 8:
                    clazz = String.class;
                    break;
                case 3:
                    clazz = Double.class;
                    break;
                case 4:
                    clazz = LocalDate.class;
                    break;
                case 5:
                case 6:
                    clazz = Integer.class;
                    break;
            }
            return clazz;
        }

        public boolean isCellEditable(int row, int col) {
            if (col != 0 && col != 4)
                if (userManager.getUser() != null) {
                    return getMusicBands().get(row).getOwner().equals(userManager.getUser().getLogin());
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

    public class FilterKeyStore {
        private String nameFilter;

        public FilterKeyStore() {
            nameFilter = ".";
        }

        public String getNameFilter() {
            return nameFilter;
        }

        public void setNameFilter(String nameFilter) {
            this.nameFilter = nameFilter;
        }
    }

    class DateRenderer extends DefaultTableCellRenderer {
        public DateRenderer() {
            super();
        }

        public void setValue(Object value) {
            if (value != null && value.getClass().equals(LocalDate.class)) {
                Date date = java.util.Date.from(LocalDate.parse(value.toString()).atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
                setText(localeManager.formatDate(date));
            } else setText("illegal");
        }
    }
}
