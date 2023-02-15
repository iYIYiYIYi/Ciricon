package core.message.implement;

public class ControlPacket extends PacketMessage{

    public ControlPacket(byte[] data) {
        super(data);
    }

    public ControlPacket(int targetID, byte[] data) {
        super(targetID, PacketTypeDefinitions.CONTROL_PACKET, data);
    }

    public int getProcessorID() {
        return getPacketLength();
    }

}
