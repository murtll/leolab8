package lab7.client.Ui;

import lab7.client.Di.DependencyBag;
import lab7.client.Locale.AbstractLocalizableDialog;
import lab7.client.Locale.LocalizableLabel;
import lab7.client.Network.ServerResponseDto;
import lab7.client.Smth.ServerCommander;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class GroupByDateDialog extends AbstractLocalizableDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private GroupByDateTable dateGroupTable;
    private LocalizableLabel statusLabel;

    private final ServerCommander serverCommander = DependencyBag.getSingleton(ServerCommander.class);

    private Runnable onCancel;

    public GroupByDateDialog() {
        super("group_by_date");
        setContentPane(contentPane);
        setModal(true);

        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        new Thread(() -> {
            try {
                ServerResponseDto response = serverCommander.groupCountingByCreationDate();

                if (!response.isOk()) {
                    statusLabel.setForeground(Color.RED);
                    statusLabel.setKey(response.getMessage());
                    pack();
                } else {
                    System.out.println(response.getGroups());
                    dateGroupTable.init(response.getGroups());
                    pack();
                }
            } catch (IOException | InterruptedException ex) {
                statusLabel.setForeground(Color.RED);
                statusLabel.setKey("network_error");
            }
        }).start();
        pack();
        setLocationRelativeTo(null);
    }

    private void onCancel() {
        dispose();
        onCancel.run();
    }

    @Override
    public void setOkCallback(Runnable r) {}

    @Override
    public void setCancelCallback(Runnable r) {
        this.onCancel = r;
    }
}
