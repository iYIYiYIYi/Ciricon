package core.Handler.implement;

import core.Handler.HandlerI;
import core.manager.CoreManager;
import core.message.MessageI;
import org.slf4j.Logger;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ScriptEmbedHandler implements HandlerI {

    private final ScriptEngine engine = CoreManager.getScriptEngineManager().getEngineByName("javascript");

    public ScriptEmbedHandler(String scriptPath) {
        try {
            FileReader fileReader = new FileReader(scriptPath);
            engine.eval(fileReader);
            engine.put("Logger", logger);
            engine.put("UDP", CoreManager.getUdpServer());
            engine.put("WS", CoreManager.getWebSocketServer());
            engine.put("DATA", CoreManager.getDataCenter());
        } catch (FileNotFoundException | ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handle(MessageI m) throws Exception {
        Invocable invocable = (Invocable) engine;
        invocable.invokeFunction("handle", m);
    }
}
