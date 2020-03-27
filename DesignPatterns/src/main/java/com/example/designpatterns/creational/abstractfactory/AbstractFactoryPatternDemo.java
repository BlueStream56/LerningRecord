package com.example.designpatterns.creational.abstractfactory;

import com.example.designpatterns.creational.abstractfactory.color.Color;
import com.example.designpatterns.creational.abstractfactory.color.ColorEnum;
import com.example.designpatterns.creational.abstractfactory.shape.Shape;
import com.example.designpatterns.creational.abstractfactory.shape.ShapeEnum;

public class AbstractFactoryPatternDemo {

    public static void main(String[] args) {
        //获取形状工厂
        AbstractFactory shapeFactory = FactoryProducer.getFactory(FactoryEnum.SHAPE.toString());

        //获取形状为 Circle 的对象
        Shape shape1 = shapeFactory.getShape(ShapeEnum.CIRCLE.toString());

        //调用 Circle 的 draw 方法
        shape1.draw();

        //获取形状为 Rectangle 的对象
        Shape shape2 = shapeFactory.getShape(ShapeEnum.RECTANGLE.toString());

        //调用 Rectangle 的 draw 方法
        shape2.draw();

        //获取形状为 Square 的对象
        Shape shape3 = shapeFactory.getShape(ShapeEnum.SQUARE.toString());

        //调用 Square 的 draw 方法
        shape3.draw();

        //获取颜色工厂
        AbstractFactory colorFactory = FactoryProducer.getFactory(FactoryEnum.COLOR.toString());

        //获取颜色为 Red 的对象
        Color color1 = colorFactory.getColor(ColorEnum.RED.toString());

        //调用 Red 的 fill 方法
        color1.fill();

        //获取颜色为 Green 的对象
        Color color2 = colorFactory.getColor(ColorEnum.GREEN.toString());

        //调用 Green 的 fill 方法
        color2.fill();

        //获取颜色为 Blue 的对象
        Color color3 = colorFactory.getColor(ColorEnum.BLUE.toString());

        //调用 Blue 的 fill 方法
        color3.fill();
    }

}
