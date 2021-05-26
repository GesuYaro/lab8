package client;

import client.exceptions.NotAResponseException;
import musicband.MusicBand;
import network.Request;
import network.Response;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Console {

    private BufferedReader bufferedReader;
    private RequestFabric requestFabric;
    private ClientExecuteScriptCommand clientExecuteScriptCommand;
    private int port;
    private String hostAddress;

    public Console(RequestFabric requestFabric, BufferedReader bufferedReader, ClientExecuteScriptCommand clientExecuteScriptCommand, int port, String hostAddress) {
        this.requestFabric = requestFabric;
        this.bufferedReader = bufferedReader;
        this.clientExecuteScriptCommand = clientExecuteScriptCommand;
        this.port = port;
        this.hostAddress = hostAddress;
    }

    public void run() {
        while (true) {
            Connector connector = null;
            Socket socket = null;
            try {

                String userMessage = bufferedReader.readLine();
                if (userMessage != null && !userMessage.trim().equals("")) {
                    String[] command = userMessage.trim().split(" ", 2);
                    command[0] = command[0].trim();
                    if (command.length > 1) {
                        command[1] = command[1].trim();
                    }
                    if (command[0].equals("exit")) {
                        break;
                    }
                    if (command[0].equals("execute_script")) {
                        if (command.length > 1) {
                            clientExecuteScriptCommand.execute(command[1], null);
                        } else {
                            System.out.println("Argument not found");
                        }
                    } else {
                        Request request = requestFabric.createRequest(command[0], command.length > 1 ? command[1] : "");
                        try {
                            connector = new Connector(InetAddress.getByName(hostAddress), port);
                        } catch (UnknownHostException e) {
                            System.out.println("Unknown host");
                        }
                        if (connector != null) {
                            socket = connector.getSocket();
                        }
                        if (socket != null) {
                            RequestWriter requestWriter = new RequestWriter(socket.getOutputStream(), new ByteArrayOutputStream());
                            requestWriter.sendRequest(request);
                            try {
                                ResponseReader responseReader = new ResponseReader(socket.getInputStream());
                                Response response = responseReader.readResponse();
                                System.out.println(response.getMessage());
                                if (!response.getList().isEmpty()) {
                                    for (MusicBand mb : response.getList()) {
                                        System.out.println(mb.toString());
                                    }
                                }
                            } catch (ClassNotFoundException | NotAResponseException e) {
                                System.out.println("Problem with the response from server");
                            }
                            socket.close();
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Connection refused");
                e.printStackTrace();
            }
        }
    }

}
