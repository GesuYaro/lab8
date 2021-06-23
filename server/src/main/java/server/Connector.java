package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Connector {

    private final int port;
    private final ServerSocketChannel serverSocketChannel;

    public Connector(int port) throws IOException {
        this.port = port;
        SocketAddress address = new InetSocketAddress(port);
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(address);
        serverSocketChannel.configureBlocking(false);
    }

    public SocketChannel getSocketChannel() throws IOException {
        SocketChannel socketChannel = serverSocketChannel.accept();
        if (socketChannel != null) {
            socketChannel.configureBlocking(false);
        }
        return socketChannel;
    }

}
