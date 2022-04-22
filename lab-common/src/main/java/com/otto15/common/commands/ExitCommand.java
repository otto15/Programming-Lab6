package com.otto15.common.commands;


import com.otto15.common.controllers.CommandListener;
import com.otto15.common.controllers.CommandManager;
import com.otto15.common.network.Response;
import com.otto15.common.state.State;

import java.io.IOException;

/**
 * Command for exit
 */
public class ExitCommand extends AbstractCommand {

    public ExitCommand() {
        super("exit", "closes the app", 0);
    }

    @Override
    public Object[] readArgs(Object[] args) {
        return new Object[0];
    }

    @Override
    public Response execute(Object[] args) {
        State.switchPerformanceStatus();
        if (!CommandListener.isOnClient()) {
            try {
                System.out.println("Saving...");
                CommandManager.getCollectionManager().write();
            } catch (IOException e) {
                return new Response(e.getMessage());
            }
        }
        return new Response("Shut down.");
    }

}
