package core.buffer;

public interface BufferI {

    public void read(byte[] target, int length);

    public void write(byte[] src, int length);

    public int getSpareRoom();

    public boolean isFull();

    public boolean isEmpty();

    public void clean();
}
