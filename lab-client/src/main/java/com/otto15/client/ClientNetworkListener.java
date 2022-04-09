package com.otto15.client;


import com.otto15.common.state.State;
import com.otto15.common.network.NetworkListener;
import com.otto15.common.network.Request;
import com.otto15.common.network.Response;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public final class ClientNetworkListener implements NetworkListener {

    private static final int TIMEOUT = 5000;
    private final ConnectionHandler connectionHandler;
    private final Reader reader = new InputStreamReader(System.in);

    public ClientNetworkListener(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    @Override
    public Response listen(Request request) {
        Response response = null;
        try {
            ClientDispatcher.send(request, connectionHandler.getOutputStream());
            connectionHandler.getSocket().setSoTimeout(TIMEOUT);
            response = ClientDispatcher.receive(connectionHandler.getInputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            connectionHandler.close();
            System.out.println();
            State.switchPerformanceStatus();
        }
        return response;
    }

}
