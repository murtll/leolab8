package lab7.client.Network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class DatagramClient {

    private final DatagramChannel channel;
    private final InetSocketAddress remoteSocketAddress;

    public DatagramClient(InetAddress remoteIp, int remotePort) throws IOException {
        this.remoteSocketAddress = new InetSocketAddress(remoteIp, remotePort);
        this.channel = DatagramChannel.open();
        channel.configureBlocking(false);
    }

    public void send(String request) throws IOException {
        ByteBuffer buf = ByteBuffer.wrap(request.getBytes(StandardCharsets.UTF_8));
        channel.send(buf, remoteSocketAddress);
    }

    public String receiveResponse() throws IOException, InterruptedException {
        ByteBuffer buffer = ByteBuffer.allocate(4096);

        boolean success = false;
        int millisCounter = 0;

        while (millisCounter <= 1000) {
            Thread.sleep(1);
            millisCounter++;

            SocketAddress addr = channel.receive(buffer);
            if (addr != null) {
                success = true;
                break;
            }
        }

        if (!success) {
            throw new IOException();
        }

        buffer.flip();
        byte[] buf = new byte[buffer.remaining()];
        buffer.get(buf);

        return new String(buf, 0, buf.length, StandardCharsets.UTF_8);
    }
}
