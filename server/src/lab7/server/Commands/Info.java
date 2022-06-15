package lab7.server.Commands;

import lab7.server.Network.ServerResponseDto;
import lab7.server.Smth.AbstrCommand;
import lab7.server.Smth.CollectionManager;

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

    public ServerResponseDto execute() {
        String info = "Коллекция типа : Vector \n" +
        "Дата создания: " + collectionManager.getCreationDate() + "\n" +
        "Количество элементов: " + collectionManager.size();

        return new ServerResponseDto(true, info);
    }
}
