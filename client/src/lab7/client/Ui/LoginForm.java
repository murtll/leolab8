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

public class LoginForm extends AbstractLocalizableFrame {
    private JPanel mainPanel;
    private JPasswordField passwordTextField;
    private JTextField usernameTextField;
    private LocalizableButton loginButton;
    private LocalizableLabel statusLabel;
    private LocalizableLabel usernameLabel;
    private LocalizableLabel passwordLabel;
    private LocaleBox localeBox;
    private LocalizableButton registerButton;

    public LoginForm() {
        super("login");

        usernameLabel.setKey("username");
        passwordLabel.setKey("password");
        loginButton.setKey("login");
        registerButton.setKey("register");
        localeBox.init();

        setContentPane(mainPanel);
        setMinimumSize(new Dimension(400, 200));
        pack();
        setLocationRelativeTo(null);

        usernameTextField.requestFocus();

        KeyListener enterListener = new EnterListener(this::checkAndProcessInput);
        loginButton.addKeyListener(enterListener);
        usernameTextField.addKeyListener(enterListener);
        passwordTextField.addKeyListener(enterListener);

        KeyListener registerEnterListener = new EnterListener(this::register);
        registerButton.addKeyListener(registerEnterListener);

        loginButton.addActionListener(e -> checkAndProcessInput());

        registerButton.addActionListener(e -> register());
    }

    private void register() {
        new Thread(() -> {
            RegisterForm registerForm = new RegisterForm();
            registerForm.setFallback(this);
            registerForm.setVisible(true);

            setVisible(false);
        }).start();
    }

    private void checkAndProcessInput() {
        new Thread(() -> {

            if (usernameTextField.getText().length() == 0 || passwordTextField.getPassword().length == 0) {
                statusLabel.setForeground(Color.RED);
                statusLabel.setKey("fill_out_all_fields");
                return;
            }

            statusLabel.setForeground(Color.BLUE);
            statusLabel.setKey("logging_in");

            try {
                ServerCommander serverCommander = new ServerCommander(DependencyBag.getSingleton(DatagramClient.class));
                DependencyBag.putSingleton(ServerCommander.class, serverCommander);

                CredentialHandler.setCredentials(new ClientMetaDto(usernameTextField.getText(), new String(passwordTextField.getPassword())));
                ServerResponseDto response = serverCommander.login();

                if (!response.isOk()) {
                    statusLabel.setForeground(Color.RED);
                    statusLabel.setKey(response.getMessage());
                    pack();
                } else {
                    CredentialHandler.setColor(response.getColor());
                    CredentialHandler.setId(response.getId());

                    MainPage mainPage = new MainPage();
                    mainPage.setVisible(true);

                    dispose();
                }
            } catch (IOException | InterruptedException ex) {
                statusLabel.setForeground(Color.RED);
                statusLabel.setKey("network_error");
            }
        }).start();
    }
}
