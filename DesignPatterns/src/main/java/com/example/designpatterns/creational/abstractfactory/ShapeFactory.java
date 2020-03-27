package com.example.designpatterns.creational.abstractfactory;

import com.example.designpatterns.creational.abstractfactory.color.Color;
import com.example.designpatterns.creational.abstractfactory.shape.*;

public class ShapeFactory extends AbstractFactory {

    @Override
    public Color getColor(String color) {
        return null;
    }

    @Override
    public Shape getShape(String shapeType){
        if(null == shapeType || "".equals(shapeType)){
            return null;
        }
        if (ShapeEnum.CIRCLE.toString().equalsIgnoreCase(shapeType)){
            return new Circle();
        }else if (ShapeEnum.RECTANGLE.toString().equalsIgnoreCase(shapeType)){
            return new Rectangle();
        }else if (ShapeEnum.SQUARE.toString().equalsIgnoreCase(shapeType)){
            return new Square();
        }
        return null;
    }

}
