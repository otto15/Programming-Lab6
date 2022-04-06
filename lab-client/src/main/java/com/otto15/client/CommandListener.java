package com.otto15.client;


import com.otto15.client.exceptions.LostConnectionException;
import com.otto15.common.controllers.CommandManager;
import com.otto15.common.network.Request;
import com.otto15.common.network.Response;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Listens to stream for commands
 *
 * @author Rakhmatullin R.
 */
public class CommandListener {

    private static boolean performanceStatus = true;
    private final Reader reader;


    public CommandListener() {
        reader = new InputStreamReader(System.in);
    }

    public static boolean getPerformanceStatus() {
        return performanceStatus;
    }

    public static void switchPerformanceStatus() {
        performanceStatus = !performanceStatus;
    }

    public void setConnectionHandler() {

    }

    public Request listen(BufferedReader in) throws IOException {

        if (!(reader.getClass() == FileReader.class)) {
            System.out.println("===========================");
        }
        String input = in.readLine();
        if (input == null) {
            ClientListener.switchPerformanceStatus();
            return null;
        }
        if (!"".equals(input)) {
            return CommandManager.onCommandReceived(input);
        }

        return null;
    }

}
