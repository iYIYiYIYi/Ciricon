package core.Handler.implement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import core.Handler.HandlerI;
import core.manager.CoreManager;
import core.message.MessageI;
import core.message.implement.PacketTypeDefinitions;
import core.utils.DataTransform;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class StreamHandler implements HandlerI {

    private class StreamRecord {
        Queue<MessageI> messageIS;
        int streamPacketCount;
    }
    public class StreamCallback {
        public Consumer<Integer> onConnect;
        public Consumer<MessageI> onReceive;
        public Consumer<Queue<MessageI>> onEnd;
    }
    private ConcurrentHashMap<Integer,  StreamRecord> dataBuffer = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, StreamCallback> callbacks = new ConcurrentHashMap<>();

    public void registerCallbackForStream(int id, Consumer<Integer> connect, Consumer<MessageI> receive, Consumer<Queue<MessageI>> end) {
        callbacks.put(id, new StreamCallback() {{
            this.onConnect = connect;
            this.onReceive = receive;
            this.onEnd = end;
        }});
    }

    @Override
    public void handle(MessageI m) throws Exception {
        if (m.getPacketType() == PacketTypeDefinitions.STREAM_PACKET) {
            int id = m.getSourceID();
            int packetACK = 200000000;
            StreamRecord streamRecord = dataBuffer.get(id);
            if (streamRecord == null) {
                callbacks.get(id).onConnect.accept(id);
                Queue<MessageI> messageIS = new ConcurrentLinkedQueue<>();
                streamRecord = new StreamRecord();
                streamRecord.messageIS = messageIS;
                JSONObject object = JSON.parseObject(m.getString());
                streamRecord.streamPacketCount = object.getIntValue("packetCount");
                dataBuffer.put(id, streamRecord);
            } else {
                int length = m.getPacketLength();
                if (length > 1024 - 16) {
                    packetACK -= (1024 - 16);
                } else if (length == 0) {
                    // 断开连接，清除记录
                    callbacks.get(id).onEnd.accept(dataBuffer.get(id).messageIS);
                    dataBuffer.remove(id);
                }
            }
            streamRecord.messageIS.add(m);
            callbacks.get(id).onReceive.accept(m);
            CoreManager.getUdpServer().send(id, DataTransform.intToByteArray(packetACK));
        }
    }

}
