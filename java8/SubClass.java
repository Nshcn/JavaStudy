package com.atguigu.java8;

public class SubClass /*extends MyClass*/ implements MyFun2,MyInterface {

    @Override
    public String getName() {
        return MyFun2.super.getName();
    }
}
