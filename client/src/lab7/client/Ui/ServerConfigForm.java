package lab7.client.Ui;

import lab7.client.Di.DependencyBag;
import lab7.client.Locale.*;
import lab7.client.Network.DatagramClient;
import lab7.client.Network.Events.EventListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerConfigForm extends AbstractLocalizableFrame {

    private JTextField hostTextField;
    private JTextField portTextField;
    private LocalizableButton nextButton;
    private LocalizableLabel statusLabel;
    private JPanel mainPanel;
    private LocalizableLabel hostLabel;
    private LocalizableLabel portLabel;
    private LocaleBox localeBox;

    public ServerConfigForm() {
        super("server_config");
        setContentPane(mainPanel);

        hostLabel.setKey("host");
        portLabel.setKey("port");
        nextButton.setKey("next");
        localeBox.init();

        setMinimumSize(new Dimension(400, 200));
        pack();
        setLocationRelativeTo(null);
        hostTextField.requestFocus();

        KeyListener enterListener = new EnterListener(this::checkAndProcessInput);
        nextButton.addKeyListener(enterListener);
        hostTextField.addKeyListener(enterListener);
        portTextField.addKeyListener(enterListener);

        nextButton.addActionListener((e) -> checkAndProcessInput());
    }

    private void checkAndProcessInput() {
        new Thread(() -> {
            try {
                InetAddress ip = InetAddress.getByName(hostTextField.getText());
                int port = Integer.parseInt(portTextField.getText());
                DependencyBag.putSingleton(DatagramClient.class, new DatagramClient(ip, port));
                DependencyBag.putSingleton(EventListener.class, new EventListener(ip, port));

                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);

                dispose();
            } catch (UnknownHostException ex) {
                statusLabel.setForeground(Color.RED);
                statusLabel.setKey("unknown_host");
                pack();
            } catch (NumberFormatException ex) {
                statusLabel.setForeground(Color.RED);
                statusLabel.setKey("invalid_port");
                pack();
            } catch (IOException ex) {
                statusLabel.setForeground(Color.RED);
                statusLabel.setKey("network_error");
                pack();
            }
        }).start();
    }
}
