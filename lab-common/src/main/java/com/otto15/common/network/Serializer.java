package com.otto15.common.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public final class Serializer {

    private static final int BUFFER_SIZE = 4096;

    private Serializer() {

    }

    public static byte[] serialize(Serializable objectToSerialize) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(BUFFER_SIZE);
        ObjectOutput objectOutput = new ObjectOutputStream(byteArrayOutputStream);
        objectOutput.writeObject(objectToSerialize);

        return byteArrayOutputStream.toByteArray();
    }

    public static Serializable deserialize(byte[] serializedObject) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializedObject);
        ObjectInputStream objectInput = new ObjectInputStream(byteArrayInputStream);
        try {
            return (Serializable) objectInput.readObject();
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

}
