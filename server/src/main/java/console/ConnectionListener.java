package console;

import server.Connector;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.RecursiveTask;

import org.slf4j.Logger;

public class ConnectionListener extends RecursiveTask<SocketChannel> {

    private Connector connector;
    private Logger logger;

    public ConnectionListener(Connector connector, Logger logger) {
        this.connector = connector;
        this.logger = logger;
    }

    @Override
    protected SocketChannel compute() {
        SocketChannel socketChannel = null;
        try {
            while (socketChannel == null) {
                socketChannel = connector.getSocketChannel();
            }
        } catch (IOException e) {
            logger.warn("Can not connect");
        }
        return socketChannel;
    }
}
