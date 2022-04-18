package com.otto15.server.collection;

import com.otto15.common.controllers.CollectionManager;
import com.otto15.common.entities.Person;
import com.otto15.common.entities.validators.EntityValidator;
import com.otto15.common.io.CollectionFileReader;
import com.otto15.server.config.IOConfig;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.io.StreamException;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Wrapper for HashSet to store the additional info.
 *
 * @author Rakhmatullin R.
 */
public class CollectionManagerImpl implements CollectionManager {
    private Long currentID = 1L; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private HashSet<Person> persons = new HashSet<>();
    private ZonedDateTime creationDate = ZonedDateTime.now();
    private Map<Long, List<Person>> groupsByHeight;

    public CollectionManagerImpl() {
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
     *
     * @param collectionFileReader
     * @param file
     * @return
     * @throws IOException
     */
    public static CollectionManagerImpl initFromFile(CollectionFileReader<CollectionManagerImpl> collectionFileReader, File file) {
        try {
            CollectionManagerImpl collectionManager = collectionFileReader.read(file);
            collectionManager.currentID = 1L;
            collectionManager.setCreationDate();
            if (collectionManager.getPersons() == null) {
                collectionManager.persons = new HashSet<>();
            }
            collectionManager.setup();
            return collectionManager;
        } catch (ConversionException e) {
            System.out.println("Objects in file are invalid.");
        } catch (IllegalArgumentException e) {
            System.out.println((e.getMessage()));
        } catch (IOException e) {
            System.out.println("Input/Output problems.");
        } catch (StreamException e) {
            System.out.println("Invalid file.");
        }
        System.out.println("You will work with empty collection via problems with file.");
        return new CollectionManagerImpl();
    }

    @Override
    public void add(Person newPerson) {
        newPerson.setId(currentID++);
        persons.add(newPerson);
    }

    @Override
    public void clear() {
        persons.clear();
    }

    @Override
    public Collection<Person> show() {
        return persons.stream()
                .sorted((person1, person2) -> person1.getName().compareToIgnoreCase(person2.getName()))
                .collect(Collectors.toList());

    }

    @Override
    public Person findById(Long id) {
        Person foundPerson = persons.stream().filter(person -> Objects.equals(person.getId(), id)).findFirst().orElse(new Person("", null, 1, null, null, null, null));
        if (foundPerson.getId() == null) {
            foundPerson.setId(-1L);
        }
        return foundPerson;
    }

    @Override
    public Person findAnyByHeight(long height) {
        Person foundPerson = persons.stream().filter(person -> Objects.equals(person.getHeight(), height)).findFirst().orElse(new Person("", null, 1, null, null, null, null));
        if (foundPerson.getId() == null) {
            foundPerson.setId(-1L);
        }
        return foundPerson;
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
        Person personToDelete = findAnyByHeight(height);
        if (personToDelete.getId() != -1) {
            persons.remove(personToDelete);
        }
        return personToDelete;
    }

    @Override
    public void makeGroupsByHeight() {
        groupsByHeight = persons.stream().collect(Collectors.groupingBy(Person::getHeight));
    }

    @Override
    public String outputGroupsByHeight() {
        if (groupsByHeight.isEmpty()) {
            return "Collection is empty";
        }

        return groupsByHeight.keySet()
                .stream()
                .map((key) -> "Height - " + key + ": " + groupsByHeight.get(key).size() + " member(s).")
                .collect(Collectors.joining("\n"));

    }

    @Override
    public void update(Person updatedPerson) {
        removeById(updatedPerson.getId());
        persons.add(updatedPerson);
    }

    @Override
    public boolean removeById(Long id) {
        Person personToRemove = findById(id);
        if (personToRemove.getId() == -1) {
            return false;
        }
        persons.remove(personToRemove);
        return true;
    }

    @Override
    public void write() throws IOException {
        IOConfig.COLLECTION_FILE_WRITER.write(IOConfig.getOutputFile(), this);
    }

    /**
     * Set up collection manager
     */
    private void setup() {
        for (Person personInQuestion : persons) {
            if (EntityValidator.isEntityValid(personInQuestion)) {
                personInQuestion.setId(currentID++);
            } else {
                throw new IllegalArgumentException("Person' field value is invalid.");
            }
        }
    }
}


