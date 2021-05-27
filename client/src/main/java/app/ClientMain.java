package app;

import client.*;
import console.ConsoleWriter;
import console.FieldsReader;
import client.ClientExecuteScriptCommand;
import musicband.MusicBandFieldsChecker;
import network.CurrentUser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;

public class ClientMain {

    private static int PORT = 3101;
    private static String hostAddress;

    public static void main(String[] args) {
        setArguments(args);
        System.out.println("Working with: " + hostAddress + " port: " + PORT);
        HashSet<String> commandsWithExtendedRequest = new HashSet<>(); //
        commandsWithExtendedRequest.add("add"); //
        commandsWithExtendedRequest.add("insert_at"); //
        commandsWithExtendedRequest.add("update"); //
        ConsoleWriter consoleWriter = new ConsoleWriter(); //
        java.io.Console standardConsole = System.console();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)); //
        PasswordCipher passwordCipher = new PasswordCipher();
        Authenticator authenticator = new Authenticator(bufferedReader, consoleWriter, passwordCipher, standardConsole);
        String login = authenticator.readLogin();
        byte[] password = authenticator.readPassword();
        CurrentUser currentUser = new CurrentUser(login, password);
        consoleWriter.write("============================");
        FieldsReader fieldsReader = new FieldsReader(new MusicBandFieldsChecker(bufferedReader), consoleWriter); //
        RequestFabric requestFabric = new RequestFabric(commandsWithExtendedRequest, fieldsReader, currentUser); //
        ClientExecuteScriptCommand executeScriptCommand = new ClientExecuteScriptCommand(consoleWriter, commandsWithExtendedRequest, PORT, hostAddress, currentUser); //
        Console console = new Console(requestFabric, bufferedReader, executeScriptCommand, PORT, hostAddress); //
        console.run();
    }

    private static void setArguments(String[] args) {
        if (args.length > 0) {
            hostAddress = args[0];
            if (args.length > 1) {
                PORT = Integer.parseInt(args[1]);
            }
        }
    }

}
