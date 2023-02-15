package core.buffer.implement;

import core.buffer.BufferI;

import java.nio.ByteBuffer;

public class StreamBuffer implements BufferI {

    private int bufferSize;
    private int usedRoom;

    private ByteBuffer buffer;

    public StreamBuffer() {
        this.bufferSize = 1024;
        this.usedRoom = 0;
        buffer = ByteBuffer.allocate(this.bufferSize);
    }

    public StreamBuffer(int bufferSize) {
        this.bufferSize = bufferSize;
        this.usedRoom = 0;
        buffer = ByteBuffer.allocate(this.bufferSize);
    }

    @Override
    public synchronized void read(byte[] target, int length) {
        int ptr = usedRoom - length - 1;
        if (ptr < 0) ptr = 0;
        buffer.get(ptr, target, 0, length);
        usedRoom -= length;
    }

    @Override
    public synchronized void write(byte[] src, int length) {
        buffer.put(src, 0, length);
        usedRoom += length;
    }

    @Override
    public void clean() {
        usedRoom = 0;
    }

    @Override
    public synchronized int getSpareRoom() {
        return this.bufferSize - this.usedRoom;
    }

    @Override
    public synchronized boolean isFull() {
        return this.bufferSize - this.usedRoom == 0;
    }

    @Override
    public boolean isEmpty() {
        return usedRoom == 0;
    }
}
