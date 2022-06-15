package lab7.client.Smth;

import lab7.client.Exceptions.WrongFormat;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * the class responsible for generating the vehicle class
 *
 * @author Nagorny Leonid
 */

public class VehicleAsk {
    private static Scanner scanner;

    public static void setScanner(Scanner scanner) {
        VehicleAsk.scanner = scanner;
    }

    public Vehicle createVehicle() {
        String name = null;
        while (name == null) {
            try {
                System.out.println("имя транспорта : ");
                String nm = scanner.nextLine().trim();
                if (nm.isEmpty()) throw new EmptyIO();
                if (nm.contains(";")) throw new WrongFormat();
                name = nm;
            } catch (EmptyIO e) {
                System.out.println("Это значение не может быть пустым");

            } catch (NoSuchElementException exception) {
                System.out.println("Введеное плохо понятно");
            } catch (WrongFormat wrongFormat) {
                System.out.println("Не используйте \";\"");
            }
        }
        int x;
        while (true) {
            try {
                System.out.println("Напишите координату X");
                String st = scanner.nextLine().trim();
                if (st.isEmpty()) throw new EmptyIO();
                x = Integer.parseInt(st);
                break;
            } catch (EmptyIO e) {
                System.out.println("Не может быть пустым");
            } catch (NumberFormatException exception) {
                System.out.println("Должен быть в требуемом формате");
            } catch (NoSuchElementException exception) {
                System.out.println("Нет такого элемента!");
            }
        }
        int y;
        while (true) {
            try {
                System.out.println("Напишите координату  Y: ");
                String st = scanner.nextLine().trim();
                if (st.isEmpty()) throw new EmptyIO();
                y = Integer.parseInt(st);
                break;
            } catch (EmptyIO e) {
                System.out.println("Не может быть пустым");
            } catch (NumberFormatException exception) {
                System.out.println("Должен быть в требуемом формате!");
            } catch (NoSuchElementException exception) {
                System.out.println("Нет такого элемента!");

            }
        }
        int enginePower;
        while (true) {
            try {
                System.out.println("Введите мощность двигателя");
                String st = scanner.nextLine().trim();
                if (st.isEmpty()) throw new EmptyIO();
                enginePower = Integer.parseInt(st);
                if (enginePower < 0 || enginePower > 191928932) throw new WrongFormat();
                break;
            } catch (EmptyIO e) {
                System.out.println("Не может быть пустого значения");
            } catch (WrongFormat e) {
                System.out.println("Неправильный формат!Введенное должно быть целым числом больше 0 либо меньше 191928932");
            } catch (NumberFormatException exception) {
                System.out.println("Пожалуйста напишите число в верном формате");
            } catch (NoSuchElementException exception) {
                System.out.println("Нет такого элемента");
            }
        }
        float capacity;
        while (true) {
            try {
                System.out.println("Введите емкость двигателя");
                String nm = scanner.nextLine().trim();
                capacity = Float.parseFloat(nm);
                if (capacity <= 0) throw new WrongFormat();
                break;
            } catch (WrongFormat e) {
                System.out.println("Неправильный формат!Введенное должно быть числом типа float ,быть больше нуля либо равняться null");
            } catch (NumberFormatException exception) {
                System.out.println("Пожалуйста напишите число в верном формате");
            } catch (NoSuchElementException exception) {
                System.out.println("Нет такого элемента");

            }
        }
        VehicleType vehicleType;
        while (true) {
            try {
                System.out.println("Введите тип транспорта");
                System.out.println(java.util.Arrays.asList(VehicleType.values()));
                String st = scanner.nextLine().trim();
                if (st.equals("")) throw new EmptyIO();
                vehicleType = VehicleType.valueOf(st);
                break;
            } catch (EmptyIO e) {
                System.out.println("Не может быть пустым");
            } catch (NoSuchElementException exception) {
                System.out.println("Нет такого элемента");
            } catch (IllegalArgumentException exception) {
                System.out.println("Нет такого в списке");
            }
        }
        FuelType fuelType;
        while (true) {
            try {
                System.out.println("Введите тип топлива");
                System.out.println(java.util.Arrays.asList(FuelType.values()));
                String st = scanner.nextLine().trim();
                if (st.equals("")) throw new EmptyIO();
                fuelType = FuelType.valueOf(st);
                break;
            } catch (EmptyIO e) {
                System.out.println("Не может быть пустым");
            } catch (NoSuchElementException exception) {
                System.out.println("Нет такого элемента");
            } catch (IllegalArgumentException exception) {
                System.out.println("Нет такого в списке");
            }
        }
        return new Vehicle(name,
                new Coordinates(x, y), enginePower,
                capacity, vehicleType, fuelType);
    }
}
