package com.otto15.client;

import com.otto15.common.controllers.CommandListener;
import com.otto15.common.controllers.CommandManager;


public final class Client {

    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        ConnectionHandler connectionHandler = new ConnectionHandler();
        ClientNetworkListener clientListener = new ClientNetworkListener(connectionHandler);
        CommandManager.setNetworkListener(clientListener);
        CommandListener commandListener = new CommandListener();
        CommandListener.setOnClient();
        System.out.println("Salamaleikum!");
        connectionHandler.openConnection();
        commandListener.run();
    }

}
