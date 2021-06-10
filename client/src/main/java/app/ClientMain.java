package app;

import client.*;
import console.ConsoleWriter;
import console.FieldsReader;
import client.ClientExecuteScriptCommand;
import gui.StartGUI;
import musicband.MusicBandFieldsChecker;
import network.CurrentUser;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;

public class ClientMain {

    private static int PORT = 3101;
    private static String hostAddress;
    private static boolean consoleMode = false;
    private static Console console;
    private static volatile UserManager userManager;

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
        consoleWriter.write("============================");
        MusicBandFieldsChecker musicBandFieldsChecker = new MusicBandFieldsChecker(bufferedReader);
        FieldsReader fieldsReader = new FieldsReader(musicBandFieldsChecker, consoleWriter); //
        userManager = new UserManager();
        RequestFabric requestFabric = new RequestFabric(commandsWithExtendedRequest, fieldsReader, userManager, musicBandFieldsChecker); //
        ClientExecuteScriptCommand executeScriptCommand = new ClientExecuteScriptCommand(consoleWriter, commandsWithExtendedRequest, PORT, hostAddress, userManager); //
        console = new Console(requestFabric, bufferedReader, executeScriptCommand, PORT, hostAddress); //
        if (!consoleMode) {
            userManager.setUser(new CurrentUser("pupa", authenticator.password("")));
            SwingUtilities.invokeLater(new StartGUI());
        } else {
            String login = authenticator.readLogin();
            byte[] password = authenticator.readPassword();
            CurrentUser currentUser = new CurrentUser(login, password);
            userManager.setUser(currentUser);
            console.run();
        }
    }

    private static void setArguments(String[] args) {
        if (args.length > 0) {
            consoleMode = Boolean.parseBoolean(args[0]);
            if (args.length > 1) {
                hostAddress = args[1];
                if (args.length > 2) {
                    PORT = Integer.parseInt(args[2]);
                }
            }
        }
    }

    public static Console getConsole() {
        return console;
    }

    public static UserManager getUserManager() {
        return userManager;
    }

}
