package com.otto15.common.network;

import com.otto15.common.entities.Person;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

public class Response implements Serializable {

    private String message;
    private Collection<Person> persons;

    public Response(String message) {
        this.message = message;
    }

    public Response(Collection<Person> persons) {
        this.persons = persons;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void showResult() {
        if (persons != null) {
            if (persons.isEmpty()) {
                System.out.println("Collection is empty.");
                return;
            }
            System.out.println(persons.stream()
                    .map(Person::toString)
                    .collect(Collectors.joining("\n")));
            return;
        }
        System.out.println(message);
    }

    @Override
    public String toString() {
        return "Response{"
                + "message='" + message + '\''
                + '}';
    }
}
