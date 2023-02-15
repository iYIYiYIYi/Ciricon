package core.Handler;

import core.message.MessageI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public interface HandlerI {

    final Logger logger = LoggerFactory.getLogger(HandlerI.class);

    void handle(MessageI m) throws Exception;

}
