package com.otto15.common.commands;


import com.otto15.common.controllers.CommandManager;

public class RemoveByIdCommand extends AbstractCommand {

    public RemoveByIdCommand() {
        super("remove_by_id", "removes collection element by id", 1);
    }

    @Override
    public Object[] readArgs(Object[] args) {
        try {
            return new Object[]{Long.parseLong((String) args[0])};
        } catch (NumberFormatException e) {
            System.out.println("Invalid format of id.");
        }
        return null;
    }

    @Override
    public String execute(Object[] args) {
        boolean result = CommandManager.getCollectionManager().removeById((Long) args[0]);
        if (result) {
            return "Removed successfully!";
        }
        return "Person with such id was not found.";
    }
}
