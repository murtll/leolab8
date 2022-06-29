package lab7.client.Ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupByDateTable extends JTable {
    public void init(Map<LocalDate, Long> groups) {
        String[] columnNames = new String[]{"date", "count"};
        if (groups != null) {
            Object[][] data = groups.entrySet()
                    .stream()
                    .map(g -> new Object[]{
                            g.getKey(),
                            g.getValue()
                    }).collect(Collectors.toList()).toArray(new Object[groups.entrySet().size()][columnNames.length]);

            setModel(new DefaultTableModel(data, columnNames));
        } else {
            setModel(new DefaultTableModel(new Object[][]{}, columnNames));
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}