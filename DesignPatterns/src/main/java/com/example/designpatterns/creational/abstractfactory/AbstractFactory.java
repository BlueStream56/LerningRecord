package com.example.designpatterns.creational.abstractfactory;

import com.example.designpatterns.creational.abstractfactory.color.Color;
import com.example.designpatterns.creational.abstractfactory.shape.Shape;

public abstract class AbstractFactory {

    public abstract Color getColor(String color);
    public abstract Shape getShape(String shape);

}
