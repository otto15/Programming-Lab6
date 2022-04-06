//package com.otto15.common.commands;
//
//import com.otto15.common.controllers.CommandManager;
//
///**
// * Command for grouping by height
// */
//public class GroupCountingByHeightCommand extends AbstractCommand {
//
//    public GroupCountingByHeightCommand() {
//        super("group_counting_by_height", "outputs the number of group members", 0);
//    }
//
//    @Override
//    public boolean execute(Object[] args) {
//        CommandManager.getCollectionManager().makeGroupsByHeight();
//        CommandManager.getCollectionManager().outputGroupsByHeight();
//        return true;
//    }
//}
