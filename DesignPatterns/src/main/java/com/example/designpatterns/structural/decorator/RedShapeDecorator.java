package com.example.designpatterns.structural.decorator;

public class RedShapeDecorator extends ShapeDecorator {

    public RedShapeDecorator(Shape decoratedSahpe) {
        super(decoratedSahpe);
    }

    @Override
    public void draw() {
        super.draw();
    }

    private void setRedBorder(Shape decoratedSahpe){
        System.out.println("Border Color: Red");
    }

}
