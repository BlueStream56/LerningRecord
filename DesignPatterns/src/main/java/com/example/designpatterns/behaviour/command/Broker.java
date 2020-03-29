package com.example.designpatterns.behaviour.command;

import java.util.ArrayList;
import java.util.List;

public class Broker {

    private List<Order> orderList = new ArrayList<Order>();

    public void takeOrder(Order order){
        orderList.add(order);
    }

    public void placeOrders(){
        orderList.stream().forEach(order -> {
            order.execute();
        });
        orderList.clear();
    }

}
