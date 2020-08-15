package com.atguigu.exer;

import com.atguigu.java8.Employee;
import com.atguigu.java8.MyFun;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestLambda {
    List<Employee> emps = Arrays.asList(
            new Employee("张三", 18, 9999.99),
            new Employee("李四", 38, 5555.99),
            new Employee("王五", 50, 6666.99),
            new Employee("赵六", 16, 3333.99),
            new Employee("田七", 8, 7777.99)
    );

    /*
    1.调用 Collections.sort()方法，通过定制排序比较两个Employee（先按年龄比，年龄相同按姓名比），使用Lambda作为参数传递
     */
    @Test
    public void test1(){
        Collections.sort(emps,(e1,e2)->{
            if(e1.getAge()==e2.getAge()) {
                return e1.getName().compareTo(e2.getName());
            }else{
                return Integer.compare(e1.getAge(),e2.getAge());
                //return -Integer.compare(e1.getAge(),e2.getAge());//倒序
            }
        });

        for (Employee emp:emps) {
            System.out.println(emp);
        }
    }

    /*
    2.  ①声明函数式接口，接口中声明抽象方法， public String getValue(String str);
        ②声明类 TestLambda，类中编写方法使用接口作为参数，将一个字符串转换成大写，并作为方法的返回值。
        ③再将一个字符串的第2个和第4个索引位置进行截取子串
     */
    @Test
    public void test2(){
        String trimStr = strHandler("\t\t\t 大威天龙 ", str -> str.trim());
        System.out.println(trimStr);

        String upper = strHandler("abcdef", str -> str.toUpperCase());
        System.out.println(upper);

        String newStr = strHandler("大威天龙式尊地藏", str -> str.substring(2, 5));
        System.out.println(newStr);

    }
    //需求：用于处理字符串
    public String strHandler(String str, MyFunction mf){
        return mf.getValue(str);
    }


    /*
    3.  ①声明一个带两个泛型的函数式接口，泛型类型为<T, R> T为参数，R为返回值
        ②接口中声明对应抽象方法
        ③在 TestLambda类中声明方法，使用接口作为参数，计算两个long型参数的和。
        ④再计算两个long型参数的乘积。
     */
    @Test
    public void test3(){
        op(100L,200L,(x,y)->x+y);

        op(100L,200L,(x,y)->x*y);
    }
    //需求：对于两个long型数据进行处理
    public void op(Long l1,Long l2,MyFunction2<Long,Long> mf){
        System.out.println(mf.getValue(l1,l2));
    }

    /*
    我要用Lambda表达式，还需要先建个接口来支持Lambda表达式，这不是更麻烦么？
    java8内置了很多函数式接口，不需要我们自己建
     */
}
