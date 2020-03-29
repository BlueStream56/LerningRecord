package com.example.designpatterns.structural.filter;

import java.util.ArrayList;
import java.util.List;

public class CriteriaMale implements Criteria {
    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> malePersons = new ArrayList<Person>();
        if (persons != null && persons.size() > 0) {
            for (Person person: persons) {
                if ("MALE".equalsIgnoreCase(person.getGender())) {
                    malePersons.add(person);
                }
            }
        }
        return malePersons;
    }
}
