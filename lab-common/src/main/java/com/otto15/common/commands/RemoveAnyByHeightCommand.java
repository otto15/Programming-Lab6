//package com.otto15.common.commands;
//
//import com.otto15.common.controllers.CommandManager;
//import com.otto15.common.entities.Person;
//
//public class RemoveAnyByHeightCommand extends AbstractCommand {
//
//    public RemoveAnyByHeightCommand() {
//        super("remove_any_by_height", "removes collection element by height", 1);
//    }
//
//    @Override
//    public boolean execute(Object[] args) {
//        try {
//            Person deletedPerson = CommandManager.getCollectionManager().removeAnyByHeight(Long.parseLong(args[0]));
//            if (deletedPerson == null) {
//                System.out.println("No person found with such height.");
//            } else {
//                System.out.println("Person[" + deletedPerson.getId() + "] " + deletedPerson.getName() + " was removed.");
//            }
//            return true;
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid format of height.");
//        }
//        return false;
//    }
//}
