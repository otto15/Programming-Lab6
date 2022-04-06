package com.otto15.common.commands;

import com.otto15.common.controllers.CommandManager;
import com.otto15.common.entities.Person;
import com.otto15.common.entities.PersonLoader;

import java.io.IOException;


/**
 * Command for adding new elements to collection
 */
public class AddCommand extends AbstractCommand {

    public AddCommand() {
        super("add", "adds element to collection", 0);
    }

    @Override
    public Object[] readArgs(Object[] args) {
        try {
            Person personToAdd = PersonLoader.loadPerson();
            return new Object[]{personToAdd};
        } catch (IOException e) {
            System.out.println("Input error.");
            return null;
        }
    }

    @Override
    public String execute(Object[] args) {
        Person personToAdd = (Person) args[0];
        CommandManager.getCollectionManager().add(personToAdd);
        return "New person successfully created!";
    }
}
