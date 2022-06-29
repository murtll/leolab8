package lab7.client.Ui;

import lab7.client.Di.DependencyBag;
import lab7.client.Entity.FuelType;
import lab7.client.Entity.Vehicle;
import lab7.client.Entity.VehicleType;
import lab7.client.Locale.*;
import lab7.client.Network.Events.EventDataDto;
import lab7.client.Network.Events.EventListener;
import lab7.client.Network.Events.EventType;
import lab7.client.Network.Events.Notifiable;
import lab7.client.Network.ServerResponseDto;
import lab7.client.Smth.Commander;
import lab7.client.Smth.CredentialHandler;
import lab7.client.Smth.ServerCommander;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.stream.Collectors;

public class MainPage extends AbstractLocalizableFrame implements Notifiable {
    private VehicleTable vehicleTable;
    private JPanel mainPanel;
    private LocaleBox localeBox;
    private LocalizableLabel statusLabel;
    private JLabel usernameLabel;
    private JToolBar toolBar;
    private LocalizableButton addButton;
    private LocalizableButton clearButton;
    private LocalizableButton executeButton;
    private LocalizableButton removeLastButton;
    private LocalizableButton shuffleButton;
    private LocalizableButton groupByDateButton;
    private LocalizableButton countLessFuelTypeButton;
    private JComboBox<FuelType> fuelTypeBox;
    private LocalizableButton countByEnginePowerButton;
    private JSpinner enginePowerSpinner;
    private JTextField idFilter;
    private JTextField nameFilter;
    private JTextField coordinatesFilter;
    private JTextField dateFilter;
    private JTextField enginePowerFilter;
    private JTextField capacityFilter;
    private JComboBox<VehicleType> vehicleTypeFilter;
    private JComboBox<FuelType> fuelTypeFilter;

    private final JFileChooser fileChooser = new JFileChooser();

    private AbstractLocalizableDialog currentDialog = null;
    private JDialog currentMiniDialog = null;

    ServerCommander serverCommander = DependencyBag.getSingleton(ServerCommander.class);

    Vector<Vehicle> vehicles;


    public MainPage() {
        super("vehicles");

        addButton.setKey("add");
        clearButton.setKey("clear");
        executeButton.setKey("execute_script");
        removeLastButton.setKey("remove_last");
        shuffleButton.setKey("shuffle");
        groupByDateButton.setKey("group_by_date");
        countLessFuelTypeButton.setKey("count_less_than_fuel_type");
        countByEnginePowerButton.setKey("count_by_engine_power");

        vehicleTypeFilter.addItem(null);
        fuelTypeFilter.addItem(null);

        for (FuelType t : FuelType.values()) {
            fuelTypeBox.addItem(t);
            fuelTypeFilter.addItem(t);
        }

        for (VehicleType t : VehicleType.values()) {
            vehicleTypeFilter.addItem(t);
        }

        usernameLabel.setForeground(new Color(CredentialHandler.getCredentials().getColor()));
        usernameLabel.setText(CredentialHandler.getCredentials().getUsername());
        localeBox.init();

        setContentPane(mainPanel);
        setMinimumSize(new Dimension(700, 400));
        pack();
        setLocationRelativeTo(null);

        fillTable();

        addButton.addActionListener((e) -> openAddDialog());
        addButton.addKeyListener(new EnterListener(this::openAddDialog));

        vehicleTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int row = vehicleTable.rowAtPoint(e.getPoint());
                if (row >= 0 && row < vehicleTable.getRowCount()) {
                    vehicleTable.setRowSelectionInterval(row, row);
                } else {
                    vehicleTable.clearSelection();
                }

                openViewDialog(vehicleTable.getVehicles().get(row));
            }
        });

        clearButton.addActionListener((e) -> clearCollection());
        executeButton.addActionListener((e) -> executeFile());
        removeLastButton.addActionListener((e) -> removeLast());
        shuffleButton.addActionListener((e) -> shuffle());
        groupByDateButton.addActionListener((e) -> openGroupByDateDialog());
        countByEnginePowerButton.addActionListener((e) -> openCountByEnginePower());
        enginePowerSpinner.setModel(new SpinnerNumberModel(1, 1, 100, 1));
        countLessFuelTypeButton.addActionListener((e) -> openCountLessThanFuelType());

        enginePowerSpinner.setAlignmentX(SwingConstants.CENTER);
        fuelTypeBox.setAlignmentX(SwingConstants.CENTER);

        DocumentListener filterChangeListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable();
            }
        };

        idFilter.getDocument().addDocumentListener(filterChangeListener);
        nameFilter.getDocument().addDocumentListener(filterChangeListener);
        coordinatesFilter.getDocument().addDocumentListener(filterChangeListener);
        dateFilter.getDocument().addDocumentListener(filterChangeListener);
        enginePowerFilter.getDocument().addDocumentListener(filterChangeListener);
        dateFilter.getDocument().addDocumentListener(filterChangeListener);
        capacityFilter.getDocument().addDocumentListener(filterChangeListener);
        vehicleTypeFilter.addActionListener(e -> filterTable());
        fuelTypeFilter.addActionListener(e -> filterTable());

        EventListener eventListener = DependencyBag.getSingleton(EventListener.class);
        eventListener.subscribe(this);
        try {
            eventListener.startListening();
        } catch (IOException e) {
            System.err.println("Cannot subscribe to server events");
        }
    }

    private void filterTable() {
        Vector<Vehicle> filteredVehicles = vehicles.stream().filter((v) -> {
            if (!String.valueOf(v.getId()).contains(idFilter.getText())) return false;
            if (!v.getName().contains(nameFilter.getText())) return false;
            if (!v.getCoordinates().toString().contains(coordinatesFilter.getText())) return false;
            if (!v.getCreationDate().toString().contains(dateFilter.getText())) return false;
            if (!v.getEnginePower().toString().contains(enginePowerFilter.getText())) return false;
            if (!v.getCapacity().toString().contains(capacityFilter.getText())) return false;
            if (vehicleTypeFilter.getSelectedItem() != null && v.getVehicleType() != vehicleTypeFilter.getSelectedItem()) return false;
            if (fuelTypeFilter.getSelectedItem() != null && v.getFuelType() != fuelTypeFilter.getSelectedItem()) return false;

            return true;
        }).collect(Collectors.toCollection(Vector::new));

        vehicleTable.init(filteredVehicles);
    }

    private void openCountLessThanFuelType() {
        new Thread(() -> {
            try {
                if (currentMiniDialog != null) {
                    return;
                }

                ServerResponseDto response = serverCommander.countLessThanFuelType((FuelType) fuelTypeBox.getSelectedItem());

                if (!response.isOk()) {
                    statusLabel.setForeground(Color.RED);
                    statusLabel.setKey(response.getMessage());
                    pack();
                } else {
                    statusLabel.setKey("");
                    JDialog dialog = new JDialog();
                    currentMiniDialog = dialog;
                    JLabel resultLabel = new JLabel(String.valueOf(response.getNumber()));
                    resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    resultLabel.setVerticalAlignment(SwingConstants.CENTER);
                    dialog.add(resultLabel);
                    dialog.pack();
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                    currentMiniDialog = null;
                }
            } catch (IOException | InterruptedException ex) {
                statusLabel.setForeground(Color.RED);
                statusLabel.setKey("network_error");
            }
        }).start();
    }

    private void openCountByEnginePower() {
        new Thread(() -> {
            try {
                if (currentMiniDialog != null) {
                    return;
                }

                ServerResponseDto response = serverCommander.countByEnginePower((int) enginePowerSpinner.getValue());

                if (!response.isOk()) {
                    statusLabel.setForeground(Color.RED);
                    statusLabel.setKey(response.getMessage());
                    pack();
                } else {
                    statusLabel.setKey("");
                    JDialog dialog = new JDialog();
                    currentMiniDialog = dialog;
                    JLabel resultLabel = new JLabel(String.valueOf(response.getNumber()));
                    resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    resultLabel.setVerticalAlignment(SwingConstants.CENTER);
                    dialog.add(resultLabel);
                    dialog.pack();
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                    currentMiniDialog = null;
                }
            } catch (IOException | InterruptedException ex) {
                statusLabel.setForeground(Color.RED);
                statusLabel.setKey("network_error");
            } catch (Exception e) {
                statusLabel.setForeground(Color.RED);
                statusLabel.setKey("invalid_value");
            }
        }).start();
    }

    private void openGroupByDateDialog() {
        if (currentDialog != null) return;
        currentDialog = new GroupByDateDialog();
        currentDialog.pack();
        currentDialog.setCancelCallback(() -> currentDialog = null);
        new Thread(() -> currentDialog.setVisible(true)).start();
    }

    private void removeLast() {
        new Thread(() -> {
            try {
                ServerResponseDto response = serverCommander.removeLast();

                if (!response.isOk()) {
                    statusLabel.setForeground(Color.RED);
                    statusLabel.setKey(response.getMessage());
                    pack();
                } else {
                    fillTable();
                }
            } catch (IOException | InterruptedException ex) {
                statusLabel.setForeground(Color.RED);
                statusLabel.setKey("network_error");
            }
        }).start();
    }

    private void shuffle() {
        new Thread(() -> {
            try {
                ServerResponseDto response = serverCommander.shuffle();

                if (!response.isOk()) {
                    statusLabel.setForeground(Color.RED);
                    statusLabel.setKey(response.getMessage());
                    pack();
                } else {
                    fillTable();
                }
            } catch (IOException | InterruptedException ex) {
                statusLabel.setForeground(Color.RED);
                statusLabel.setKey("network_error");
            }
        }).start();
    }

    private void executeFile() {
        new Thread(() -> {
                if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    String scriptResult = Commander.scriptMode(fileChooser.getSelectedFile().getAbsolutePath());

                    if (scriptResult.equals("success")) {
                        statusLabel.setForeground(Color.GREEN);
                    } else {
                        statusLabel.setForeground(Color.RED);
                    }
                    statusLabel.setKey(scriptResult);
                    fillTable();
                }
        }).start();
    }

    private void clearCollection() {
        new Thread(() -> {
            try {
                ServerResponseDto response = serverCommander.clear();

                if (!response.isOk()) {
                    statusLabel.setForeground(Color.RED);
                    statusLabel.setKey(response.getMessage());
                    pack();
                } else {
                    fillTable();
                }
            } catch (IOException | InterruptedException ex) {
                statusLabel.setForeground(Color.RED);
                statusLabel.setKey("network_error");
            }
        }).start();
    }

    private void openViewDialog(Vehicle v) {
        if (currentDialog != null) return;
        currentDialog = new VehicleViewDialog(v);
        currentDialog.setOkCallback(() -> {
            currentDialog = null;
            fillTable();
        });
        currentDialog.setCancelCallback(() -> currentDialog = null);
        currentDialog.pack();
        new Thread(() -> currentDialog.setVisible(true)).start();
    }

    private void openAddDialog() {
        if (currentDialog != null) return;
        currentDialog = new AddVehicleDialog();
        currentDialog.pack();
        currentDialog.setOkCallback(() -> {
            currentDialog = null;
            fillTable();
        });
        currentDialog.setCancelCallback(() -> currentDialog = null);
        new Thread(() -> currentDialog.setVisible(true)).start();
    }

    private void fillTable() {
        new Thread(() -> {
            try {
                ServerResponseDto response = serverCommander.show();

                if (!response.isOk()) {
                    statusLabel.setForeground(Color.RED);
                    statusLabel.setKey(response.getMessage());
                    pack();
                } else {
                    this.vehicles = response.getVehicles();
                    vehicleTable.init(vehicles);
                    pack();
                }
            } catch (IOException | InterruptedException ex) {
                statusLabel.setForeground(Color.RED);
                statusLabel.setKey("network_error");
            }
        }).start();
    }

    @Override
    public void notifyAboutEvent(EventDataDto event) {
        if (event.getType() == EventType.UPDATE) {
            fillTable();
        }
    }
}