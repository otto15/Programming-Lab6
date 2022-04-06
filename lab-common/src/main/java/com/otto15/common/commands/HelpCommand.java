//package com.otto15.common.commands;
//
//import com.otto15.common.controllers.CommandManager;
//
//import java.util.Map;
//
///**
// * Command for showing the commands info
// */
//public class HelpCommand extends AbstractCommand {
//
//    public HelpCommand() {
//        super("help", "shows the list of commands", 0);
//    }
//
//    @Override
//    public boolean execute(Object[] args) {
//        for (Map.Entry<String, AbstractCommand> command : CommandManager.getCommands().entrySet()) {
//            System.out.println(command.getValue().getName() + " - " + command.getValue().getDescription() + ", implies " + command.getValue().getInlineArgsCount() + " argument(s)");
//        }
//        return true;
//    }
//}
