//package com.otto15.common.commands;
//
//import com.otto15.common.controllers.CommandManager;
//import com.otto15.common.entities.Person;
//import com.otto15.common.entities.PersonLoader;
//
//import java.io.IOException;
//
//public class RemoveGreaterCommand extends AbstractCommand {
//
//    public RemoveGreaterCommand() {
//        super("remove_greater", "remove all elements greater than given", 0);
//    }
//
//    @Override
//    public boolean execute(Object[] args) {
//        try {
//            Person comparedPerson = PersonLoader.loadPerson(getReader());
//            CommandManager.getCollectionManager().getPersons().removeIf(person -> person.compareTo(comparedPerson) > 0);
//            return true;
//        } catch (IOException e) {
//            System.out.println("Input error.");
//        }
//        return false;
//    }
//}
