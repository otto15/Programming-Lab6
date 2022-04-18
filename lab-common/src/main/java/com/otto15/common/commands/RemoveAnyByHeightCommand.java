package com.otto15.common.commands;

import com.otto15.common.controllers.CommandManager;
import com.otto15.common.entities.Person;
import com.otto15.common.network.Response;

public class RemoveAnyByHeightCommand extends AbstractCommand {

    public RemoveAnyByHeightCommand() {
        super("remove_any_by_height", "removes collection element by height", 1);
    }

    @Override
    public Object[] readArgs(Object[] args) {
        try {
            long height = Long.parseLong((String) args[0]);
            return new Object[]{height};
        } catch (NumberFormatException e) {
            System.out.println("Invalid format of height.");
        }
        return null;
    }

    @Override
    public Response execute(Object[] args) {
        Person deletedPerson = CommandManager.getCollectionManager().removeAnyByHeight((Long) args[0]);
        if (deletedPerson.getId() == -1) {
            return new Response("No person found with such height.");
        }
        return new Response("Person[" + deletedPerson.getId() + "] " + deletedPerson.getName() + " was removed.");

    }
}
