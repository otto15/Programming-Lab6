package com.otto15.common.commands;

import com.otto15.common.controllers.CommandManager;
import com.otto15.common.network.Response;

import java.util.stream.Collectors;

/**
 * Command for showing the commands info
 */
public class HelpCommand extends AbstractCommand {

    public HelpCommand() {
        super("help", "shows the list of commands", 0);
    }

    @Override
    public Object[] readArgs(Object[] args) {
        return new Object[0];
    }

    @Override
    public Response execute(Object[] args) {
        return new Response(CommandManager.getCommands()
                .values()
                .stream()
                .map(value -> value.getName() + " - " + value.getDescription() + ", implies " + value.getInlineArgsCount() + " argument(s)")
                .collect(Collectors.joining("\n")));
    }
}
