package com.example.designpatterns.structural.decorator;

public abstract class ShapeDecorator implements Shape {

    protected Shape decoratedSahpe;

    public ShapeDecorator(Shape decoratedSahpe) {
        this.decoratedSahpe = decoratedSahpe;
    }

    @Override
    public void draw() {
        decoratedSahpe.draw();
    }
}
