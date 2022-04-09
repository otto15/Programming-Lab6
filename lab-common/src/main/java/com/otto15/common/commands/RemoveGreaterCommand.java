package com.otto15.common.commands;

import com.otto15.common.controllers.CommandManager;
import com.otto15.common.entities.Person;
import com.otto15.common.entities.PersonLoader;

import java.io.IOException;

public class RemoveGreaterCommand extends AbstractCommand {

    public RemoveGreaterCommand() {
        super("remove_greater", "remove all elements greater than given", 0);
    }

    @Override
    public Object[] readArgs(Object[] args) {
        try {
            Person comparedPerson = PersonLoader.loadPerson();
            return new Object[]{comparedPerson};
        } catch (IOException e) {
            System.out.println("Input error.");
        }
        return null;
    }

    @Override
    public String execute(Object[] args) {
        int collectionLen = CommandManager.getCollectionManager().getPersons().size();
        CommandManager.getCollectionManager().getPersons().removeIf(person -> person.compareTo((Person) args[0]) > 0);
        return (collectionLen - CommandManager.getCollectionManager().getPersons().size()) + " object(s) was deleted.";
    }
}
