package console;

import network.Request;

import java.nio.channels.SocketChannel;

public class RequestListenerResponse {

    private Request request;
    private SocketChannel socketChannel;

    public RequestListenerResponse(Request request, SocketChannel socketChannel) {
        this.request = request;
        this.socketChannel = socketChannel;
    }

    public Request getRequest() {
        return request;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }
}
