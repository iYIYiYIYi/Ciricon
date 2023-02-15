package core.Handler.implement;

import com.alibaba.fastjson.JSON;
import core.Handler.HandlerI;
import core.data.entities.Equipment;
import core.data.entities.Status;
import core.manager.CoreManager;
import core.message.MessageI;
import core.message.implement.PacketTypeDefinitions;

import java.time.Instant;

public class HeartbeatHandler implements HandlerI {
    @Override
    public void handle(MessageI m) throws Exception {
        if (m.getPacketType() == PacketTypeDefinitions.HEARTBEAT_PACKET) {
            Instant now = Instant.now();
            long epochSecond = now.getEpochSecond();
            Equipment equipment = CoreManager.getDataCenter().getEquipments().getEquipment(m.getSourceID());
            equipment.setAlive(true);
            equipment.setLast_connect_time(epochSecond);

            CoreManager.getWebSocketServer().wsBroadcast(JSON.parseObject(m.getString()));
        }
    }
}
