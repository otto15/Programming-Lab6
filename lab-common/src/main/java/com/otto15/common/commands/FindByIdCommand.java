package com.otto15.common.commands;

import com.otto15.common.controllers.CommandManager;
import com.otto15.common.entities.Person;
import com.otto15.common.network.Response;

public class FindByIdCommand extends AbstractCommand {

    public FindByIdCommand() {
        super("", "", 0);
    }

    @Override
    public Object[] readArgs(Object[] args) {
        return new Object[0];
    }

    @Override
    public Response execute(Object[] args) {
        Person foundPerson = CommandManager.getCollectionManager().findById((Long) args[0]);
        if (foundPerson.getId() == -1) {
            return new Response("not found");
        }
        return new Response(foundPerson.currentValues());
    }

}
