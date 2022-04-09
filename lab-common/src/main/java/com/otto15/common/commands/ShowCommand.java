package com.otto15.common.commands;


import com.otto15.common.controllers.CommandManager;

public class ShowCommand extends AbstractCommand {

    public ShowCommand() {
        super("show", "outputs all collection elements", 0);
    }


    @Override
    public Object[] readArgs(Object[] args) {
        return new Object[0];
    }

    @Override
    public String execute(Object[] args) {
        return CommandManager.getCollectionManager().show();
    }

}
