package com.otto15.client;

import com.otto15.common.network.Request;
import com.otto15.common.network.Response;
import com.otto15.common.network.Serializer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ClientDispatcher {


    private ClientDispatcher() {
    }

    public static void send(Request request, OutputStream outputStream) throws IOException {
        byte[] bytes = Serializer.serialize(request);
        outputStream.write(bytes);
        System.out.println(Arrays.toString(bytes));
    }

    public static Response receive(InputStream inputStream, int bufferSize) throws IOException {
        ByteBuffer mainBuffer = ByteBuffer.allocate(0);
        while (true) {
            byte[] bytesToDeserialize = new byte[bufferSize];
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            int bytesCount = bis.read(bytesToDeserialize);
            System.out.println(bytesCount);
            ByteBuffer newBuffer = ByteBuffer.allocate(mainBuffer.capacity() + bytesCount);
            newBuffer.put(mainBuffer);
            newBuffer.put(ByteBuffer.wrap(bytesToDeserialize, 0, bytesCount));
            mainBuffer = ByteBuffer.wrap(newBuffer.array());
            try {
                return (Response) Serializer.deserialize(mainBuffer.array());
            } catch (IOException e) {
                List<ByteBuffer> list = new ArrayList<>();
                int bytesLeft = bis.available();
                int len = bytesLeft;
                while (bytesLeft > 0) {
                    byte[] leftBytesToSerialize = new byte[bytesLeft];
                    System.out.println(bis.read(leftBytesToSerialize));
                    list.add(ByteBuffer.wrap(leftBytesToSerialize));
                    bytesLeft = bis.available();
                    len += bytesLeft;
                }
                newBuffer = ByteBuffer.allocate(len + mainBuffer.capacity());
                newBuffer.put(mainBuffer);
                list.forEach(newBuffer::put);
                mainBuffer = ByteBuffer.wrap(newBuffer.array());
                return (Response) Serializer.deserialize(mainBuffer.array());
            }
        }
    }

}
