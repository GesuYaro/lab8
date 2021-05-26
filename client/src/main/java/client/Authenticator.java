package client;

import console.ConsoleWriter;

import java.io.BufferedReader;
import java.io.IOException;

public class Authenticator {

    private BufferedReader bufferedReader;
    private ConsoleWriter consoleWriter;
    private PasswordCipher passwordCipher;

    public Authenticator(BufferedReader bufferedReader, ConsoleWriter consoleWriter, PasswordCipher passwordCipher) {
        this.bufferedReader = bufferedReader;
        this.consoleWriter = consoleWriter;
        this.passwordCipher = passwordCipher;
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
            String prePassword = bufferedReader.readLine();
            if (prePassword != null) {
                password = prePassword;
            }
        } catch (IOException e) {
            consoleWriter.write("Unexpected problem with reading password");
        }
        return passwordCipher.hashPassword(password);
    }

}
