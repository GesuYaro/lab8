package server;

import collectionmanager.databasetools.AuthenticationException;
import collectionmanager.databasetools.DatabaseException;
import console.CommandHandler;
import console.commands.CommandCode;
import console.commands.CommandResponse;
import console.exсeptions.*;
import musicband.InputValueException;
import network.Request;
import org.slf4j.Logger;
import server.exceptions.WrongRequestException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RequestHandler implements Runnable {

    private CommandHandler commandHandler;
    private RequestReader requestReader;
    private ServerWriter writer;
    private Logger logger;

    public RequestHandler(CommandHandler commandHandler, RequestReader requestReader, ServerWriter writer, Logger logger) {
        this.commandHandler = commandHandler;
        this.requestReader = requestReader;
        this.writer = writer;
        this.logger = logger;
    }

    @Override
    public void run() {
        try {
            CommandResponse commandResponse = new CommandResponse(CommandCode.DEFAULT);
            Request request = null;
            while (request == null){
                try {
                    request = requestReader.readRequest();
                    if (request != null) {
                        try {
                            logger.info("Got the request");
                            Future<CommandResponse> future = commandHandler.execute(request);
                            try {
                                commandResponse = future.get();
                                if (commandResponse.getCommandCode().equals(CommandCode.NO_SUCH_COMMAND)) {
                                    logger.warn(commandResponse.getMessage());
                                }
                            } catch (InterruptedException | ExecutionException e) {
                                logger.warn("Problem with getting response from invoker");
                            }
                            writer.write(commandResponse);
                        } catch (NoArgumentFoundException | InputValueException | IndexOutOfBoundsException | NoSuchIdException |
                                NotEnoughArgumentsException | DatabaseException | AuthenticationException e) {
                            writer.write(e.getMessage());
                            logger.warn(e.getMessage());
                        }
                    }
                } catch (NoSuchCommandException | WrongRequestException e) {
                    writer.write(e.getMessage());
                    logger.warn(e.getMessage());
                } finally {
                    if (request != null) {
                        writer.sendResponse();
                        logger.info("Send response");
                    }
                }
            }
        } catch (IOException e) {
            logger.warn("Connection refused");
        }
    }

}
