package console;

import console.commands.CommandResponse;
import org.slf4j.Logger;
import server.ServerWriter;

import java.io.IOException;
import java.util.concurrent.RecursiveAction;

public class ResponseSender extends RecursiveAction {

    private final Logger logger;
    private final ServerWriter writer;
    private final CommandResponse commandResponse;

    public ResponseSender(Logger logger, ServerWriter writer, CommandResponse commandResponse) {
        this.logger = logger;
        this.writer = writer;
        this.commandResponse = commandResponse;
    }

    @Override
    protected void compute() {
        try {
            writer.write(commandResponse);
            writer.sendResponse();
            logger.info("Send response");
        } catch (IOException e) {
            logger.warn("Connection refused");
        }
    }
}
