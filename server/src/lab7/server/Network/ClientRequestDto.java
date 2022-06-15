package lab7.server.Network;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.net.InetAddress;

public class ClientRequestDto {
    @JsonIgnore
    private InetAddress ip;
    @JsonIgnore
    private int port;
    private final ClientDataDto data;

    private final ClientMetaDto meta;

    public ClientRequestDto() {
        this.meta = null;
        this.data = null;
    }

    public ClientRequestDto(ClientDataDto data, ClientMetaDto meta) {
        this.data = data;
        this.meta = meta;
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

    public ClientMetaDto getMeta() {
        return meta;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
