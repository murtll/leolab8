package lab7.client.Network.Events;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

public class EventListener {
    private Socket socket;
    private final ObjectMapper mapper;
    private BufferedReader input;

    private final Set<Notifiable> subscribers = Collections.synchronizedSet(new HashSet<>());

    public EventListener(InetAddress ip, int port) throws IOException {
        socket = new Socket(ip, port);
        this.mapper = new ObjectMapper();
        this.mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        this.mapper.findAndRegisterModules();
    }

    public void startListening() throws IOException {
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Thread eventThread = new Thread(() -> {
            mainLoop:while (true) {
                try {
                    String s = input.readLine();
                    EventDataDto event = mapper.readValue(s, EventDataDto.class);

                    for (Notifiable n : subscribers) {
                        n.notifyAboutEvent(event);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Cannot read line from server");
                    if (socket.isClosed()) while (true) {
                        try {
                            System.out.println("Trying to reconnect");
                            socket = new Socket(socket.getInetAddress(), socket.getPort());
                            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            continue mainLoop;
                        } catch (IOException ex) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException exc) {
                                return;
                            }
                        }
                    }
                }
            }
        });
        eventThread.setDaemon(true);
        eventThread.start();
    }

    public void subscribe(Notifiable n) {
        subscribers.add(n);
    }

    public void unsubscribe(Notifiable n) {
        subscribers.add(n);
    }
}
