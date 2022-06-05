package lab6.server.Smth;


import lab6.server.Network.ClientRequestDto;
import lab6.server.Network.DatagramServer;
import lab6.server.Network.ServerResponseDto;

import java.io.IOException;

public class CommandProcessor {
    private final DatagramServer server;
    private Thread processRequestsThread;

    public CommandProcessor(DatagramServer server) {
        this.server = server;
    }

    private void processPayload(ClientRequestDto payload) throws IOException {
        ServerResponseDto result = Commander.categorizeCommand(payload.getData());

        server.sendResponse(payload.getIp(), payload.getPort(), result);
    }

    public void start() throws InterruptedException {
        processRequestsThread = new Thread(() -> {
            while (true) {
                try {
                    ClientRequestDto request = server.receive();

                    processPayload(request);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error while reading request");
                }
            }
        });
        processRequestsThread.start();
    }

    public void stop() {
        processRequestsThread.interrupt();
    }
}
