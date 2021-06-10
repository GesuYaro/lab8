package client;

import console.ConsoleWriter;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;

public class Authenticator {

    private BufferedReader bufferedReader;
    private ConsoleWriter consoleWriter;
    private PasswordCipher passwordCipher;
    private Console console;

    public Authenticator(BufferedReader bufferedReader, ConsoleWriter consoleWriter, PasswordCipher passwordCipher, Console console) {
        this.bufferedReader = bufferedReader;
        this.consoleWriter = consoleWriter;
        this.passwordCipher = passwordCipher;
        this.console = console;
    }

    public Authenticator() {
    }

    public String readLogin() {
        String login = null;
        consoleWriter.write("Enter your login:");
        while (login == null) {
            try {
                login = bufferedReader.readLine();
            } catch (IOException e) {
                consoleWriter.write("Unexpected problem with reading login");
            }
        }
        return login;
    }

    public byte[] readPassword() {
        String password = "no_password";
        consoleWriter.write("Enter your password or skip it:");
        try {
            char[] passwordChars;
            String prePassword = null;
            if (console != null) {
                passwordChars = console.readPassword();
                prePassword = new String(passwordChars);
            } else {
                prePassword = bufferedReader.readLine();
            }
            if (prePassword != null) {
                password = prePassword;
            }
        } catch (IOException e) {
            consoleWriter.write("Unexpected problem with reading password");
        }
        return passwordCipher.hashPassword(password);
    }

    public byte[] password(String pswrd) {
        return passwordCipher.hashPassword(pswrd);
    }

}
