package client;

import console.*;
import console.commands.CommandResponse;
import console.exсeptions.IncorrectScriptException;
import console.exсeptions.NoArgumentFoundException;
import musicband.MusicBand;
import musicband.MusicBandFieldsChecker;
import network.CurrentUser;

import java.io.*;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Stream;

public class ClientExecuteScriptCommand {

    private ConsoleWriter writer;
    private HashSet<File> scripts;
    private boolean isInnerScript;
    private Collection<String> commandsWithExtendedRequest;
    private int port;
    private String hostAddress;
    private UserManager userManager;

    public ClientExecuteScriptCommand(ConsoleWriter writer, Collection<String> commandsWithExtendedRequest, int port, String hostAddress, UserManager userManager) {
        this.writer = writer;
        this.commandsWithExtendedRequest = commandsWithExtendedRequest;
        this.isInnerScript = false;
        this.scripts = new HashSet<>();
        this.port = port;
        this.hostAddress = hostAddress;
        this.userManager = userManager;
    }

    public ClientExecuteScriptCommand(ConsoleWriter writer, Collection<String> commandsWithExtendedRequest, int port, String hostAddress, UserManager userManager, HashSet<File> scripts) {
        this.writer = writer;
        this.commandsWithExtendedRequest = commandsWithExtendedRequest;
        this.isInnerScript = true;
        this.scripts = scripts;
        this.port = port;
        this.hostAddress = hostAddress;
        this.userManager = userManager;
    }

    public CommandResponse execute(String firstArgument, MusicBand requestedMusicBand) throws NoArgumentFoundException {
        CommandResponse commandResponse = new CommandResponse();
        String message = "";
        String path = firstArgument.trim();
        if (path.equals("")) throw new NoArgumentFoundException();
        File file = new File(path);
        try {
            if (file.exists()) {
                if (file.canRead()) {
                    if (!scripts.contains(file)) {
                        scripts.add(file);
                        if (containsExit(file)) {
                            BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                            MusicBandFieldsChecker fieldsReader = new MusicBandFieldsChecker(fileReader);
                            FieldsReader scriptModeReader = new FieldsReader(fieldsReader, writer, false);
                            RequestFabric requestFabric = new RequestFabric(commandsWithExtendedRequest, scriptModeReader, userManager, fieldsReader);
                            ClientExecuteScriptCommand innerExecuteScriptCommand = new ClientExecuteScriptCommand(writer, commandsWithExtendedRequest, port, hostAddress, userManager, scripts);
                            client.Console console = new Console(requestFabric, fileReader, innerExecuteScriptCommand, port, hostAddress);
                            try {
                                console.run();
                            } catch (IncorrectScriptException e) {
                                writer.write(e.getMessage());
                                message += e.getMessage() + "\n";
                            } catch (NullPointerException | StackOverflowError e) {
                                writer.write("Error. Incorrect Script");
                                message += "Error. Incorrect Script\n";
                            }
                        } else {
                            writer.write("Script doesn't contain exit. It wouldn't be executed.");
                            message += "Script doesn't contain exit. It wouldn't be executed.\n";
                        }
                    } else {
                        writer.write("Recursion found. Stopping the script");
                        message += "Recursion found. Stopping the script\n";
                    }
                } else {
                    writer.write("Access denied. Can't read the file");
                    message += "Access denied. Can't read the file\n";
                }
            } else {
                writer.write("Can't access the file " + path);
                message += "Can't access the file " + path + "\n";
            }
        } catch (FileNotFoundException | SecurityException e) {
            writer.write("Can't access the file " + path);
            message += "Can't access the file " + path + "\n";
        } catch (IOException e) {
            writer.write("Unexpected error with file");
            message += "Unexpected error with file\n";
        }
        if (!isInnerScript) scripts.clear();
        else scripts.remove(file);
        commandResponse.setMessage(message);
        return commandResponse;
    }

    private boolean containsExit(File file) throws IOException {
        Stream<String> stream = Files.lines(file.toPath());
        boolean contains = false;
        if (stream != null) contains = stream.anyMatch(str -> str != null && str.trim().equals("exit"));
        return contains;
    }

    public ConsoleWriter getWriter() {
        return writer;
    }

    public void setWriter(ConsoleWriter writer) {
        this.writer = writer;
    }
}

