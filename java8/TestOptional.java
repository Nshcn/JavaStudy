package com.atguigu.java8;

import org.junit.Test;

import java.util.Optional;

public class TestOptional {

    /*
    Optional容器类的常用方法
    Optional.of(T t) : 创建一个 Optional 实例
    Optional.empty() : 创建一个空的 Optional 实例
    Optional.ofNullable(T t):若 t 不为 null,创建 Optional 实例,否则创建空实例
    isPresent() : 判断是否包含值
    orElse(T t) : 如果调用对象包含值，返回该值，否则返回t
    orElseGet(Supplier s) :如果调用对象包含值，返回该值，否则返回 s 获取的值
    map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回 Optional.empty()
    flatMap(Function mapper):与 map 类似，要求返回值必须是Optional
    */

    //Optional.of(T t) : 创建一个 Optional 实例
    @Test
    public void test1() {
        Optional<Employee> op = Optional.of(new Employee());
        //Optional<Employee> op=Optional.of(null);//如果of方法里面是null，那么执行的时候会产生空指针异常，这里使得空指针异常能在Optional类建立的时候就显示出来，能更早的定位出来空指针异常
        Employee emp = op.get();
        System.out.println(emp);
    }

    //Optional.empty() : 创建一个空的 Optional 实例
    @Test
    public void test2() {
        Optional<Employee> op = Optional.empty();
        System.out.println(op.get());//NoSuchElementException
    }

    //Optional.ofNullable(T t):若 t 不为 null,创建 Optional 实例,否则创建空实例
    @Test
    public void test3() {
        Optional<Employee> op = Optional.ofNullable(new Employee());//ofNullable允许传入null，这时会在op.get的时候产生NoSuchElementException异常
        if (op.isPresent()) {//isPresent() : 判断是否包含值
            System.out.println(op.get());
        }

        //orElse(T t) : 如果调用对象包含值，返回该值，否则返回t
        Employee emp = op.orElse(new Employee(22, "张三", 23, 2399));
        System.out.println(emp);
        //orElseGet(Supplier s) :如果调用对象包含值，返回该值，否则返回 s 获取的值
        Employee emp2 = op.orElseGet(() -> new Employee());//orElseGet里面是函数式接口，可以用来实现一些为空的时候的操作
        System.out.println(emp2);
    }

    @Test
    public void test4(){
        Optional<Employee> op=Optional.ofNullable(new Employee(22, "张三", 23, 2399));
        //map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回 Optional.empty()
        Optional<String> str = op.map((e) -> e.getName());
        System.out.println(str.get());
        //flatMap(Function mapper):与 map 类似，要求返回值必须是Optional
        Optional<String> str2 = op.flatMap((e) -> Optional.of(e.getName()));
        System.out.println(str2.get());
    }

    //例题
    @Test
    public void test5(){
        Man man=new Man();

        String n=getGodnessName(man);
        System.out.println(n);//当man没有女神时会产生NullPointerException

        Optional<Godness> gn=Optional.ofNullable(new Godness("波多老师"));
        Optional<NewMan> op=Optional.ofNullable(new NewMan());
        String str=getGodnessName2(op);
        System.out.println(str);
    }

    //需求：获取一个男人心中女神的名字
    //以前处理空指针异常的写法，需要嵌套多个if else语句
    public String getGodnessName(Man man){
        if(man!=null){
            Godness gn=man.getGodness();
            if(gn!=null){
                return gn.getName();
            }
        }
        return "苍老师";
    }

    public String getGodnessName2(Optional<NewMan> man){
        return man.orElse(new NewMan())
                .getGodness()
                .orElse(new Godness("苍老师"))
                .getName();
    }

}
