package core.network;

import java.net.SocketException;

public interface ServerI {

    void start(int RECV_port, int SEND_port) throws SocketException;

    void stop();
    void run();
    int getId();

}
