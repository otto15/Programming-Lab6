package com.otto15.client;

import com.otto15.client.exceptions.LostConnectionException;
import com.otto15.common.network.Request;
import com.otto15.common.network.Response;
import com.otto15.common.network.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class ClientDispatcher {

    private ClientDispatcher() {
    }

    public static void send(Request request, OutputStream outputStream) throws IOException {
        byte[] bytes = Serializer.serialize(request);
        outputStream.write(bytes);
    }

    public static Response receive(InputStream inputStream) throws IOException {
        byte[] bytesToDeserialize = new byte[ConnectionHandler.BUFFER_SIZE];
        inputStream.read(bytesToDeserialize);
        return (Response) Serializer.deserialize(bytesToDeserialize);
    }

}
