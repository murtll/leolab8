package lab6.client;

import lab6.client.Network.DatagramClient;
import lab6.client.Smth.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Implementation of the program
 *
 * @author Nagorny Leonid
 */

public class Main {

    public static void main(String[] args) {
        try {
            Scanner scan = new Scanner(System.in);

            InetAddress host;
            while (true) {
                try {
                    System.out.print("Адрес сервера (default localhost): ");
                    String inputHost = scan.nextLine();
                    if (inputHost.length() == 0) inputHost = "localhost";

                    host = InetAddress.getByName(inputHost);
                    break;
                } catch (UnknownHostException e) {
                    System.out.println("Неверный адрес. Невозможно определить хост.");
                }
            }

            int port;
            while (true) {
                try {
                    System.out.print("Порт сервера (default 8080): ");
                    String inputPort = scan.nextLine();
                    if (inputPort.length() == 0) inputPort = "8080";

                    port = Integer.parseInt(inputPort);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат порта.");
                }
            }

            System.out.println("Добро пожаловать!");
            System.out.println("Введите help для того чтобы начать");

            DatagramClient client = new DatagramClient(host, port);

            ServerCommander serverCommander = new ServerCommander(client);
            InputChecker inputChecker = new InputChecker();
            VehicleAsk vehicleAsk = new VehicleAsk();
            VehicleAsk.setScanner(scan);

            CommandManager.setServerCommander(serverCommander);
            CommandManager.setIc(inputChecker);
            CommandManager.setVehicleAsk(vehicleAsk);

            Commander.setUserScanner(scan);
            Commander.interactiveMode();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}