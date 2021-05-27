package client;

import console.*;
import console.commands.CommandCode;
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
    private CurrentUser currentUser;

    public ClientExecuteScriptCommand(ConsoleWriter writer, Collection<String> commandsWithExtendedRequest, int port, String hostAddress, CurrentUser currentUser) {
        this.writer = writer;
        this.commandsWithExtendedRequest = commandsWithExtendedRequest;
        this.isInnerScript = false;
        this.scripts = new HashSet<>();
        this.port = port;
        this.hostAddress = hostAddress;
        this.currentUser = currentUser;
    }

    public ClientExecuteScriptCommand(ConsoleWriter writer, Collection<String> commandsWithExtendedRequest, int port, String hostAddress, CurrentUser currentUser, HashSet<File> scripts) {
        this.writer = writer;
        this.commandsWithExtendedRequest = commandsWithExtendedRequest;
        this.isInnerScript = true;
        this.scripts = scripts;
        this.port = port;
        this.hostAddress = hostAddress;
    }

    public CommandResponse execute(String firstArgument, MusicBand requestedMusicBand) throws NoArgumentFoundException {
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
                            RequestFabric requestFabric = new RequestFabric(commandsWithExtendedRequest, scriptModeReader, currentUser);
                            ClientExecuteScriptCommand innerExecuteScriptCommand = new ClientExecuteScriptCommand(writer, commandsWithExtendedRequest, port, hostAddress, currentUser, scripts);
                            client.Console console = new Console(requestFabric, fileReader, innerExecuteScriptCommand, port, hostAddress);
                            try {
                                console.run();
                            } catch (IncorrectScriptException e) {
                                writer.write(e.getMessage());
                            } catch (NullPointerException | StackOverflowError e) {
                                writer.write("Error. Incorrect Script");
                            }
                        } else {
                            writer.write("Script doesn't contain exit. It wouldn't be executed.");
                        }
                    } else {
                        writer.write("Recursion found. Stopping the script");
                    }
                } else {
                    writer.write("Access denied. Can't read the file");
                }
            } else {
                writer.write("Can't access the file " + path);
            }
        } catch (FileNotFoundException | SecurityException e) {
            writer.write("Can't access the file " + path);
        } catch (IOException e) {
            writer.write("Unexpected error with file");
        }
        if (!isInnerScript) scripts.clear();
        else scripts.remove(file);
        return null;
    }

    private boolean containsExit(File file) throws IOException {
        Stream<String> stream = Files.lines(file.toPath());
        boolean contains = false;
        if (stream != null) contains = stream.anyMatch(str -> str != null && str.trim().equals("exit"));
        return contains;
    }

}

