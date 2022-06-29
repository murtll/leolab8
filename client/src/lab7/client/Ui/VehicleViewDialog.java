package lab7.client.Ui;

import lab7.client.Di.DependencyBag;
import lab7.client.Entity.Coordinates;
import lab7.client.Entity.FuelType;
import lab7.client.Entity.Vehicle;
import lab7.client.Entity.VehicleType;
import lab7.client.Locale.AbstractLocalizableDialog;
import lab7.client.Locale.LocaleBox;
import lab7.client.Locale.LocalizableButton;
import lab7.client.Locale.LocalizableLabel;
import lab7.client.Network.ServerResponseDto;
import lab7.client.Smth.CredentialHandler;
import lab7.client.Smth.ServerCommander;
import lab7.client.Smth.VehicleAsk;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class VehicleViewDialog extends AbstractLocalizableDialog {
    private JPanel contentPane;
    private LocalizableLabel nameLabel;
    private JTextField nameTextField;
    private LocalizableLabel coordinatesLabel;
    private LocalizableLabel enginePower;
    private LocalizableLabel capacityLabel;
    private LocalizableLabel vehicleTypeLabel;
    private JComboBox<VehicleType> vehicleTypeBox;
    private LocalizableLabel fuelTypeLabel;
    private JComboBox<FuelType> fuelTypeBox;
    private JSpinner enginePowerSpinner;
    private JSpinner capacitySpinner;
    private JSpinner coordinatesYSpinner;
    private JSpinner coordinatesXSpinner;
    private LocalizableLabel statusLabel;
    private LocaleBox localeBox;
    private LocalizableButton buttonOK;
    private LocalizableButton buttonCancel;
    private JPanel drawingPanel;
    private LocalizableButton deleteButton;

    private Runnable onOk;
    private Runnable onCancel;
    private final Vehicle vehicle;

    public VehicleViewDialog(Vehicle vehicle) {
        super("vehicle");
        this.vehicle = vehicle;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonCancel.setKey("cancel");
        buttonOK.setKey("ok");

        nameLabel.setKey("name");
        coordinatesLabel.setKey("coordinates");
        enginePower.setKey("engine_power");
        capacityLabel.setKey("capacity");
        vehicleTypeLabel.setKey("vehicle_type");
        fuelTypeLabel.setKey("fuel_type");

        deleteButton.setKey("delete");

        localeBox.init();

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        nameTextField.setText(vehicle.getName());

        for (VehicleType t : VehicleType.values()) {
            vehicleTypeBox.addItem(t);
        }
        vehicleTypeBox.setSelectedItem(vehicle.getVehicleType());

        for (FuelType t : FuelType.values()) {
            fuelTypeBox.addItem(t);
        }
        fuelTypeBox.setSelectedItem(vehicle.getFuelType());

        capacitySpinner.setModel(new SpinnerNumberModel(0.0, 0.0, 100.0, 0.1));
        capacitySpinner.setValue(vehicle.getCapacity());

        coordinatesXSpinner.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        coordinatesXSpinner.setValue(vehicle.getCoordinates().getX());

        coordinatesYSpinner.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        coordinatesYSpinner.setValue(vehicle.getCoordinates().getY());

        enginePowerSpinner.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        enginePowerSpinner.setValue(vehicle.getEnginePower());


        if (vehicle.getUserId() != CredentialHandler.getCredentials().getId()) {
            nameTextField.setEnabled(false);
            vehicleTypeBox.setEnabled(false);
            fuelTypeBox.setEnabled(false);
            enginePowerSpinner.setEnabled(false);
            capacitySpinner.setEnabled(false);
            coordinatesXSpinner.setEnabled(false);
            coordinatesYSpinner.setEnabled(false);
            deleteButton.setEnabled(false);
            deleteButton.setVisible(false);
        } else {
            deleteButton.addActionListener(e -> new Thread(() -> {
                try {
                    statusLabel.setKey("loading");

                    ServerCommander serverCommander = DependencyBag.getSingleton(ServerCommander.class);

                    ServerResponseDto response = serverCommander.removeById(this.vehicle.getId());

                    if (!response.isOk()) {
                        statusLabel.setKey(response.getMessage());
                        pack();
                    } else {
                        dispose();
                        onOk.run();
                    }
                } catch (IOException | InterruptedException ex) {
                    statusLabel.setKey("error");
                    pack();
                }
            }).start());
        }

        drawingPanel.setLayout(new GridLayout());

        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void setVisible(boolean b) {
        if (b) {
            playAnimation();
        }
        super.setVisible(b);
    }

    public void playAnimation() {
        new Thread(() -> {
            while (!this.isVisible()) {}

            while (this.isVisible()) {
                int size;
                try {
                    size = (int) (float) capacitySpinner.getValue();
                } catch (Exception e) {
                    size = vehicle.getCapacity().intValue();
                }

                try {
                    drawVehicle(vehicle, size);
                    Thread.sleep(500);
                    drawVehicle(vehicle, size + 5);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.err.println("Thread was interrupted");
                }
            }
        }).start();
    }

    private void drawVehicle(Vehicle vehicle, int size) {
        JLabel drawingLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                try {
                    Image mapImage = ImageIO.read(new File("resources/map.png"));
                    g.drawImage(mapImage, 0, 0, this);

                    int coordinateX;
                    int coordinateY;
                    try {
                        coordinateX = (int) coordinatesXSpinner.getValue();
                        coordinateY = (int) coordinatesYSpinner.getValue();
                    } catch (Exception e) {
                        coordinateX = vehicle.getCoordinates().getX();
                        coordinateY = vehicle.getCoordinates().getY();
                    }

                    VehicleType type;
                    if (vehicleTypeBox.getSelectedItem() != null) {
                        type = (VehicleType) vehicleTypeBox.getSelectedItem();
                    } else {
                        type = vehicle.getVehicleType();
                    }

                    switch (type) {
                        case HELICOPTER:
                            g.setColor(new Color(vehicle.getUserColor()));
                            g.fillOval(coordinateX * (mapImage.getWidth(this) / 100) - (size / 2),
                                    coordinateY * (mapImage.getHeight(this) / 100) - (size / 2),
                                    size, size);

                            g.setColor(new Color(vehicle.getUserColor()).brighter());
                            g.drawOval(coordinateX * (mapImage.getWidth(this) / 100) - (size / 2),
                                    coordinateY * (mapImage.getHeight(this) / 100) - (size / 2),
                                    size, size);
                            break;
                        case SUBMARINE:
                            g.setColor(new Color(vehicle.getUserColor()));
                            g.fillOval(coordinateX * (mapImage.getWidth(this) / 100) - (size / 2),
                                    coordinateY * (mapImage.getHeight(this) / 100) - (size),
                                    size, size * 2);

                            g.setColor(new Color(vehicle.getUserColor()).brighter());
                            g.drawOval(coordinateX * (mapImage.getWidth(this) / 100) - (size / 2),
                                    coordinateY * (mapImage.getHeight(this) / 100) - (size),
                                    size, size * 2);
                            break;
                        case MOTORCYCLE:
                            g.setColor(new Color(vehicle.getUserColor()));
                            g.fillRect(coordinateX * (mapImage.getWidth(this) / 100) - (size / 2),
                                    coordinateY * (mapImage.getHeight(this) / 100) - (size),
                                    size, size * 2);

                            g.setColor(new Color(vehicle.getUserColor()));
                            g.drawRect(coordinateX * (mapImage.getWidth(this) / 100) - (size / 2),
                                    coordinateY * (mapImage.getHeight(this) / 100) - (size),
                                    size, size * 2);
                            break;
                    }
                } catch (IOException e) {
                    System.err.println("No map image was found");
                }
                super.paintComponent(g);
            }
        };
        drawingPanel.removeAll();
        drawingPanel.add(drawingLabel);
        drawingPanel.setMinimumSize(new Dimension(300, 300));
        drawingPanel.repaint();
        pack();
    }

    private void onOK() {
        new Thread(() -> {
            try {
                if (nameTextField.getText().equals("")) {
                    statusLabel.setKey("fill_out_all_fields");
                    return;
                }

                statusLabel.setKey("loading");

                ServerCommander serverCommander = DependencyBag.getSingleton(ServerCommander.class);

                Vehicle vehicle = new Vehicle(
                        nameTextField.getText(),
                        new Coordinates(
                                (int) coordinatesXSpinner.getValue(),
                                (int) coordinatesYSpinner.getValue()
                        ),
                        (int) enginePowerSpinner.getValue(),
                        (float) (int) enginePowerSpinner.getValue(),
                        (VehicleType) vehicleTypeBox.getSelectedItem(),
                        (FuelType) fuelTypeBox.getSelectedItem());

                ServerResponseDto response = serverCommander.updateId(this.vehicle.getId(), vehicle);

                if (!response.isOk()) {
                    statusLabel.setKey(response.getMessage());
                    pack();
                } else {
                    dispose();
                    onOk.run();
                }
            } catch (IOException | InterruptedException ex) {
                statusLabel.setKey("error");
                pack();
            }
        }).start();
    }

    private void onCancel() {
        dispose();
        onCancel.run();
    }

    @Override
    public void setOkCallback(Runnable r) {
        this.onOk = r;
    }

    @Override
    public void setCancelCallback(Runnable r) {
        this.onCancel = r;
    }
}
