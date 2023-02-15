package core.buffer.implement;

import core.buffer.BufferI;
import core.buffer.BufferPoolI;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BufferPool implements BufferPoolI {

    private Queue<BufferI> buffers;
    private ConcurrentHashMap<Integer, ArrayList<BufferI>> distributeTable;
    private int bufferCount = 8 * 1024;
    private int bufferSize = 0;

    public BufferPool() {
        init();
    }

    public BufferPool(int bufferCount) {
        this.bufferCount = bufferCount;
        init();
    }

    public BufferPool(int bufferCount, int bufferSize) {
        this.bufferSize = bufferSize;
        this.bufferCount = bufferCount;
        init();
    }

    private void init() {
        buffers = new LinkedList<>();
        distributeTable = new ConcurrentHashMap<>();
        for (int i = 0; i < bufferCount; i++) {
            if (bufferSize != 0) {
                buffers.add(new StreamBuffer(bufferSize));
            } else {
                buffers.add(new StreamBuffer(bufferSize));
            }
        }
    }

    @Override
    public void registerBuffer(BufferI bufferI) {
        buffers.add(bufferI);
        bufferCount ++;
    }

    @Override
    public void distribute(int id, int count) {
        if (distributeTable.get(id) == null) {
            ArrayList<BufferI> bufferIS = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                if (buffers.isEmpty()) {
                    registerBuffer(new StreamBuffer(this.bufferSize));
                }
                bufferIS.add(buffers.poll());
            }
        }
    }

    @Override
    public void withdraw(int id) {
        ArrayList<BufferI> bufferIS = distributeTable.get(id);
        buffers.addAll(bufferIS);
        bufferIS.clear();
    }

    public void write(int id, byte[] data) {
        ArrayList<BufferI> bufferIS = distributeTable.get(id);
        boolean writeFlag = false;
        for (BufferI is : bufferIS) {
            if (is.getSpareRoom() >= data.length) {
                is.write(data, data.length);
                writeFlag = true;
                break;
            }
        }
        if (! writeFlag) {
            //申请空间
            int count = data.length / this.bufferSize + 1;
            distribute(id, count);
        }
    }

    public void read(int id, byte[] dst, int length, int offset) {

    }
}
