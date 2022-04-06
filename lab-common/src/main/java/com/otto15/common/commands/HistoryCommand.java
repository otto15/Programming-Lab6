//package com.otto15.common.commands;
//
//import com.otto15.common.controllers.CommandManager;
//
//public class HistoryCommand extends AbstractCommand {
//
//    public HistoryCommand() {
//        super("history", "outputs last 10 commands", 0);
//    }
//
//    @Override
//    public boolean execute(Object[] args) {
//        if (CommandManager.getCommandHistory().size() == 0) {
//            System.out.println("History is empty.");
//        } else {
//            CommandManager.getCommandHistory().forEach(System.out::println);
//        }
//        return true;
//    }
//}
