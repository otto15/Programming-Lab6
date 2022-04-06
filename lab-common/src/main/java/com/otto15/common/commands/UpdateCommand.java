//package com.otto15.common.commands;
//
//import com.otto15.common.controllers.CommandManager;
//import com.otto15.common.entities.Person;
//import com.otto15.common.entities.PersonLoader;
//
//import java.io.IOException;
//
//public class UpdateCommand extends AbstractCommand {
//
//    public UpdateCommand() {
//        super("update", "updates person value", 1);
//    }
//
//    @Override
//    public boolean execute(Object[] args) {
//        try {
//            Person person = CommandManager.getCollectionManager().findById(Long.parseLong(args[0]));
//            person.setName(
//                    PersonLoader.loadName(getReader(), person.getName()));
//            person.setCoordinates(
//                    PersonLoader.loadCoordinates(getReader(), person.getCoordinates().toString()));
//            person.setHeight(
//                    PersonLoader.loadHeight(getReader(), Long.toString(person.getHeight())));
//            person.setEyeColor(
//                    PersonLoader.loadEyeColor(getReader(), person.getEyeColor().name()));
//            person.setHairColor(
//                    PersonLoader.loadHairColor(getReader(), person.getHairColor().name()));
//            person.setNationality(
//                    PersonLoader.loadNationality(getReader(), person.getNationality().name()));
//            person.setLocation(
//                    PersonLoader.loadLocation(getReader(), person.getLocation().toString()));
//            System.out.println("Person successfully updated!");
//            return true;
//        } catch (IOException e) {
//            System.out.println("Input error.");
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid format of id.");
//        } catch (IllegalArgumentException e) {
//            System.out.println(e.getMessage());
//        }
//        return false;
//    }
//}
