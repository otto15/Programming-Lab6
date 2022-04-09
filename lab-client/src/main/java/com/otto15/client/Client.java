package com.otto15.client;

import com.otto15.common.controllers.CommandListener;
import com.otto15.common.controllers.CommandManager;

import java.io.IOException;

public final class Client {

    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) throws IOException {
        ConnectionHandler connectionHandler = new ConnectionHandler();
        connectionHandler.openConnection();
        if (connectionHandler.isOpen()) {
            ClientNetworkListener clientListener = new ClientNetworkListener(connectionHandler);
            CommandManager.setNetworkListener(clientListener);
            CommandListener commandListener = new CommandListener();
            CommandListener.setOnClient();
            System.out.println("Salamaleikum!");
            commandListener.run();
        }
    }

}
