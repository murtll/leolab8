package lab6.client.Smth;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab6.client.Network.ServerResponseDto;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Vector;
import java.util.function.Consumer;

/**
 * a class that provides access to the commands
 *
 * @author Nagorny Leonid
 */

public class CommandManager {

    private static ServerCommander serverCommander;

    private static VehicleAsk vehicleAsk;

    private static InputChecker ic;

    public static void setIc(InputChecker ic) {
        CommandManager.ic = ic;
    }

    public static void setServerCommander(ServerCommander commander) {
        CommandManager.serverCommander = commander;
    }

    public static void setVehicleAsk(VehicleAsk vehicleAsk) {
        CommandManager.vehicleAsk = vehicleAsk;
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
        } catch (IOException e) {
            return "Ошибка передачи данных";
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка";
        }
    }

    public static String add() {
        Vehicle v = vehicleAsk.createVehicle();

        try {
            ServerResponseDto result = serverCommander.add(v);
            if (result.isOk()) {
                return result.getMessage();
            } else {
                return resolveErrors(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Ошибка передачи данных";
        } catch (Exception e) {
            return "Ошибка";
        }
    }

    public static String updateId(String s) {
        if (!ic.longValidCheck(s, 0L, Long.MAX_VALUE)) {
            return "The inserting ID is not in valid range! Please insert Id greater than 0!";
        }

        long id = Long.parseLong(s);
        Vehicle v = vehicleAsk.createVehicle();

        try {
            ServerResponseDto result = serverCommander.updateId(id, v);
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

    public static String removeById(String s) {
        if (!ic.longValidCheck(s, 0L, Long.MAX_VALUE)) {
            return "The inserting ID is not in valid range! Please insert Id greater than 0!";
        }

        long id = Long.parseLong(s);

        try {
            ServerResponseDto result = serverCommander.removeById(id);
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

    public static String clear() {
        try {
            ServerResponseDto result = serverCommander.clear();
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

    public static String removeLast() {
        try {
            ServerResponseDto result = serverCommander.removeLast();
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

    public static String shuffle() {
        try {
            ServerResponseDto result = serverCommander.shuffle();
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
}
