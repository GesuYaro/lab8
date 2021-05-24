package server;

import collectionmanager.databasetools.DatabaseException;
import console.CommandHandler;
import console.exсeptions.*;
import musicband.InputValueException;
import network.Request;
import org.slf4j.Logger;
import server.exceptions.WrongRequestException;

import java.io.IOException;

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
            Request request = null;
            while (request == null){
                try {
                    request = requestReader.readRequest();
                    if (request != null) {
                        try {
                            logger.info("Got the request");
                            commandHandler.execute(request);
                        } catch (NoArgumentFoundException | InputValueException | IndexOutOfBoundsException | NoSuchIdException |
                                NotEnoughArgumentsException | DatabaseException e) {
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
