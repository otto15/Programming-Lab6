package com.otto15.common.commands;

import com.otto15.common.controllers.CommandManager;
import com.otto15.common.entities.Person;
import com.otto15.common.entities.PersonLoader;

import java.io.IOException;
import java.util.Collections;


/**
 * Command for adding new element to collection it is minimal
 */
public class AddIfMinCommand extends AbstractCommand {

    public AddIfMinCommand() {
        super("add_if_min", "adds new person if minimal value", 0);
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

        Person newPerson = (Person) args[0];
        if (newPerson.compareTo(Collections.min(CommandManager.getCollectionManager().getPersons())) < 0) {
            CommandManager.getCollectionManager().add(newPerson);
            return "New person successfully created!";
        } else {
            return "Given person is not minimal.";
        }

    }
}
