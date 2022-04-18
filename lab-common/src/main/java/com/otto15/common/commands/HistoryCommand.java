package com.otto15.common.commands;

import com.otto15.common.controllers.CommandManager;
import com.otto15.common.network.Response;

import java.util.Queue;
import java.util.stream.Collectors;

public class HistoryCommand extends AbstractCommand {

    public HistoryCommand() {
        super("history", "outputs last 10 commands", 0);
    }

    @Override
    public Object[] readArgs(Object[] args) {
        return new Object[0];
    }

    @Override
    public Response execute(Object[] args) {
        if (CommandManager.getCommandHistory().size() == 0) {
            return new Response("History is empty.");
        } else {
            Queue<String> history = CommandManager.getCommandHistory();
            return new Response(history.stream().
                    limit(history.size() - 1).
                    collect(Collectors.joining("\n")));
        }
    }
}
