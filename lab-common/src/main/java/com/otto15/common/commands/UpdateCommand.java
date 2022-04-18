package com.otto15.common.commands;

import com.otto15.common.controllers.CommandManager;
import com.otto15.common.entities.Person;
import com.otto15.common.entities.PersonLoader;
import com.otto15.common.network.Request;
import com.otto15.common.network.Response;
import com.otto15.common.utils.DataNormalizer;

import java.io.IOException;

public class UpdateCommand extends AbstractCommand {

    public UpdateCommand() {
        super("update", "updates person value", 1);
    }

    @Override
    public Object[] readArgs(Object[] args) {
        try {
            long id = Long.parseLong((String) args[0]);
            Response response = CommandManager.getNetworkListener().listen(new Request(new FindByIdCommand(), new Object[]{id}));
            String currentValues = response != null ? response.getMessage() : "not found";
            if ("not found".equals(currentValues)) {
                System.out.println("No person found with such id.");
                return null;
            }
            Person updatedPerson = PersonLoader.loadPersonWithCurrentValues(DataNormalizer.normalize(currentValues));
            updatedPerson.setId(id);
            return new Object[]{updatedPerson};
        } catch (IOException e) {
            System.out.println("Input error.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid format of id.");
        }
        return null;
    }

    @Override
    public Response execute(Object[] args) {
        Person updatedPerson = (Person) args[0];
        CommandManager.getCollectionManager().update(updatedPerson);
        return new Response("Person successfully updated!");
    }
}
