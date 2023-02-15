package core.message;

public interface MessageI {

    int getPacketLength();
    int getTargetID();
    int getSourceID();
    byte[] getData();
    String getString();
    int getPacketType();

    int getId();

    byte[] toBytes();

}
