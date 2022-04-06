package com.otto15.client;

import com.otto15.common.network.Request;
import com.otto15.common.network.Response;
import com.otto15.common.network.Serializer;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public final class ConnectionHandler {

    public static final int PORT = 2645;
    public static final int BUFFER_SIZE = 4096;
    private Socket socket;
    private boolean isOpen = false;

    public ConnectionHandler() {
    }

    public Socket getSocket() {
        return socket;
    }

    public void openConnection() throws IOException {
        try {
            socket = new Socket("localhost", PORT);
            isOpen = !isOpen;
        } catch (ConnectException e) {
            System.out.println("Server is not available.");
        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void close() throws IOException {
        socket.close();
    }


}


