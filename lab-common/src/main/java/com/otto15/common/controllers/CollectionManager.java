package com.otto15.common.controllers;

import com.otto15.common.entities.Person;

import java.util.HashSet;
import java.util.List;

public interface CollectionManager {

    HashSet<Person> getPersons();

    static void initFrom() {

    }

    void add(Person newPerson);
    void clear();
    void show();
    Person findById(Long id);
    long getSumOfHeights();
    List<String> getInfo();
    Person removeAnyByHeight(long height);
    void makeGroupsByHeight();
    void outputGroupsByHeight();

}
