package lab7.server.Commands;

import lab7.server.Network.ServerResponseDto;
import lab7.server.Smth.CollectionManager;

import java.time.LocalDate;
import java.util.Map;


/**
 * The class of the command for grouping a collection element by the value of the element's creationdate field
 *
 * @author Nagorny Leonid
 */

public class Group_counting_by_creation_date extends AbstrCommand {
    private final CollectionManager cm;

    public Group_counting_by_creation_date(CollectionManager cm) {
        this.cm = cm;
    }

    @Override
    public ServerResponseDto execute() {
        Map<LocalDate, Long> result = cm.group_counting_by_creation_date();
        return new ServerResponseDto(true, result);
    }
}
