package core.Handler.implement;

import com.alibaba.fastjson.JSONObject;
import core.Handler.HandlerI;
import core.manager.CoreManager;
import core.message.MessageI;
import core.message.implement.PacketTypeDefinitions;

public class ControlHandler implements HandlerI {

    @Override
    public void handle(MessageI m) throws Exception {
        if (m.getPacketType() == PacketTypeDefinitions.CONTROL_PACKET) {
            JSONObject object = new JSONObject();
            object.put("id", m.getSourceID());
            object.put("status", m.getString());
            CoreManager.getWebSocketServer().wsBroadcast(object);
        }
    }
}
