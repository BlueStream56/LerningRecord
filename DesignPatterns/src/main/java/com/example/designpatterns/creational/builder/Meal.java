package com.example.designpatterns.creational.builder;

import java.util.ArrayList;
import java.util.List;

public class Meal {

    private List<Item> items = new ArrayList<Item>();

    public void addItem(Item item){
        items.add(item);
    }

    public float getCost(){
        float cost = 0.0f;
        if(null != items && items.size() > 0){
            for (Item item: items) {
                cost += item.price();
            }
        }
        return cost;
    }

    public void showItems(){
        if(null != items && items.size() > 0){
            for (Item item: items) {
                System.out.print("Item : " + item.name());
                System.out.print(", Packing : " + item.packing().pack());
                System.out.println(", Price : " + item.price());
            }
        }
    }

}
