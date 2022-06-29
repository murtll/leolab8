package lab7.client.Smth;

import lab7.client.Di.DependencyBag;
import lab7.client.Entity.FuelType;
import lab7.client.Entity.Vehicle;
import lab7.client.Network.ServerResponseDto;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Vector;

/**
 * a class that provides access to the commands
 *
 * @author Nagorny Leonid
 */

public class CommandManager {

    private static final ServerCommander serverCommander = DependencyBag.getSingleton(ServerCommander.class);

    private static InputChecker ic;

    public static void setIc(InputChecker ic) {
        CommandManager.ic = ic;
    }

    public static String help() {
        try {
            ServerResponseDto result = serverCommander.help();
            if (result.isOk()) {
                return result.getMessage();
            } else {
                return resolveErrors(result);
            }
        } catch (IOException e) {
            return "Ошибка передачи данных";
        } catch (Exception e) {
            return "Ошибка";
        }
    }

    public static String login() {
        try {
            ServerResponseDto result = serverCommander.login();
            if (result.isOk()) {
                return result.getMessage();
            } else {
                return resolveErrors(result);
            }
        } catch (IOException e) {
            return "Ошибка передачи данных";
        } catch (Exception e) {
            return "Ошибка";
        }
    }

    public static String register() {
        try {
            ServerResponseDto result = serverCommander.register();
            if (result.isOk()) {
                return result.getMessage();
            } else {
                return resolveErrors(result);
            }
        } catch (IOException e) {
            return "Ошибка передачи данных";
        } catch (Exception e) {
            return "Ошибка";
        }
    }

    public static String info() {
        try {
            ServerResponseDto result = serverCommander.info();
            if (result.isOk()) {
                return result.getMessage();
            } else {
                return resolveErrors(result);
            }
        } catch (IOException e) {
            return "Ошибка передачи данных";
        } catch (Exception e) {
            return "Ошибка";
        }
    }

    public static String show() {
        try {
            ServerResponseDto result = serverCommander.show();
            return collectVehiclesToString(result);
        } catch (IOException e) {
            return "Ошибка передачи данных";
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка";
        }
    }

    public static String add() {
        Vehicle v = VehicleAsk.createVehicle();

        try {
            ServerResponseDto result = serverCommander.add(v);
            return result.getMessage();
        } catch (IOException e) {
            return "network_error";
        } catch (Exception e) {
            return "error";
        }
    }

    public static String updateId(String s) {
        long id = Long.parseLong(s);
        Vehicle v = VehicleAsk.createVehicle();

        try {
            ServerResponseDto result = serverCommander.updateId(id, v);
            return result.getMessage();
        } catch (IOException e) {
            return "network_error";
        } catch (Exception e) {
            return "error";
        }
    }

    public static String removeById(String s) {
        long id = Long.parseLong(s);

        try {
            ServerResponseDto result = serverCommander.removeById(id);
            return result.getMessage();
        } catch (IOException e) {
            return "network_error";
        } catch (Exception e) {
            return "error";
        }
    }

    public static String clear() {
        try {
            ServerResponseDto result = serverCommander.clear();
            return result.getMessage();
        } catch (IOException e) {
            return "network_error";
        } catch (Exception e) {
            return "error";
        }
    }

    public static String removeLast() {
        try {
            ServerResponseDto result = serverCommander.removeLast();
            return result.getMessage();
        } catch (IOException e) {
            return "network_error";
        } catch (Exception e) {
            return "error";
        }
    }

    public static String shuffle() {
        try {
            ServerResponseDto result = serverCommander.shuffle();
            return result.getMessage();
        } catch (IOException e) {
            return "network_error";
        } catch (Exception e) {
            return "error";
        }
    }

    public static String groupCountingByCreationDate() {
        try {
            ServerResponseDto result = serverCommander.groupCountingByCreationDate();
            if (result.isOk()) {
                Map<LocalDate, Long> groups = result.getGroups();

                StringBuilder sb = new StringBuilder("Группы:\n");
                for (Map.Entry<LocalDate, Long> g: groups.entrySet()) {
                    sb.append("Дата: ").append(g.getKey()).append(", ");
                    sb.append("Количество транспорта: ").append(g.getValue()).append('\n');
                }

                return sb.toString();
            } else {
                return resolveErrors(result);
            }
        } catch (IOException e) {
            return "Ошибка передачи данных";
        } catch (Exception e) {
            return "Ошибка";
        }
    }

    public static String countByEnginePower(String s) {
        if (!ic.integerValidCheck(s, 0, Integer.MAX_VALUE)) {
            return "Invalid power";
        }
        int power = Integer.parseInt(s);

        try {
            ServerResponseDto result = serverCommander.countByEnginePower(power);
            if (result.isOk()) {
                return "Количество транспорта с мощностью двигателя " + power + ": " + result.getNumber() + "\n";
            } else {
                return resolveErrors(result);
            }
        } catch (IOException e) {
            return "Ошибка передачи данных";
        } catch (Exception e) {
            return "Ошибка";
        }
    }

    public static String countLessThanFuelType(String s) {
        FuelType fuelType = FuelType.getObjByStr(s);

        try {
            ServerResponseDto result = serverCommander.countLessThanFuelType(fuelType);
            if (result.isOk()) {
                return "Количество транспорта с типом топлива меньше чем " + fuelType + ": " + result.getNumber() + "\n";
            } else {
                return resolveErrors(result);
            }
        } catch (IOException e) {
            return "Ошибка передачи данных";
        } catch (Exception e) {
            return "Ошибка";
        }
    }

    private static String resolveErrors(ServerResponseDto dto) {
        if (dto.getMessage() != null) {
            return "Ошибка: " + dto.getMessage();
        } else  {
            return "Ошибка";
        }
    }

    public static String showMy() {
        try {
            ServerResponseDto result = serverCommander.showMy();
            return collectVehiclesToString(result);
        } catch (IOException e) {
            return "Ошибка передачи данных";
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка";
        }
    }

    private static String collectVehiclesToString(ServerResponseDto result) {
        if (result.isOk()) {
            Vector<Vehicle> vehicles = result.getVehicles();

            if (vehicles == null) {
                return "Пусто";
            }

            StringBuilder sb = new StringBuilder();
            for (Vehicle v: vehicles) {
                sb.append(v.toString()).append('\n');
            }

            return sb.toString();
        } else {
            return resolveErrors(result);
        }
    }
}
