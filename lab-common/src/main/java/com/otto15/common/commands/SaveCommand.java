//package com.otto15.common.commands;
//
//import com.otto15.common.config.Configurator;
//import com.otto15.common.controllers.CommandManager;
//
//import java.io.IOException;
//
//public class SaveCommand extends AbstractCommand {
//
//    public SaveCommand() {
//        super("save", "saves collection to the file", 0);
//    }
//
//    @Override
//    public boolean execute(Object[] args) {
//        try {
//            Configurator.COLLECTION_FILE_WRITER.write(Configurator.getOutputFile(), CommandManager.getCollectionManager());
//            return true;
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//        return false;
//    }
//}
