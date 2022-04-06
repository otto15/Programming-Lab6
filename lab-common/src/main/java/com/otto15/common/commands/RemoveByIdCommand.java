//package com.otto15.common.commands;
//
//
//import com.otto15.common.controllers.CommandManager;
//
//public class RemoveByIdCommand extends AbstractCommand {
//
//    public RemoveByIdCommand() {
//        super("remove_by_id", "removes collection element by id", 1);
//    }
//
//    @Override
//    public boolean execute(Object[] args) {
//        try {
//            CommandManager.getCollectionManager().getPersons().remove(CommandManager.getCollectionManager().findById(Long.parseLong(args[0])));
//            return true;
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid format of id.");
//        } catch (IllegalArgumentException e) {
//            System.out.println(e.getMessage());
//        }
//        return false;
//    }
//}
