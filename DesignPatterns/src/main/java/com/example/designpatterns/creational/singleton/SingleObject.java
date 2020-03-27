package com.example.designpatterns.creational.singleton;

public class SingleObject {

    private static SingleObject instance = new SingleObject();

    //私有化无参构造函数，是的无法使用new关键字进行实例化
    private SingleObject(){}

    public static SingleObject getInstance() {
        return instance;
    }

    public void showMessage(){
        System.out.println("Hello World!");
    }

}
