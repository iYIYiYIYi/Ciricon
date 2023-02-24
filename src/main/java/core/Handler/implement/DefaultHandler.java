package core.Handler.implement;

import core.Handler.HandlerI;
import core.data.entities.Equipment;
import core.manager.CoreManager;
import core.message.MessageI;
import core.message.implement.PacketTypeDefinitions;

public class DefaultHandler implements HandlerI {
    @Override
    public void handle(MessageI m) throws Exception {
        if (m.getPacketType() == PacketTypeDefinitions.BROADCAST_PACKET) {
            logger.info("New Broadcast Packet received");
            for (Integer target: CoreManager.getDataCenter().getEquipments().getMap().keySet()) {
                CoreManager.getUdpServer().send(target, m.toBytes());
            }
        } else {
            int targetId = m.getTargetID();
            if (targetId == CoreManager.SERVER_ID) {
                return;
            }

            boolean containsKey = CoreManager.getDataCenter().getEquipments().getMap().containsKey(targetId);
            if (containsKey) {
                logger.info("Transit message to "+targetId);
                CoreManager.getUdpServer().send(m);
            }
        }
    }
}
