package core.network;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import core.manager.CoreManager;
import io.javalin.Javalin;
import io.javalin.websocket.WsContext;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

public class WebSocketServer {

    private Javalin app;
    private final ArrayList<Session> contexts = new ArrayList<>();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public WebSocketServer() {
    }

    public void init() {
        app = CoreManager.getApp();
        app.wsBefore(wsConfig -> {
            wsConfig.onConnect(wsConnectContext -> {
                contexts.add(wsConnectContext.session);
            });
        });

        app.ws("/websocket/equipment_status",wsConfig -> {
            wsConfig.onMessage(wsConnectContext -> {
                logger.info("ws message from "+wsConnectContext.getSessionId());
                JSONArray array = new JSONArray();
                CoreManager.getDataCenter().getEquipments().getMap().forEach((integer, equipment) -> {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", integer);
                    jsonObject.put("is_alive", equipment.isAlive());
                    array.add(jsonObject);
                });
                wsConnectContext.send(array.toJSONString());
            });

            wsConfig.onClose(wsCloseContext -> {
                contexts.remove(wsCloseContext.session);
            });
        });
    }

    public void wsBroadcast(String m) throws IOException {
        for (Session context : contexts) {
            context.getRemote().sendString(m);
        }
    }

    public void wsBroadcast(JSONObject o) throws IOException {
        for (Session context : contexts) {
            context.getRemote().sendBytes(ByteBuffer.wrap(JSONObject.toJSONBytes(o)));
        }
    }

}
