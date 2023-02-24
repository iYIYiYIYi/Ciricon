package core.message.implement;

import core.manager.CoreManager;
import core.message.MessageI;
import core.utils.DataTransform;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;


public class PacketMessage implements MessageI {

    private InetAddress address;

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    private final byte[] src;
    private int packetLength;
    private final int targetID;
    private final int sourceID;

    public void setPacketType(int packetType) {
        this.packetType = packetType;
    }

    private int packetType;

    public PacketMessage(byte[] data) {
        src = Arrays.copyOfRange(data, 16, data.length);
        packetLength = DataTransform.byteArrayToInt(Arrays.copyOfRange(data, 0, 4));
        targetID = DataTransform.byteArrayToInt(Arrays.copyOfRange(data, 4, 8));
        sourceID = DataTransform.byteArrayToInt(Arrays.copyOfRange(data, 8, 12));
        packetType = DataTransform.byteArrayToInt(Arrays.copyOfRange(data, 12, 16));
    }

    public PacketMessage(int targetID, int packetType, byte[] data) {
        sourceID = CoreManager.SERVER_ID;
        this.packetType = packetType;
        this.targetID = targetID;
        src = data;
    }

    @Override
    public int getPacketLength() {
        return packetLength;
    }

    @Override
    public int getTargetID() {
        return targetID;
    }

    @Override
    public int getSourceID() {
        return sourceID;
    }

    @Override
    public byte[] getData() {
        return src;
    }

    @Override
    public String getString() {
        return new String(src);
    }

    @Override
    public int getPacketType() {
        return packetType;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public byte[] toBytes() {
        byte[] length = DataTransform.intToByteArray(packetLength);
        byte[] target = DataTransform.intToByteArray(targetID);
        byte[] source = DataTransform.intToByteArray(sourceID);
        byte[] type = DataTransform.intToByteArray(packetType);
        ByteBuffer buffer = ByteBuffer.allocate(length.length + target.length + source.length + type.length + src.length);
        buffer.put(length);
        buffer.put(target);
        buffer.put(source);
        buffer.put(type);
        buffer.put(src);
        return buffer.array();
    }
}
