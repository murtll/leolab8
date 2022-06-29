package lab7.server.Network;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lab7.server.Smth.RequestProcessor;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class DatagramServer {
    private final DatagramSocket socket;
    private final ObjectMapper mapper;
    private final Logger logger;
    private Thread listenForRequestsThread;

    private final RequestProcessor processor;


    public DatagramServer(int port, Logger logger, RequestProcessor processor) throws SocketException {
        this.socket = new DatagramSocket(port);
        this.mapper = new ObjectMapper();
        this.mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        this.mapper.findAndRegisterModules();
        this.logger = logger;
        this.processor = processor;
    }

    public void receive() throws IOException {
        byte[] buf = new byte[2048];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        processor.submitToReadRequestsPool(() -> processPacket(packet));
    }

    public void start() throws InterruptedException {
        listenForRequestsThread = new Thread(() -> {
            logger.info("Listening for requests");
            while (true) {
                try {
                    receive();
                } catch (IOException e) {
                    logger.error("Error reading request");
                }
            }
        });
        listenForRequestsThread.start();
    }

    private void processPacket(DatagramPacket packet) {
        String data = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);

        String maskedData = data.replaceAll("(\"password\":\")(\\w*)", "$1***");
        logger.info("Received packet from " + packet.getAddress() + ":" + packet.getPort() + ", payload " + maskedData);

        try {
            ClientRequestDto clientRequest = mapper.readValue(data, ClientRequestDto.class);
            clientRequest.setIp(packet.getAddress());
            clientRequest.setPort(packet.getPort());

            processor.addRequestToProcess(clientRequest, this);
        } catch (JsonProcessingException e) {
            logger.error("Error deserializing data");
        }
    }

    public void sendResponse(InetAddress ip, int port, ServerResponseDto responseDto) throws IOException {
        String response = mapper.writeValueAsString(responseDto);

        byte[] buf = response.getBytes(StandardCharsets.UTF_8);

        DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, port);

        logger.info("Sending packet to " + ip + ":" + port + ", payload " + response);

        socket.send(packet);
    }

    public void stop() {
        listenForRequestsThread.interrupt();
        logger.info("Process request thread stopped");
    }
}
