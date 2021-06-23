package client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connector {

    private final int port;
    private final InetAddress inetAddress;
    public static final int DEFAULT_NUMBER_OF_ATTEMPTS = 5;
    private final int numberOfReconnectAttempts;

    public Connector(InetAddress inetAddress, int port) {
        this.port = port;
        this.inetAddress = inetAddress;
        this.numberOfReconnectAttempts = DEFAULT_NUMBER_OF_ATTEMPTS;
    }

    public Connector(InetAddress inetAddress, int port, int numberOfReconnectAttempts) {
        this.port = port;
        this.inetAddress = inetAddress;
        this.numberOfReconnectAttempts = numberOfReconnectAttempts;
    }

    public Socket getSocket() {
        Socket socket = null;
        int attemptNumber = 1;
        try {
            while (socket == null) {
                try {
                    socket = new Socket(inetAddress, port);
                } catch (ConnectException e) {
                    System.out.println("Server is not available ");
                    if (attemptNumber > numberOfReconnectAttempts) {
                        break;
                    } else {
                        System.out.printf("Trying to reconnect (%d/%d)\n", attemptNumber, numberOfReconnectAttempts);
                        attemptNumber++;
                    }
                }
            }
        } catch (UnknownHostException e) {
            System.out.println("Unknown InetAddress");
        } catch (IOException e) {
            System.out.println("Unexpected error with connection");
        }
        return socket;
    }

}
