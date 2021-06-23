package console;

import network.Request;
import org.slf4j.Logger;
import server.RequestReader;
import server.exceptions.WrongRequestException;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.RecursiveTask;

public class RequestListener extends RecursiveTask<RequestListenerResponse> {

    private final RequestReader requestReader;
    private final Logger logger;
    private final SocketChannel socketChannel;

    public RequestListener(RequestReader requestReader, Logger logger, SocketChannel socketChannel) {
        this.requestReader = requestReader;
        this.logger = logger;
        this.socketChannel = socketChannel;
    }

    @Override
    protected RequestListenerResponse compute() {
        Request request = null;
        try {
            while (request == null) {
                try {
                    request = requestReader.readRequest();
                } catch (WrongRequestException e) {
                    logger.warn(e.getMessage());
                }
            }
            logger.info("Got the request");
        } catch (IOException e) {
            logger.warn("Connection refused");
        }
        return new RequestListenerResponse(request, socketChannel);
    }

}
