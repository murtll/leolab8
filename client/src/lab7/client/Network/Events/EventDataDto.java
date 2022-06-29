package lab7.client.Network.Events;

public class EventDataDto {
    private EventType type;

    public EventDataDto() {}

    public EventDataDto(EventType type) {
        this.type = type;
    }

    public EventType getType() {
        return type;
    }
}
