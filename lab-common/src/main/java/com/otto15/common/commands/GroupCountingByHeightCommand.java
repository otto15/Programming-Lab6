package com.otto15.common.commands;

import com.otto15.common.controllers.CommandManager;

/**
 * Command for grouping by height
 */
public class GroupCountingByHeightCommand extends AbstractCommand {

    public GroupCountingByHeightCommand() {
        super("group_counting_by_height", "outputs the number of group members", 0);
    }

    @Override
    public Object[] readArgs(Object[] args) {
        return new Object[0];
    }

    @Override
    public String execute(Object[] args) {
        CommandManager.getCollectionManager().makeGroupsByHeight();
        return CommandManager.getCollectionManager().outputGroupsByHeight();
    }
}
