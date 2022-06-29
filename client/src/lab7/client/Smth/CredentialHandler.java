package lab7.client.Smth;

import lab7.client.Network.ClientMetaDto;

import java.io.*;
import java.net.InetAddress;

public class CredentialHandler {
    private static ClientMetaDto meta;

    public static ClientMetaDto getCredentials() {
        return CredentialHandler.meta;
    }

    public static void setCredentials(ClientMetaDto meta) {
        CredentialHandler.meta = meta;
    }

    public static void setId(long id) {
        meta.setId(id);
    }

    public static void setColor(int color) {
        meta.setColor(color);
    }
}
