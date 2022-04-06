package com.otto15.client;


import com.otto15.common.network.Request;
import com.otto15.common.network.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.SocketTimeoutException;

public final class ClientListener {

    private static final int TIMEOUT = 5000;
    private static boolean performanceStatus = true;
    private ConnectionHandler connectionHandler;
    private CommandListener commandListener;
    private final Reader reader = new InputStreamReader(System.in);

    public ClientListener(ConnectionHandler connectionHandler, CommandListener commandListener) {
        this.connectionHandler = connectionHandler;
        this.commandListener = commandListener;
    }

    public void listen() {
        try (
                InputStream inputStream = connectionHandler.getSocket().getInputStream();
                OutputStream outputStream = connectionHandler.getSocket().getOutputStream();
                BufferedReader in = new BufferedReader(reader);
        ) {
            while (performanceStatus) {
                Request request = commandListener.listen(in);
                if (request != null) {
                    ClientDispatcher.send(request, outputStream);
                    connectionHandler.getSocket().setSoTimeout(TIMEOUT);
                    Response response = ClientDispatcher.receive(inputStream);
                    System.out.println(response);
                }
            }
            connectionHandler.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void switchPerformanceStatus() {
        performanceStatus = !performanceStatus;
    }

}
