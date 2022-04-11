package lab5.Commands;

import lab5.Smth.AbstrCommand;
import lab5.Smth.CollectionManager;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Script command class
 *
 * @author Nagorny Leonid
 */

public class Execute extends AbstrCommand {
    private final CollectionManager cm;

    public Execute(CollectionManager cm) {
        this.cm = cm;
    }

    public boolean execute(String arg) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(arg))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if ((line.split(" ")[0].equals("srcexecute_script"))) {
                    System.out.println(line);
                } else {
                    System.out.println("Неверная команда");
                }
            }
        } catch (IOException e) {
            System.out.println("Файл не найден,пожалуйста введите существующий файл");
        }
        return true;
    }
}
