package com.otto15.server;

import com.otto15.common.controllers.CollectionManager;
import com.otto15.server.collection.CollectionManagerImpl;
import com.otto15.server.config.Config;

import java.io.IOException;

public final class Server {

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        if (Config.configure()) {
            try {
                CollectionManager collectionManager = CollectionManagerImpl.initFromFile(Config.COLLECTION_FILE_READER,
                        Config.getInputFile());
                ConnectionHandler connectionHandler = new ConnectionHandler();
                connectionHandler.run();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
