package com.otto15.client;


import java.io.IOException;

public final class Client {

    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) throws IOException {
        ConnectionHandler connectionHandler = new ConnectionHandler();
        connectionHandler.openConnection();
        if (connectionHandler.isOpen()) {
            CommandListener commandListener = new CommandListener();
            ClientListener clientListener = new ClientListener(connectionHandler, commandListener);
            System.out.println("Salamaleikum!");
            clientListener.listen();
        }
    }

}
