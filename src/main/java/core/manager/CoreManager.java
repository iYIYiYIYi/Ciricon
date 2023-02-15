package core.manager;

import core.Handler.HandlerPool;
import core.data.DataCenter;
import core.network.HttpServer;
import core.network.WebSocketServer;
import core.network.implement.UDPServer;
import core.scheduler.SchedulerManager;
import io.javalin.Javalin;
import org.mapdb.DB;

import javax.script.ScriptEngineManager;
import java.net.UnknownHostException;

public class CoreManager {

    public static int SERVER_ID = 200000000;

    private static HandlerPool handlerPool;
    private static UDPServer udpServer;
    private static DataCenter dataCenter;
    private static HttpServer httpServer;
    private static WebSocketServer webSocketServer;
    private static ScriptEngineManager scriptEngineManager;
    private SchedulerManager schedulerManager;



    public void init() throws Exception {
        scriptEngineManager = new ScriptEngineManager();
        dataCenter = new DataCenter();
        dataCenter.init();
        handlerPool = new HandlerPool();
        handlerPool.init();
        udpServer = new UDPServer();
        udpServer.run();
        httpServer = new HttpServer();
        httpServer.init();
        webSocketServer = new WebSocketServer();
        webSocketServer.init();
        schedulerManager = new SchedulerManager();
        schedulerManager.init();
    }

    public static Javalin getApp() {
        return httpServer.getApp();
    }
    public static HandlerPool getHandlerPool() {
        return handlerPool;
    }

    public static DB getDB() {
        return dataCenter.getDB();
    }

    public static DataCenter getDataCenter() {
        return dataCenter;
    }

    public static UDPServer getUdpServer() {
        return udpServer;
    }

    public static WebSocketServer getWebSocketServer() {
        return webSocketServer;
    }

    public static ScriptEngineManager getScriptEngineManager() {
        return scriptEngineManager;
    }

}
