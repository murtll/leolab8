package lab7.server.Network.Events;

public class EventDataDto {
    private final EventType type;

    public EventDataDto(EventType type) {
        this.type = type;
    }

    public EventType getType() {
        return type;
    }
}
