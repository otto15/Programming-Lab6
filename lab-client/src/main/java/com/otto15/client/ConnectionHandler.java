package com.otto15.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;

public final class ConnectionHandler {

    public static final int PORT = 2645;
    public static final int BUFFER_SIZE = 4096;
    private Socket socket;
    private boolean isOpen = false;
    private InputStream inputStream;
    private OutputStream outputStream;


    public ConnectionHandler() {
    }

    public Socket getSocket() {
        return socket;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void openConnection() throws IOException {
        try {
            socket = new Socket("localhost", PORT);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            isOpen = !isOpen;
        } catch (ConnectException e) {
            System.out.println("Server is not available.");
        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}


