package lab6.server.Smth;


import lab6.server.Network.ClientRequestDto;
import lab6.server.Network.DatagramServer;
import lab6.server.Network.ServerResponseDto;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class CommandProcessor {
    private final DatagramServer server;
    private Thread processRequestsThread;

    private final Logger logger;

    public CommandProcessor(DatagramServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    private void processPayload(ClientRequestDto payload) throws IOException {
        ServerResponseDto result = Commander.categorizeCommand(payload.getData());

        server.sendResponse(payload.getIp(), payload.getPort(), result);
    }

    public void start() throws InterruptedException {
        processRequestsThread = new Thread(() -> {
            logger.info("Listening for requests");
            while (true) {
                try {
                    ClientRequestDto request = server.receive();

                    processPayload(request);
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("Error while reading request");
                }
            }
        }, "ProcessRequestsThread");
        processRequestsThread.start();
    }

    public void stop() {
        processRequestsThread.interrupt();
        logger.info("Process request thread stopped");
    }
}
