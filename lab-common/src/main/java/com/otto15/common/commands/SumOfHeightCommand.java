package com.otto15.common.commands;


import com.otto15.common.controllers.CommandManager;
import com.otto15.common.network.Response;

public class SumOfHeightCommand extends AbstractCommand {

    public SumOfHeightCommand() {
        super("sum_of_height", "outputs the sum of person's heights", 0);
    }

    @Override
    public Object[] readArgs(Object[] args) {
        return new Object[0];
    }

    @Override
    public Response execute(Object[] args) {
        System.out.println();
        return new Response(String.valueOf(CommandManager.getCollectionManager().getSumOfHeights()));
    }
}
