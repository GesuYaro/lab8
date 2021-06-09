package server;

import collectionmanager.ArrayListManager;
import collectionmanager.CollectionManager;
import collectionmanager.databasetools.DatabaseConnector;
import collectionmanager.databasetools.DatabaseManager;
import collectionmanager.databasetools.UserChecker;
import console.*;
import console.commands.*;
import musicband.MusicBandFieldsChecker;
import network.Request;
import org.slf4j.Logger;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;

public class Console {

    private volatile ArrayListManager arrayListManager;
    private Connector connector;
    private Logger logger;
    private volatile CollectionManager databaseManager;
    private volatile CommandHandler commandHandler;
    private UserChecker userChecker;

    public Console(ArrayListManager arrayListManager, Connector connector, Logger logger, CollectionManager databaseManager, UserChecker userChecker) {
        this.arrayListManager = arrayListManager;
        this.connector = connector;
        this.logger = logger;
        this.databaseManager = databaseManager;
        this.userChecker = userChecker;
    }

    public void run(boolean singleIterationMode) {

        CommandHandler.HistoryStorage historyStorage = new CommandHandler.HistoryStorage();
        HashMap<String, Command> commands = new HashMap<>();
        commands.put("info", new InfoCommand(arrayListManager));
        commands.put("show", new ShowCommand(arrayListManager));
        commands.put("add", new AddCommand(arrayListManager, databaseManager));
        commands.put("update", new UpdateCommand(arrayListManager, databaseManager));
        commands.put("remove_by_id", new RemoveByIdCommand(arrayListManager, databaseManager));
        commands.put("clear", new ClearCommand(arrayListManager, databaseManager));
        commands.put("exit", new ExitCommand());
        commands.put("insert_at", new InsertAtCommand(arrayListManager, databaseManager));
        commands.put("remove_last", new RemoveLastCommand(arrayListManager, databaseManager));
        commands.put("history", new HistoryCommand(historyStorage));
        commands.put("count_greater_than_genre", new CountGreaterThanGenreCommand(arrayListManager));
        commands.put("filter_less_than_singles_count", new FilterLessThanSinglesCountCommand(arrayListManager));
        commands.put("print_field_descending_genre", new PrintFieldsDescendingGenreCommand(arrayListManager));
        commands.put("help", new HelpCommand(commands));
        commands.put("exit", new ExitCommand());
        commands.put("sign_in", new SignInCommand(userChecker));
        commands.put("sign_up", new SignUpCommand(userChecker));
        commandHandler = new CommandHandler(commands, historyStorage, userChecker);

        logger.info("App is turned on.");

        List<SocketChannel> socketChannelList = new LinkedList<>();
        List<Future<RequestListenerResponse>> requestList = new LinkedList<>();
        List<Future<ExecutorResponse>> commandResponseList = new LinkedList<>();

        ForkJoinPool requestListenerPool = new ForkJoinPool();
        ForkJoinPool responseSenderPool = new ForkJoinPool();

        do {
            SocketChannel socketChannel = null;
            try {
                socketChannel = connector.getSocketChannel();
            } catch (IOException e) {
                logger.warn("Can not connect");
            }

            if (socketChannel != null) {
                socketChannelList.add(socketChannel);
            }

            for (Iterator<SocketChannel> socketChannelIterator = socketChannelList.iterator(); socketChannelIterator.hasNext(); ) {
                SocketChannel currentSocketChannel = socketChannelIterator.next();
                requestList.add(requestListenerPool.submit(new RequestListener(new RequestReader(currentSocketChannel, ByteBuffer.allocate(1024)), logger, currentSocketChannel)));
                socketChannelIterator.remove();
            }

            for (Iterator<Future<RequestListenerResponse>> requestIterator = requestList.iterator(); requestIterator.hasNext(); ) {
                Future<RequestListenerResponse> requestFuture = requestIterator.next();
                try {
                    if (requestFuture.isDone()) {
                        commandResponseList.add(commandHandler.execute(requestFuture.get().getRequest(), requestFuture.get().getSocketChannel()));
                        requestIterator.remove();
                    } else if (requestFuture.isCancelled()) {
                        logger.warn("Request is cancelled");
                        requestIterator.remove();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    logger.error("Problem with getting Future<Response>");
                    requestIterator.remove();
                }
            }

            for (Iterator<Future<ExecutorResponse>> responseIterator = commandResponseList.iterator(); responseIterator.hasNext(); ) {
                Future<ExecutorResponse> responseFuture = responseIterator.next();
                try {
                    if (responseFuture.isDone()) {
                        responseSenderPool.submit(new ResponseSender(logger, new ServerWriter(responseFuture.get().getSocketChannel()), responseFuture.get().getCommandResponse()));
                        responseIterator.remove();
                    } else if (responseFuture.isCancelled()) {
                        logger.warn("Response is cancelled");
                        responseIterator.remove();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    logger.error("Problem with sending response");
                    responseIterator.remove();
                }
            }

        } while (!singleIterationMode);
    }



}
