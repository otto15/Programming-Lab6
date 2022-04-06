package com.otto15.server.collection;

import com.otto15.common.controllers.CollectionManager;
import com.otto15.common.entities.Person;
import com.otto15.common.entities.validators.EntityValidator;
import com.otto15.server.io.CollectionFileReader;
import com.thoughtworks.xstream.converters.ConversionException;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Wrapper for HashSet to store the additional info.
 *
 * @author Rakhmatullin R.
 */
public class CollectionManagerImpl implements CollectionManager {
    private HashSet<Person> persons;
    private ZonedDateTime creationDate = ZonedDateTime.now();
    private Map<Long, List<Person>> groupsByHeight;

    public CollectionManagerImpl() {
        persons = new HashSet<>();
    }

    public HashSet<Person> getPersons() {
        return persons;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate() {
        creationDate = ZonedDateTime.now();
    }

    /**
     * Collection manager initialization from file
     * @param collectionFileReader
     * @param file
     * @return
     * @throws IOException
     */
    public static CollectionManagerImpl initFromFile(CollectionFileReader<CollectionManagerImpl> collectionFileReader, File file) throws IOException {
        try {
            CollectionManagerImpl collectionManager = collectionFileReader.read(file);
            collectionManager.setCreationDate();
            if (collectionManager.getPersons() == null) {
                collectionManager.persons = new HashSet<>();
            }
            collectionManager.setup();
            return collectionManager;
        } catch (ConversionException e) {
            throw new IOException("Objects in file are invalid.");
        } catch (IllegalArgumentException e) {
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public void add(Person newPerson) {
        persons.add(newPerson);
    }

    @Override
    public void clear() {
        persons.clear();
    }

    @Override
    public void show() {
        if (!persons.isEmpty()) {
            for (Person person : persons) {
                System.out.println(person);
            }
        } else {
            System.out.println("Collection is empty.");
        }
    }

    @Override
    public Person findById(Long id) {
        for (Person person : persons) {
            if (person.getId().equals(id)) {
                return person;
            }
        }
        throw new IllegalArgumentException("No person with such id.");
    }

    @Override
    public long getSumOfHeights() {
        long sumOfHeights = 0;
        for (Person singlePerson : persons) {
            sumOfHeights += singlePerson.getHeight();
        }
        return sumOfHeights;
    }

    @Override
    public List<String> getInfo() {
        List<String> info = new ArrayList<>();
        info.add(persons.getClass().getName());
        info.add(creationDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm")));
        info.add(String.valueOf(persons.size()));
        return info;
    }

    @Override
    public Person removeAnyByHeight(long height) {
        Person deletedPerson = null;
        for (Person singlePerson : persons) {
            if (singlePerson.getHeight() == height) {
                deletedPerson = singlePerson;
                persons.remove(singlePerson);
                break;
            }
        }
        return deletedPerson;
    }

    @Override
    public void makeGroupsByHeight() {
        groupsByHeight = new HashMap<>();
        for (Person person : persons) {
            if (groupsByHeight.containsKey(person.getHeight())) {
                groupsByHeight.get(person.getHeight()).add(person);
            } else {
                groupsByHeight.put(person.getHeight(), new ArrayList<>(Arrays.asList(person)));
            }
        }
    }

    @Override
    public void outputGroupsByHeight() {
        if (groupsByHeight.isEmpty()) {
            System.out.println("Collection is empty");
        } else {
            for (Map.Entry<Long, List<Person>> group : groupsByHeight.entrySet()) {
                System.out.println("Height - " + group.getKey() + ": " + group.getValue().size() + " members.");
            }
        }
    }

    /**
     * Set up collection manager
     */
    private void setup() {
        for (Person personInQuestion : persons) {
            if (EntityValidator.isEntityValid(personInQuestion)) {
                personInQuestion.setId();
            } else {
                throw new IllegalArgumentException("Person' field value is invalid.");
            }
        }
    }
}
