package com.example.designpatterns.structural.filter;

import java.util.List;

public interface Criteria {

    public List<Person> meetCriteria(List<Person> persons);

}
