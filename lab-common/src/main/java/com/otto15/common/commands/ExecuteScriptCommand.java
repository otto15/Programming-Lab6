//package com.otto15.common.commands;
//
//import com.otto15.common.controllers.CommandListener;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * Command for executing script
// */
//public class ExecuteScriptCommand extends AbstractCommand {
//
//    private static final Set<String> FILE_HISTORY = new HashSet<>();
//
//    public ExecuteScriptCommand() {
//        super("execute_script", "executes the script with commands", 1);
//    }
//
//    @Override
//    public boolean execute(String[] args) {
//        String fileName = args[0];
//        if (FILE_HISTORY.contains(fileName)) {
//            System.out.println("There is a problem: script will loop.");
//            return false;
//        } else {
//            try {
//                CommandListener listenerFromFile = new CommandListener(new FileReader(fileName));
//                FILE_HISTORY.add(fileName);
//                listenerFromFile.run();
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//                return false;
//            }
//            FILE_HISTORY.remove(fileName);
//        }
//        return true;
//    }
//}
