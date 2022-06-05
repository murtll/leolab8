package lab6.server.Network;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public DatagramServer(int port, Logger logger) throws SocketException {
        this.socket = new DatagramSocket(port);
        this.mapper = new ObjectMapper();
        this.mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        this.mapper.findAndRegisterModules();
        this.logger = logger;
    }

    public ClientRequestDto receive() throws IOException {
        byte[] buf = new byte[2048];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        String data = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
        logger.info("Received packet from " + packet.getAddress() + ":" + packet.getPort() + ", payload " + data);

        ClientDataDto clientData = mapper.readValue(data, ClientDataDto.class);
        return new ClientRequestDto(packet.getAddress(), packet.getPort(), clientData);
    }

    public void sendResponse(InetAddress ip, int port, ServerResponseDto responseDto) throws IOException {
        String response = mapper.writeValueAsString(responseDto);

        byte[] buf = response.getBytes(StandardCharsets.UTF_8);

        DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, port);

        logger.info("Sending packet to " + ip + ":" + port + ", payload " + response);

        socket.send(packet);
    }
}
