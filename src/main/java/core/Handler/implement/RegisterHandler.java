package core.Handler.implement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import core.Handler.HandlerI;
import core.data.entities.Equipment;
import core.data.entities.Operation;
import core.manager.CoreManager;
import core.message.MessageI;
import core.message.implement.PacketMessage;
import core.message.implement.PacketTypeDefinitions;
import core.message.implement.RegisterJsonMessage;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterHandler implements HandlerI {
    @Override
    public void handle(MessageI m) throws Exception {
        if (m instanceof PacketMessage && m.getPacketType() == PacketTypeDefinitions.REGISTER_PACKET) {
            InetAddress address = ((PacketMessage) m).getAddress();
            logger.info("New Equipment Registering... IP:" + address.getHostAddress());
            var equipment = new Equipment();
            equipment.setId(m.getSourceID());
            JSONObject obj_data = JSON.parseObject(m.getString());
            equipment.setType(obj_data.getIntValue("type"));
            equipment.setDescribe(obj_data.getString("describe"));
            equipment.setAddress(address);
            List<Operation> parse = obj_data.getJSONArray("operations").toJavaList(Operation.class);
            int targetId = CoreManager.getDataCenter().getEquipments().addElement(equipment);
            for (Operation operation : parse) {
                operation.setEquipmentID(targetId);
            }
            CoreManager.getDataCenter().getOperations().addOperations(parse);
            Map<String, Object> map = new HashMap<>();
            map.put("id", targetId);
            JSONObject obj = new JSONObject(map);
            CoreManager.getUdpServer().send(new PacketMessage(targetId, PacketTypeDefinitions.REGISTER_PACKET, JSON.toJSONBytes(obj)));
            CoreManager.getDB().commit();
        }
    }

}
