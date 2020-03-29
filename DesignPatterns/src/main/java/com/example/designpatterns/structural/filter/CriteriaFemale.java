package com.example.designpatterns.structural.filter;

import java.util.ArrayList;
import java.util.List;

public class CriteriaFemale implements Criteria {
    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> femalePersons = new ArrayList<Person>();
        if (persons != null && persons.size() > 0) {
            for (Person person: persons) {
                if ("FEMALE".equalsIgnoreCase(person.getGender())) {
                    femalePersons.add(person);
                }
            }
        }
        return femalePersons;
    }
}
