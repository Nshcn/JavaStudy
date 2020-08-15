package com.atguigu.java8;

import java.util.function.Supplier;

public class TestDefalutInterface {

    public static void main(String[] args) {
        SubClass sc=new SubClass();
        System.out.println(sc.getName());

        MyInterface.show();
    }
}
