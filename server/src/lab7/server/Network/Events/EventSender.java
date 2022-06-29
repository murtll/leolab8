package lab7.server.Network.Events;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class EventSender {
    private final ServerSocket serverSocket;
    private final ObjectMapper mapper;

    private final Set<Socket> connections = Collections.synchronizedSet(new HashSet<>());

    public EventSender(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        this.mapper = new ObjectMapper();
        this.mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        this.mapper.findAndRegisterModules();
    }

    public void startAccepting() {
        Thread eventThread = new Thread(() -> {
            while (true) {
                try {
                    Socket connection = serverSocket.accept();
                    connections.add(connection);
                } catch (IOException ignored) {}
            }
        });
        eventThread.setDaemon(true);
        eventThread.start();
    }

    public void notifyAboutEvent(EventDataDto event) {
        Socket currentConnection = null;
        try {
            String s = mapper.writeValueAsString(event);
            for (Socket connection : connections) {
                currentConnection = connection;
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                writer.write(s);
                writer.newLine();
                writer.flush();
            }
        } catch (JsonProcessingException ignored) {
        } catch (IOException e) {
            connections.remove(currentConnection);
        }
    }

}
