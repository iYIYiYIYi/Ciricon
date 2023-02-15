package core.network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import core.data.Equipments;
import core.data.entities.Equipment;
import core.data.entities.Operation;
import core.manager.CoreManager;
import core.message.implement.ControlPacket;
import core.message.implement.PacketMessage;
import io.javalin.Javalin;
import org.mapdb.HTreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class HttpServer {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private Javalin app;

    public void init() {
        logger.info("HttpServer initiating...");
        app = Javalin.create()
                .post("/control",ctx -> {
                    Operation parse = JSON.parseObject(ctx.body(), Operation.class);
                    int equip = parse.getEquipmentID();
                    if (CoreManager.getDataCenter().getOperations().hasOperation(parse)) {
                        CoreManager.getUdpServer().send(new ControlPacket(parse.getEquipmentID(), JSON.toJSONBytes(parse)));
                    }
                })
                .post("/send",ctx -> {
                    JSONObject obj = JSON.parseObject(ctx.body());
                    int id = obj.getIntValue("id");
                    int type = obj.getIntValue("type");
                    byte[] data = obj.getBytes("data");
                    CoreManager.getUdpServer().send(new PacketMessage(id,type,data));
                })
                .get("/equipments", context -> {
                    HTreeMap<Integer, Equipment> map = CoreManager.getDataCenter().getEquipments().getMap();
                    context.json(map);
                })
                .get("/equipment/{id}", ctx -> {
                    int id = Integer.parseInt(ctx.pathParam("id"));
                    HTreeMap<Integer, Equipment> map = CoreManager.getDataCenter().getEquipments().getMap();
                    Equipment equipment = map.get(id);
                    if(null != equipment) {
                        ctx.json(equipment);
                    } else {
                        ctx.result("{}");
                    }
                })
                .get("/operations/{id}",ctx -> {
                    int id = Integer.parseInt(ctx.pathParam("id"));
                    ArrayList<Operation> operations = CoreManager.getDataCenter().getOperations().getOperations(id);
                    ctx.json(operations);
                }).start(55350);
    }

    public Javalin getApp() {
        return app;
    }
}
