//package com.otto15.common.commands;
//
//
//import com.otto15.common.controllers.CommandManager;
//
///**
// * Command for clearing the collection
// */
//public class ClearCommand extends AbstractCommand {
//
//    public ClearCommand() {
//        super("clear", "deletes all collection elements", 0);
//    }
//
//    @Override
//    public boolean execute(Object[] args) {
//        CommandManager.getCollectionManager().clear();
//        return true;
//    }
//
//}
