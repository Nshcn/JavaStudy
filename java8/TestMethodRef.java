package com.atguigu.java8;

import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.*;

/*
 * 一、方法引用：若 Lambda 体中的功能，已经有方法提供了实现，可以使用方法引用
 * 			  （可以将方法引用理解为 Lambda 表达式的另外一种表现形式）
 *
 * 1. 对象的引用 :: 实例方法名
 *
 * 2. 类名 :: 静态方法名
 *
 * 3. 类名 :: 实例方法名
 *
 * 注意：
 * 	 ①方法引用所引用的方法的参数列表与返回值类型，需要与函数式接口中抽象方法的参数列表和返回值类型保持一致！
 * 	 ②若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式： ClassName::MethodName
 *
 * 二、构造器引用 :构造器的参数列表，需要与函数式接口中参数列表保持一致！
 *
 * 1. 类名 :: new
 *
 * 三、数组引用
 *
 * 	类型[] :: new;
 *
 *
 */
public class TestMethodRef {

    //数组引用
    @Test
    public void test7(){
        Function<Integer,String[]> fun=(x)->new String[x];
        String[] strs = fun.apply(10);
        System.out.println(strs.length);

        Function<Integer,String[]> fun2=String[]::new;
        String[] strs2 = fun2.apply(20);
        System.out.println(strs2.length);
    }

    //构造器引用 :构造器的参数列表，需要与函数式接口中参数列表保持一致！
    @Test
    public void test6() {
        Function<Integer,Employee> fun=(x)->new Employee(x);

        Function<Integer,Employee> fun2=Employee::new;
        Employee emp = fun2.apply(101);
        System.out.println(emp);

        BiFunction<Integer,String,Employee> bf=Employee::new;
        Employee emp1 = bf.apply(45, "mike");
        System.out.println(emp1);

    }

    //类名 :: 实例方法名
    /*
    ①方法引用所引用的方法的参数列表与返回值类型，需要与函数式接口中抽象方法的参数列表和返回值类型保持一致！
    ②若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式： ClassName::MethodName
     */
    @Test
    public void test5() {
        Supplier<Employee> sup = () -> new Employee(102, "李四", 59, 6666.66);

        Supplier<Employee> sup2 = () -> new Employee(102, "李四", 59, 6666.66);
        Employee emp = sup2.get();
        System.out.println(emp);
    }

    //类名 :: 静态方法名
    @Test
    public void test4() {
        BiPredicate<String, String> bp = (x, y) -> x.equals(y);

        //若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式： ClassName::MethodName
        BiPredicate<String, String> bp2 = String::equals;
    }


    @Test
    public void test3() {
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);

        //使用注意：Lambda体中的调用方法的参数列表和返回值类型，要与函数式接口中的抽象方法的参数列表和返回值类型保持一致！
        Comparator<Integer> com1 = Integer::compare;
        System.out.println(com1.compare(4, 5));
    }

    //对象的引用 :: 实例方法名
    @Test
    public void test2() {
        Employee emp = new Employee(102, "李四", 59, 6666.66);
        Supplier<String> sup = () -> emp.getName();
        String str = sup.get();
        System.out.println(str);

        Supplier<Integer> sup2 = emp::getAge;
        Integer num = sup2.get();
        System.out.println(num);
    }

    @Test
    public void test1() {
        PrintStream ps = System.out;
        Consumer<String> con = (str) -> ps.println(str);
        con.accept("Hello World！");

        System.out.println("--------------------------------");

        Consumer<String> con2 = ps::println;
        con2.accept("Hello Java8！");

        Consumer<String> con3 = System.out::println;
    }
}









