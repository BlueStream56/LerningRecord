package com.example.designpatterns.structural.filter;

import java.util.ArrayList;
import java.util.List;

public class CriteriaSingle implements Criteria {
    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> singlePersons = new ArrayList<Person>();
        if (persons != null && persons.size() > 0) {
            for (Person person: persons) {
                if ("SINGLE".equalsIgnoreCase(person.getMaritalStatus())) {
                    singlePersons.add(person);
                }
            }
        }
        return singlePersons;
    }
}
