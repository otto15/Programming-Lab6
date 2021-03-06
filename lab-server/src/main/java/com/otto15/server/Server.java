package com.otto15.server;

import com.otto15.common.controllers.CollectionManager;
import com.otto15.common.controllers.CommandListener;
import com.otto15.common.controllers.CommandManager;
import com.otto15.server.collection.CollectionManagerImpl;
import com.otto15.server.config.IOConfig;

import java.io.IOException;

public final class Server {

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        if (IOConfig.configure()) {
            CollectionManager collectionManager = CollectionManagerImpl.initFromFile(IOConfig.COLLECTION_FILE_READER,
                    IOConfig.getInputFile());
            CommandManager.setCollectionManager(collectionManager);
            try {
                ConnectionHandler connectionHandler = new ConnectionHandler();
                Thread commandListenerThread = new Thread(new CommandListener());
                commandListenerThread.start();
                connectionHandler.run();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
