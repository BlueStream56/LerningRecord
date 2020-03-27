package com.example.designpatterns.creational.factory;

import org.springframework.util.StringUtils;

public class ShapeFactory {

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
