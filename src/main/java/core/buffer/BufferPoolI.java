package core.buffer;

public interface BufferPoolI {

    public void registerBuffer(BufferI bufferI);

    public void distribute(int id, int count);

    public void withdraw(int id);

}
