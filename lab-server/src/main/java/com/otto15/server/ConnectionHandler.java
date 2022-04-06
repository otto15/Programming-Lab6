package com.otto15.server;


import com.otto15.common.network.Request;
import com.otto15.common.network.Response;
import com.otto15.common.network.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public final class ConnectionHandler {

    private static final int PORT = 2645;
    private static final int BUFFER_SIZE = 4096;
    private final Selector selector;
    private final ServerSocketChannel serverChannel;
    private final Map<SocketChannel, ByteBuffer> channels = new HashMap<>();


    public ConnectionHandler() throws IOException {
        selector = Selector.open();
        serverChannel = ServerSocketChannel.open();
        serverChannel.socket().bind(new InetSocketAddress(PORT));
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server launched!");
    }

    public void run() throws IOException {
        while (true) {
            SelectionKey key = null;
            try {
                selector.select();
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
            } catch (IOException e) {
                assert key != null;
                kill((SocketChannel) key.channel());
            }
        }

    }

    private void accept(SelectionKey key) throws IOException {
        SocketChannel channel = serverChannel.accept();
        System.out.println("New connection accepted " + channel.getRemoteAddress());
        channel.configureBlocking(false);
        channels.put(channel, ByteBuffer.allocate(BUFFER_SIZE));
        channel.register(selector, SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = channels.get(channel);

        int bytesRead = channel.read(buffer);
        if (bytesRead == -1) {
            kill(channel);
            return;
        }

        Request request = (Request) Serializer.deserialize(buffer.array());
        System.out.println("got: " + request);
        buffer.clear();
        assert request != null;
        buffer.put(ByteBuffer.wrap(request.toString().getBytes(StandardCharsets.UTF_8)));
        buffer.flip();

        channel.register(selector, SelectionKey.OP_WRITE);
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = channels.get(channel);
        System.out.println("write");
        String clientMessage = new String(buffer.array(), buffer.position(), buffer.limit());
        Response response = new Response(clientMessage);

        buffer.clear();
        buffer.put(ByteBuffer.wrap(Serializer.serialize(response)));
        buffer.flip();
        int bytesWritten = channel.write(buffer);
        if (!buffer.hasRemaining()) {
            buffer.compact();
            channel.register(selector, SelectionKey.OP_READ);
        }
    }

    private void kill(SocketChannel channel) throws IOException {
        System.out.println("Connection closed " + channel.getRemoteAddress());
        channels.remove(channel);
        channel.close();
    }
}
