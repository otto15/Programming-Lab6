package com.otto15.server.config;

import com.otto15.server.collection.CollectionManagerImpl;
import com.otto15.server.io.CollectionFileReader;
import com.otto15.server.io.CollectionFileWriter;
import com.otto15.server.io.xml.XmlCollectionFileOperator;

import java.io.File;

public class Config {

    public static final CollectionFileReader<CollectionManagerImpl> COLLECTION_FILE_READER = new XmlCollectionFileOperator();
    public static final CollectionFileWriter<CollectionManagerImpl> COLLECTION_FILE_WRITER = new XmlCollectionFileOperator();
    private static File inputFile;
    private static File outputFile;


    private Config() {

    }

    public static File getInputFile() {
        return inputFile;
    }

    public static File getOutputFile() {
        return outputFile;
    }

    public static boolean configure() {
        if (System.getenv("COLLECTION_FILE") == null) {
            System.out.println(("Set your collection file as a COLLECTION_FILE environment variable and restart the app."));
            return false;
        } else {
            inputFile = new File(System.getenv("COLLECTION_FILE"));
            outputFile = new File(System.getenv("COLLECTION_FILE"));
        }
        return true;
    }

}
