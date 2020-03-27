package com.example.designpatterns.creational.abstractfactory;

import com.example.designpatterns.creational.abstractfactory.color.*;
import com.example.designpatterns.creational.abstractfactory.shape.Shape;

public class ColorFactory extends AbstractFactory {
    @Override
    public Color getColor(String color) {
        if(null == color || "".equals(color)){
            return null;
        }
        if (ColorEnum.RED.toString().equalsIgnoreCase(color)){
            return new Red();
        }else if (ColorEnum.GREEN.toString().equalsIgnoreCase(color)){
            return new Green();
        }else if (ColorEnum.BLUE.toString().equalsIgnoreCase(color)){
            return new Blue();
        }
        return null;
    }

    @Override
    public Shape getShape(String shape) {
        return null;
    }
}
