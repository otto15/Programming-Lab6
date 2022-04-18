package com.otto15.common.commands;


import com.otto15.common.controllers.CommandManager;
import com.otto15.common.network.Response;

/**
 * Command for clearing the collection
 */
public class ClearCommand extends AbstractCommand {

    public ClearCommand() {
        super("clear", "deletes all collection elements", 0);
    }

    @Override
    public Object[] readArgs(Object[] args) {
        return new Object[0];
    }

    @Override
    public Response execute(Object[] args) {
        CommandManager.getCollectionManager().clear();
        return new Response("Collection has been cleared.");
    }

}
