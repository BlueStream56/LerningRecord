package com.example.designpatterns.creational.abstractfactory;

public class FactoryProducer {

    public static AbstractFactory getFactory(String choice){
        if (FactoryEnum.SHAPE.toString().equalsIgnoreCase(choice)){
            return new ShapeFactory();
        }else if (FactoryEnum.COLOR.toString().equalsIgnoreCase(choice)){
            return new ColorFactory();
        }
        return null;
    }

}
