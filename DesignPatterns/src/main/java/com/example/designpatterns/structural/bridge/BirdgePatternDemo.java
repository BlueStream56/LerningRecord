package com.example.designpatterns.structural.bridge;

public class BirdgePatternDemo {

    public static void main(String[] args) {
        Shape redCircle = new Circle(100, 100, 10, new RedCircle());
        Shape greenCircle = new Circle(100, 100, 10, new GreeanCircle());

        redCircle.draw();
        greenCircle.draw();
    }

}
