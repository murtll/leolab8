package lab7.server.Network;

public class ClientMetaDto {
    private final String username;
    private final String password;
    private int color;
    private long id;

    public ClientMetaDto() {
        this.username = null;
        this.password = null;
    }

    public ClientMetaDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public ClientMetaDto(int color, long id) {
        this.username = null;
        this.password = null;
        this.color = color;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getColor() {
        return color;
    }

    public long getId() {
        return id;
    }
}
