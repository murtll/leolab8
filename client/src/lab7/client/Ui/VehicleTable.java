package lab7.client.Ui;

import lab7.client.Entity.Coordinates;
import lab7.client.Entity.Vehicle;
import sun.awt.image.IntegerComponentRaster;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class VehicleTable extends JTable {
    private Vector<Vehicle> vehicles;

    public void init(Vector<Vehicle> vehicles) {
        if (vehicles != null) {
            this.vehicles = vehicles;

            String[] columnNames = new String[]{"id", "name", "coordinates", "created", "engine_power", "capacity", "vehicle_type", "fuel_type"};
            Object[][] vehicleData = vehicles
                    .stream()
                    .map(v -> new Object[]{
                            v.getId(),
                            v.getName(),
                            v.getCoordinates(),
                            v.getCreationDate(),
                            v.getEnginePower(),
                            v.getCapacity(),
                            v.getVehicleType(),
                            v.getFuelType()
                    }).collect(Collectors.toList()).toArray(new Object[vehicles.size()][columnNames.length]);

            setModel(new VehicleTableModel(vehicleData, columnNames));
        } else {
            setModel(new VehicleTableModel());
        }
        setAutoCreateRowSorter(true);
        ((DefaultRowSorter) getRowSorter()).setComparator(2, Comparator.comparing((c) -> (int) (Math.sqrt(((Coordinates) c).getX() * ((Coordinates) c).getX() + ((Coordinates) c).getY() * ((Coordinates) c).getY()))));
//        // Set a row sorter for the table
//        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter(getModel());
//        setRowSorter(sorter);
//
//
//        RowFilter<DefaultTableModel, Comparable> IDFilter = new RowFilter<DefaultTableModel, Integer> () {
//                    @Override
//                    public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
//                            TableModel model = getModel();
//                            int rowIndex = entry.getIdentifier().intValue();
//                            Integer ID = (Integer) model.getValueAt(rowIndex, 0);
//                            if (ID.intValue() <= 100) {
//                                return false;
//                            }
//                            return true;
//                        }
//
//                };

//        sorter.setRowFilter(IDFilter);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        c.setBackground(new Color(vehicles.get(row).getUserColor()));
        return c;
    }

    public Vector<Vehicle> getVehicles() {
        return vehicles;
    }
}

class VehicleTableModel extends DefaultTableModel {

    public VehicleTableModel() {
        super();
    }

    public VehicleTableModel(Object[][] data, Object[] columns) {
        super(data, columns);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (getRowCount() == 0) return Object.class;
        return getValueAt(0, columnIndex).getClass();
    }


}