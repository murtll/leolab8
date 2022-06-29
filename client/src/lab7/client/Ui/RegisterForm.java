package lab7.client.Ui;

import lab7.client.Di.DependencyBag;
import lab7.client.Locale.AbstractLocalizableFrame;
import lab7.client.Locale.LocaleBox;
import lab7.client.Locale.LocalizableButton;
import lab7.client.Locale.LocalizableLabel;
import lab7.client.Network.ClientMetaDto;
import lab7.client.Network.DatagramClient;
import lab7.client.Network.ServerResponseDto;
import lab7.client.Smth.CredentialHandler;
import lab7.client.Smth.ServerCommander;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.IOException;

public class RegisterForm extends AbstractLocalizableFrame {
    private JPanel mainPanel;
    private LocalizableLabel statusLabel;
    private LocalizableLabel usernameLabel;
    private LocalizableLabel passwordLabel;
    private JPasswordField passwordTextField;
    private JTextField usernameTextField;
    private LocaleBox localeBox;
    private LocalizableButton registerButton;
    private JButton backButton;
    private LocalizableLabel colorLabel;
    private LocalizableButton colorButton;

    private AbstractLocalizableFrame fallback;
    private PickColorDialog colorDialog;

    private Color pickedColor = null;

    public RegisterForm() {
        super("register");

        usernameLabel.setKey("username");
        passwordLabel.setKey("password");
        registerButton.setKey("register");
        colorLabel.setKey("color");
        colorButton.setKey("pick");
        localeBox.init();

        setContentPane(mainPanel);
        setMinimumSize(new Dimension(400, 200));
        pack();
        setLocationRelativeTo(null);

        usernameTextField.requestFocus();

        KeyListener enterListener = new EnterListener(this::checkAndProcessInput);
        registerButton.addKeyListener(enterListener);
        usernameTextField.addKeyListener(enterListener);
        passwordTextField.addKeyListener(enterListener);

        KeyListener backEnterListener = new EnterListener(this::getBack);
        backButton.addKeyListener(backEnterListener);

        KeyListener colorEnterListener = new EnterListener(this::pickColor);
        colorButton.addKeyListener(colorEnterListener);

        registerButton.addActionListener(e -> checkAndProcessInput());
        backButton.addActionListener(e -> getBack());
        colorButton.addActionListener(e -> pickColor());
    }

    private void getBack() {
        new Thread(() -> {
            fallback.setVisible(true);
            dispose();
        }).start();
    }

    private void checkAndProcessInput() {
        new Thread(() -> {

            if (usernameTextField.getText().length() == 0 || passwordTextField.getPassword().length == 0 || pickedColor == null) {
                statusLabel.setForeground(Color.RED);
                statusLabel.setKey("fill_out_all_fields");
                return;
            }

            statusLabel.setForeground(Color.BLUE);
            statusLabel.setKey("registration");

            try {
                ServerCommander serverCommander = new ServerCommander(DependencyBag.getSingleton(DatagramClient.class));
                DependencyBag.putSingleton(ServerCommander.class, serverCommander);

                CredentialHandler.setCredentials(new ClientMetaDto(usernameTextField.getText(),
                        new String(passwordTextField.getPassword()), pickedColor.getRGB()));
                ServerResponseDto response = serverCommander.register();

                if (!response.isOk()) {
                    statusLabel.setForeground(Color.RED);
                    statusLabel.setKey(response.getMessage());
                    pack();
                } else {
                    CredentialHandler.setId(response.getId());

                    MainPage mainPage = new MainPage();
                    mainPage.setVisible(true);

                    fallback.dispose();
                    colorDialog.dispose();
                    dispose();
                }
            } catch (IOException | InterruptedException ex) {
                statusLabel.setForeground(Color.RED);
                statusLabel.setKey("network_error");
            }
        }).start();
    }

    private void pickColor() {
        new Thread(() -> {
            if (colorDialog == null) {
                colorDialog = new PickColorDialog();
            } else if (colorDialog.isVisible()) {
                return;
            }

            colorDialog.pack();
            colorDialog.setOnPick((color) -> {
                colorButton.setBackground(color);
                pickedColor = color;
            });
            colorDialog.setVisible(true);
        }).start();
    }

    public void setFallback(AbstractLocalizableFrame fallback) {
        this.fallback = fallback;
    }
}
