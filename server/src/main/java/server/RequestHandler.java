package server;

import collectionmanager.databasetools.AuthenticationException;
import collectionmanager.databasetools.DatabaseException;
import console.CommandHandler;
import console.commands.CommandCode;
import console.commands.CommandResponse;
import console.commands.ExecutorResponse;
import console.exсeptions.*;
import musicband.InputValueException;
import network.Request;
import org.slf4j.Logger;
import server.exceptions.WrongRequestException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RequestHandler implements Runnable {

    private final CommandHandler commandHandler;
    private final RequestReader requestReader;
    private final ServerWriter writer;
    private final Logger logger;

    public RequestHandler(CommandHandler commandHandler, RequestReader requestReader, ServerWriter writer, Logger logger) {
        this.commandHandler = commandHandler;
        this.requestReader = requestReader;
        this.writer = writer;
        this.logger = logger;
    }

    @Override
    public void run() {
        try {
            ExecutorResponse commandResponse = new ExecutorResponse(new CommandResponse(CommandCode.DEFAULT), null);
            Request request = null;
            while (request == null){
                try {
                    request = requestReader.readRequest();
                    if (request != null) {
                        try {
                            logger.info("Got the request");
                            Future<ExecutorResponse> future = commandHandler.execute(request, null);
                            try {
                                commandResponse = future.get();
                                if (commandResponse.getCommandResponse().getCommandCode().equals(CommandCode.ERROR)) {
                                    logger.warn(commandResponse.getCommandResponse().getMessage());
                                }
                            } catch (InterruptedException | ExecutionException e) {
                                logger.warn("Problem with getting response from invoker");
                            }
                            writer.write(commandResponse.getCommandResponse());
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
