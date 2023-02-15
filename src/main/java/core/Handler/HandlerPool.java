package core.Handler;

import core.Handler.implement.DefaultHandler;
import core.Handler.implement.GetEquipmentsHandler;
import core.Handler.implement.RegisterHandler;
import core.message.MessageI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HandlerPool {

    private final List<HandlerI> handlers = new ArrayList<>();
    private final ConcurrentLinkedQueue<MessageI> messages = new ConcurrentLinkedQueue<>();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void init() {
        logger.info("HandlerPool initiating...");
        handlers.add(new RegisterHandler());
        handlers.add(new GetEquipmentsHandler());
        handlers.add(new DefaultHandler());

        Thread thread = new Thread(() -> {
            logger.info("start handling...");
            while (true) {
                if (messages.size() > 0)
                    eat();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    void eat() {
        logger.info("start processing message...");
        MessageI m = messages.poll();
        if(m == null)
            return;

        for (var handler: handlers) {
            try {
                handler.handle(m);
            } catch (Exception e) {
                logger.warn("Something went wrong", e);
            }
        }
    }

    public void newMessage(MessageI m) {
        messages.add(m);
    }

}
