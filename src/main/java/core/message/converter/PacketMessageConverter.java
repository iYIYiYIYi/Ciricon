package core.message.converter;

import core.message.implement.PacketMessage;

import java.net.InetAddress;

public class PacketMessageConverter {

    public static PacketMessage convert(InetAddress address, byte[] data) {
        PacketMessage message = new PacketMessage(data);
        message.setAddress(address);
        return message;
    }

}
