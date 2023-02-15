package core.Handler.implement;

import com.alibaba.fastjson.JSONObject;
import core.Handler.HandlerI;
import core.manager.CoreManager;
import core.message.MessageI;
import core.message.implement.PacketMessage;
import core.message.implement.PacketTypeDefinitions;

import java.nio.charset.StandardCharsets;


public class GetEquipmentsHandler implements HandlerI {
    @Override
    public void handle(MessageI m) throws Exception {
        if (m.getTargetID() == CoreManager.SERVER_ID && m.getPacketType() == PacketTypeDefinitions.INFO_PACKET) {
            logger.info("Give out map...");
            var map = CoreManager.getDataCenter().getEquipments().getMap();
            String obj = JSONObject.toJSONString(map);
            PacketMessage message = new PacketMessage(m.getSourceID(), PacketTypeDefinitions.INFO_PACKET, obj.getBytes(StandardCharsets.UTF_8));
            CoreManager.getUdpServer().send(message);
        }
    }
}
