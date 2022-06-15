package lab7.client.Smth;

import lab7.client.Network.ClientMetaDto;

import java.io.Console;
import java.util.Scanner;

public class CredentialAsk {
    private static Scanner scanner;

    public static void setScanner(Scanner scanner) {
        CredentialAsk.scanner = scanner;
    }

    public static ClientMetaDto askMeta() {
        System.out.print("Имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Пароль: ");

        String password;

        Console console = System.console();
        if (console != null) {
            char[] passChars = console.readPassword();
            password = new String(passChars);
        } else {
            password = scanner.nextLine();
        }

        return new ClientMetaDto(username, password);
    }
}
