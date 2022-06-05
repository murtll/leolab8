package lab6.server.Network;

import java.net.InetAddress;

public class ClientRequestDto {
    private final InetAddress ip;
    private final int port;
    private final ClientDataDto data;

    public ClientRequestDto() {
        this.ip = null;
        this.port = 0;
        this.data = null;
    }

    public ClientRequestDto(InetAddress ip, int port, ClientDataDto data) {
        this.ip = ip;
        this.port = port;
        this.data = data;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public ClientDataDto getData() {
        return data;
    }
}
