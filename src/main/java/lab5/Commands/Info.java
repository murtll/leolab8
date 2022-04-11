package lab5.Commands;

import lab5.Smth.AbstrCommand;
import lab5.Smth.CollectionManager;

/**
 * Command class info
 *
 * @author Nagorny Leonid
 */

public class Info extends AbstrCommand {
    private final CollectionManager collectionManager;

    public Info(CollectionManager c) {
        this.collectionManager = c;
    }


    @Override
    public boolean execute(String argument) {
        return false;
    }

    public boolean execute() {
        System.out.println("Коллекция типа : Vector");
        System.out.println("Дата создания: " + collectionManager.getCreationDate());
        System.out.println("Количество элементов: " + collectionManager.size());
        return true;
    }
}
