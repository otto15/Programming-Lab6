package com.otto15.server;


import com.otto15.common.controllers.CommandManager;
import com.otto15.common.network.Request;
import com.otto15.common.network.Response;
import com.otto15.common.network.Serializer;
import com.otto15.common.state.State;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;


public final class ConnectionHandler implements Runnable {

    private static final int PORT = 29876;
    private static final int SELECT_DELAY = 1000;
    private final Map<SocketChannel, ByteBuffer> channels = new HashMap<>();

    private final Selector selector;
    private final ServerSocketChannel serverChannel;

    public ConnectionHandler() throws IOException {
        selector = Selector.open();
        serverChannel = ServerSocketChannel.open();
        while (true) {
            try {
                serverChannel.socket().bind(new InetSocketAddress(inputPort()));
                break;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Port must be in the range from 1 to 65535");
            }
        }
    }

    private int inputPort() {
        while (true) {
            try {
                System.out.println("Enter port:");
                Scanner sc = new Scanner(System.in);
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Enter number");
            }
        }
    }

    public void run() {
        try {
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            listen();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            close();
        }
    }

    private void close() {
        try {
            selector.close();
            serverChannel.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void listen() throws IOException {
        while (State.getPerformanceStatus()) {
            SelectionKey key = null;
            try {
                int numberOfKeys = selector.select(SELECT_DELAY);
                if (numberOfKeys != 0) {
                    Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                    while (keys.hasNext()) {
                        key = keys.next();
                        keys.remove();
                        if (key.isValid()) {
                            if (key.isAcceptable()) {
                                accept(key);
                            } else if (key.isReadable()) {
                                read(key);
                            } else if (key.isWritable()) {
                                write(key);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                if (key != null) {
                    kill((SocketChannel) key.channel());
                }
            }
        }
    }

    private SocketAddress accept(SelectionKey key) throws IOException {
        SocketChannel channel = serverChannel.accept();
        SocketAddress address = channel.getRemoteAddress();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
        channels.put(channel, ByteBuffer.allocate(0));
        return address;
    }

    private Request read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead = channel.read(buffer);
        System.out.println(bytesRead);
        if (bytesRead == -1) {
            kill(channel);
            return null;
        }
        //((Buffer) buffer).flip();
        ByteBuffer newBuffer = ByteBuffer.allocate(channels.get(channel).capacity() + bytesRead);
        newBuffer.put(channels.get(channel).array());
        System.out.println(Arrays.toString(newBuffer.array()));
        newBuffer.put(ByteBuffer.wrap(buffer.array(), 0, bytesRead));
        System.out.println(Arrays.toString(newBuffer.array()));
        channels.put(channel, newBuffer);
        Request request = null;

        try {
            request = (Request) Serializer.deserialize(channels.get(channel).array());
            System.out.println(request);
        } catch (IOException e) {
            System.out.println("continue to read");
        }

        //buffer.clear();
        if (request != null) {
//            buffer.put(ByteBuffer.wrap(
//                    CommandManager.executeCommand(request.getCommand(), request.getArgs()).getBytes(StandardCharsets.UTF_8)
//            ));

            channels.put(channel, ByteBuffer.wrap(CommandManager.executeCommand(request.getCommand(), request.getArgs()).getBytes(StandardCharsets.UTF_8)));

//            buffer.flip();
            channel.register(selector, SelectionKey.OP_WRITE);
        }
        return request;
    }

    private Response write(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = channels.get(channel);
        String clientMessage = new String(buffer.array());
        Response response = new Response(clientMessage);
        ((Buffer) buffer).clear();
        buffer = ByteBuffer.wrap(Serializer.serialize(response));
        int bytesWritten = channel.write(buffer);
        System.out.println("sent " + bytesWritten);
        while (buffer.hasRemaining()) {
            bytesWritten = channel.write(buffer);
            System.out.println("sent " + bytesWritten);
        }
        //System.out.println(response);
        channels.put(channel, ByteBuffer.allocate(0));
        channel.register(selector, SelectionKey.OP_READ);
        return response;
    }

    private SocketAddress kill(SocketChannel channel) throws IOException {
        SocketAddress address = channel.getRemoteAddress();
        channels.remove(channel);
        channel.close();
        return address;
    }
}
