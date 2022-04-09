package com.otto15.common.commands;


import com.otto15.common.controllers.CommandManager;

import java.io.IOException;

public class SaveCommand extends AbstractCommand {

    public SaveCommand() {
        super("save", "saves collection to the file", 0);
    }

    @Override
    public Object[] readArgs(Object[] args) {
        return new Object[0];
    }

    @Override
    public String execute(Object[] args) {
        try {
            CommandManager.getCollectionManager().write();
            return "Saved successfully!";
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
