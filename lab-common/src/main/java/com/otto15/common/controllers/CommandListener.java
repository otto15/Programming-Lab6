package com.otto15.common.controllers;


import com.otto15.common.network.Response;
import com.otto15.common.state.State;

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
public class CommandListener implements Runnable {

    private static boolean onClient;
    private final Reader reader;

    public CommandListener(Reader reader) {
        this.reader = reader;
    }

    public CommandListener() {
        this(new InputStreamReader(System.in));
    }

    public static boolean isOnClient() {
        return onClient;
    }

    public static void setOnClient() {
        CommandListener.onClient = true;
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(reader)) {
            while (State.getPerformanceStatus()) {
                if (!(reader.getClass() == FileReader.class)) {
                    System.out.println("===========================");
                }
                String input = in.readLine();
                if (input == null) {
                    break;
                }
                if (!"".equals(input)) {
                    Response response = CommandManager.onCommandReceived(input);
                    if (response != null) {
                        response.showResult();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Invalid output.");
        }

    }

}
