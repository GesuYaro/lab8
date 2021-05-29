package console.commands;

import java.nio.channels.SocketChannel;

public class ExecutorResponse {

    private CommandResponse commandResponse;
    private SocketChannel socketChannel;

    public ExecutorResponse(CommandResponse commandResponse, SocketChannel socketChannel) {
        this.commandResponse = commandResponse;
        this.socketChannel = socketChannel;
    }

    public CommandResponse getCommandResponse() {
        return commandResponse;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }
}
