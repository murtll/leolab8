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
import lab7.client.Smth.ServerCommander;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class AddVehicleDialog extends AbstractLocalizableDialog {
    private JPanel contentPane;
    private LocalizableButton buttonOK;
    private LocalizableButton buttonCancel;
    private JTextField nameTextField;
    private JComboBox<VehicleType> vehicleTypeBox;
    private JComboBox<FuelType> fuelTypeBox;
    private LocalizableLabel nameLabel;
    private LocalizableLabel coordinatesLabel;
    private LocalizableLabel enginePower;
    private LocalizableLabel capacityLabel;
    private LocalizableLabel vehicleTypeLabel;
    private LocalizableLabel fuelTypeLabel;
    private LocalizableLabel statusLabel;
    private JSpinner enginePowerSpinner;
    private JSpinner capacitySpinner;
    private JSpinner coordinatesYSpinner;
    private JSpinner coordinatesXSpinner;
    private LocaleBox localeBox;

    private Runnable onOk;
    private Runnable onCancel;

    public AddVehicleDialog() {
        super("add");
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

        for (VehicleType t : VehicleType.values()) {
            vehicleTypeBox.addItem(t);
        }

        for (FuelType t : FuelType.values()) {
            fuelTypeBox.addItem(t);
        }

        capacitySpinner.setModel(new SpinnerNumberModel(0.1, 0.1, 100.0, 0.1));
        coordinatesXSpinner.setModel(new SpinnerNumberModel(1, 1, 100, 1));
        coordinatesYSpinner.setModel(new SpinnerNumberModel(1, 1, 100, 1));
        enginePowerSpinner.setModel(new SpinnerNumberModel(1, 1, 100, 1));

        localeBox.init();

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        pack();
        setLocationRelativeTo(null);
    }

    private void onOK() {
        new Thread(() -> {
            try {
                if (nameTextField.getText().equals("") ||
                        vehicleTypeBox.getSelectedItem() == null ||
                        fuelTypeBox.getSelectedItem() == null) {
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

                ServerResponseDto response = serverCommander.add(vehicle);

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
