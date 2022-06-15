package lab7.client.Network;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.net.InetAddress;

public class ClientRequestDto {
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

    public ClientDataDto getData() {
        return data;
    }

    public ClientMetaDto getMeta() {
        return meta;
    }
}
