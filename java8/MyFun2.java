package com.atguigu.java8;

public interface MyFun2 {
    //java8以前接口中只能有全局静态常量和抽象方法，java8之后有默认方法
    default String getName() {
        return "哈哈哈";
    }
}

