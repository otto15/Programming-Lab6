package com.otto15.common.controllers;

import com.otto15.common.commands.AbstractCommand;
import com.otto15.common.commands.AddCommand;
//import com.otto15.common.commands.AddIfMinCommand;
//import com.otto15.common.commands.ClearCommand;
//import com.otto15.common.commands.ExecuteScriptCommand;
//import com.otto15.common.commands.ExitCommand;
//import com.otto15.common.commands.GroupCountingByHeightCommand;
//import com.otto15.common.commands.HelpCommand;
//import com.otto15.common.commands.HistoryCommand;
//import com.otto15.common.commands.InfoCommand;
//import com.otto15.common.commands.RemoveAnyByHeightCommand;
//import com.otto15.common.commands.RemoveByIdCommand;
//import com.otto15.common.commands.RemoveGreaterCommand;
//import com.otto15.common.commands.SaveCommand;
//import com.otto15.common.commands.ShowCommand;
//import com.otto15.common.commands.SumOfHeightCommand;
//import com.otto15.common.commands.UpdateCommand;
import com.otto15.common.network.Request;
import com.otto15.common.utils.DataNormalizer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;

/**
 * Manager for commands, responsible for invoking.
 */
public final class CommandManager {

    private static final int HISTORY_LENGTH = 10;
    private static final Queue<String> COMMAND_HISTORY = new LinkedList<>();
    private static final Map<String, AbstractCommand> COMMANDS = new HashMap<>();
    private static CollectionManager collectionManager;

    private CommandManager() {

    }

    static {
        COMMANDS.put("add", new AddCommand());
//        COMMANDS.put("add_if_min", new AddIfMinCommand());
//        COMMANDS.put("clear", new ClearCommand());
//        COMMANDS.put("exit", new ExitCommand());
//        COMMANDS.put("history", new HistoryCommand());
//        COMMANDS.put("info", new InfoCommand());
//        COMMANDS.put("remove_any_by_height", new RemoveAnyByHeightCommand());
//        COMMANDS.put("remove_by_id", new RemoveByIdCommand());
//        COMMANDS.put("remove_greater", new RemoveGreaterCommand());
//        COMMANDS.put("save", new SaveCommand());
//        COMMANDS.put("sum_of_height", new SumOfHeightCommand());
//        COMMANDS.put("update", new UpdateCommand());
//        COMMANDS.put("group_counting_by_height", new GroupCountingByHeightCommand());
//        COMMANDS.put("execute_script", new ExecuteScriptCommand());
//        COMMANDS.put("help", new HelpCommand());
//        COMMANDS.put("show", new ShowCommand());
    }

    public static void setCollectionManager(CollectionManager collectionManager) {
        CommandManager.collectionManager = collectionManager;
    }

    public static CollectionManager getCollectionManager() {
        return collectionManager;
    }

    public static Map<String, AbstractCommand> getCommands() {
        return COMMANDS;
    }

    public static Queue<String> getCommandHistory() {
        return COMMAND_HISTORY;
    }

    public static void addCommandToHistory(String commandName) {
        COMMAND_HISTORY.add(commandName);
        if (COMMAND_HISTORY.size() > HISTORY_LENGTH) {
            COMMAND_HISTORY.poll();
        }
    }

    /**
     * Normalize the data and pass it to executeCommand()
     *
     * @param inputData data from listener
     */
    public static Request onCommandReceived(String inputData) {
        String[] commandWithRawArgs = DataNormalizer.normalize(inputData);
        String commandName = commandWithRawArgs[0].toLowerCase(Locale.ROOT);
        String[] rawArgs = Arrays.copyOfRange(commandWithRawArgs, 1, commandWithRawArgs.length);
        if (COMMANDS.containsKey(commandName)) {
            AbstractCommand command = COMMANDS.get(commandName);
            if (rawArgs.length == command.getInlineArgsCount()) {
                Object[] commandArgs = command.readArgs(rawArgs);
                return new Request(command, commandArgs);
            } else {
                System.out.println("Wrong number of arguments.");
            }
        } else {
            System.out.println("No such command, call \"help\" to see the list of commands.");
        }
        return null;
    }

    public static String executeCommand(AbstractCommand command, Object[] args) {
        CommandManager.addCommandToHistory(command.getName());
        return command.execute(args);
    }

}
