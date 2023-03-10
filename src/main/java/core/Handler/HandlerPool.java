package core.Handler;

import core.Handler.implement.*;
import core.message.MessageI;
import core.utils.DataTransform;
import core.utils.TaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class HandlerPool {

    private final List<HandlerI> handlers = new CopyOnWriteArrayList<>();
    private class MessageList {
        private final ConcurrentLinkedQueue<MessageI> messages = new ConcurrentLinkedQueue<>();
        public void add(MessageI e) {
            messages.add(e);
            TaskExecutor.submit(HandlerPool.this::eat);
        }
    }

    private final MessageList messageList = new MessageList();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void init() {
        logger.info("HandlerPool initiating...");

        handlers.add(new RegisterHandler());
        handlers.add(new GetEquipmentsHandler());
        handlers.add(new DefaultHandler());
        handlers.add(new HeartbeatHandler());
        handlers.add(new StreamHandler());
        handlers.add(new ControlHandler());

//        Thread thread = new Thread(() -> {
//            logger.info("start handling...");

//            while (true) {
//                if (messages.size() > 0)
//                    eat();
//            }
//        });
//        thread.setDaemon(true);
//        thread.start();
    }

    void eat() {
        logger.info("start processing message...");
        MessageI m = messageList.messages.poll();
        if(m == null)
            return;

        for (HandlerI handler: handlers) {
            try {
                handler.handle(m);
            } catch (Exception e) {
                logger.warn("Something went wrong\n", e);
            }
        }
    }

    public void newMessage(MessageI m) {
        messageList.add(m);
    }

    public void registerNewHandler(HandlerI handlerI) {
        handlers.add(handlerI);
    }

    public void registerNewScriptEmbedHandler(String name) {
        String path = DataTransform.getScriptPathByName(name);
        ScriptEmbedHandler scriptEmbedHandler = new ScriptEmbedHandler(path);
        registerNewHandler(scriptEmbedHandler);
    }

}
