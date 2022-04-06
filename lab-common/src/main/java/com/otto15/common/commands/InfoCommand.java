//package com.otto15.common.commands;
//
//import com.otto15.common.controllers.CommandManager;
//
//public class InfoCommand extends AbstractCommand {
//
//    public InfoCommand() {
//        super("info", "shows the info about collection", 0);
//    }
//
//    @Override
//    public boolean execute(Object[] args) {
//        System.out.println("Collection info:");
//        System.out.println(String.join("\n", CommandManager.getCollectionManager().getInfo()));
//        return true;
//    }
//}
