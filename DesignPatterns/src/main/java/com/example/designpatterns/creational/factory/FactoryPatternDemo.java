package com.example.designpatterns.creational.factory;

public class FactoryPatternDemo {

    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();

        Shape shape1 = shapeFactory.getShape(ShapeEnum.CIRCLE.toString());
        shape1.draw();

        Shape shape2 = shapeFactory.getShape(ShapeEnum.RECTANGLE.toString());
        shape2.draw();

        Shape shape3 = shapeFactory.getShape(ShapeEnum.SQUARE.toString());
        shape3.draw();

    }

}
