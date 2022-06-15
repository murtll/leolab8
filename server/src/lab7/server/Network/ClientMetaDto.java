package lab7.server.Network;

public class ClientMetaDto {
    private final String username;
    private final String password;

    public ClientMetaDto() {
        this.username = null;
        this.password = null;
    }

    public ClientMetaDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
