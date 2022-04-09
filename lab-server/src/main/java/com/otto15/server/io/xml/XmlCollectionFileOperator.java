package com.otto15.server.io.xml;

import com.otto15.server.collection.CollectionManagerImpl;
import com.otto15.common.io.CollectionFileReader;
import com.otto15.common.io.CollectionFileWriter;
import com.otto15.common.utils.FileStreamToStringConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.otto15.common.entities.Person;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * XML file worker
 * <p>
 * Reads and writes collection xml data
 * @author Rakhmatullin R.
 */
public class XmlCollectionFileOperator implements CollectionFileReader<CollectionManagerImpl>, CollectionFileWriter<CollectionManagerImpl> {
    @Override
    public CollectionManagerImpl read(File file) throws IOException, ConversionException {
        XStream xStream = new XStream();
        xStream.addPermission(AnyTypePermission.ANY);
        xStream.alias("person", Person.class);
        xStream.alias("persons", CollectionManagerImpl.class);
        xStream.omitField(CollectionManagerImpl.class, "creationDate");
        xStream.omitField(Person.class, "id");
        xStream.omitField(CollectionManagerImpl.class, "groupsByHeight");
        xStream.addImplicitCollection(CollectionManagerImpl.class, "persons");
        String xml = FileStreamToStringConverter.fileStreamToString(file);
        return (CollectionManagerImpl) xStream.fromXML(xml);
    }

    @Override
    public void write(File file, CollectionManagerImpl persons) throws IOException {
        XStream xStream = new XStream();
        xStream.alias("person", Person.class);
        xStream.alias("persons", CollectionManagerImpl.class);
        xStream.addImplicitCollection(CollectionManagerImpl.class, "persons");
        xStream.omitField(CollectionManagerImpl.class, "creationDate");
        xStream.omitField(Person.class, "id");
        xStream.omitField(CollectionManagerImpl.class, "groupsByHeight");
        String xml = xStream.toXML(persons);
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        printWriter.print(xml);
        printWriter.close();
    }
}
