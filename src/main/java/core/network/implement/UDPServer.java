package core.network.implement;

import core.data.entities.Equipment;
import core.manager.CoreManager;
import core.message.MessageI;
import core.message.converter.PacketMessageConverter;
import core.network.ServerI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.util.Objects;

public class UDPServer implements ServerI {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private boolean keepRunning = true;
    private DatagramSocket recv_socket;
    private DatagramSocket send_socket;
    private int RECV_port = 55320;
    private int SEND_port = 55321;

    public UDPServer() throws UnknownHostException, SocketException {
            send_socket = new DatagramSocket(SEND_port);
            send_socket.setBroadcast(true);
    }

    @Override
    public void start(int RECV_port, int SEND_port){
        this.RECV_port = RECV_port;
        this.SEND_port = SEND_port;
    }

    @Override
    public void stop() {
        keepRunning = false;
    }

    private void running() {
        logger.info("UDP Server running on RECV:" + RECV_port+" and SEND:" + SEND_port);
        try {
            recv_socket = new DatagramSocket(RECV_port);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        byte[] databuf = new byte[1024];
        DatagramPacket data = new DatagramPacket(databuf, databuf.length);

        while(keepRunning) {
            try {
                logger.info("Start listening...");
//                recv_socket.setSoTimeout(60000);
                recv_socket.receive(data);
                logger.info("Receiving Packet...");
                var address = data.getAddress();
                var message = PacketMessageConverter.convert(address, databuf);
                CoreManager.getHandlerPool().newMessage(message);
            } catch (IOException e) {
                logger.error("cant parse message", e);
            }
        }
    }

    @Override
    public void run() {
        logger.info("creating thread...");
        new Thread(this::running).start();
    }

    public void send(int target, byte[] data) throws IOException {
        InetAddress address = CoreManager.getDataCenter().getEquipments().getEquipment(target).getAddress();
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, address, SEND_port);
        send_socket.send(datagramPacket);
    }

    public void send(MessageI m) throws IOException {
        send(m.getTargetID(), m.toBytes());
    }

    public void socket_broadcast(byte[] data) throws IOException {
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, InetAddress.getByName("192.168.2.255"), SEND_port);
        send_socket.send(datagramPacket);
    }

    @Override
    public int getId() {
        return 0;
    }
}
