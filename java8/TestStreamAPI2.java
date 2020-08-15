package com.atguigu.java8;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class TestStreamAPI2 {

    //中间操作
        List<Employee> employees = Arrays.asList(
                new Employee(102, "李四", 59, 6666.66),
                new Employee(101, "张三", 18, 9999.99),
                new Employee(103, "王五", 28, 3333.33),
                new Employee(104, "赵六", 8, 7777.77),
                new Employee(105, "田七", 38, 8888.88),
                new Employee(105, "田七", 38, 8888.88),
                new Employee(105, "田七", 38, 8888.88)
        );


    /*
	  筛选与切片
		filter——接收 Lambda ， 从流中排除某些元素。
		limit——截断流，使其元素不超过给定数量。
		skip(n) —— 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
		distinct——筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
	 */

    //内部迭代：迭代操作 Stream API 内部完成
    @Test
    public void test1() {

        Stream<Employee> stream = employees.stream()
                //中间操作：不会执行任何操作
                .filter((e) -> {
                    System.out.println("Stream API 的中间操作");
                    return e.getAge() > 35;
                });
        //终止操作：产生结果，只有执行终止操作之后所有的中间操作才一次性执行全部即“延迟加载”、“惰性求值”
        stream.forEach(System.out::println);
    }

    //外部迭代，需要Iterator遍历器
    @Test
    public void test2() {
        Iterator<Employee> it = employees.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    @Test
    public void test3() {
        employees.stream()
                .filter((e) -> {
                    System.out.println("短路");
                    return e.getSalary() > 5000;
                })
                .limit(2)
                .forEach(System.out::println);//只要找到满足条件的两个结果，后续的迭代操作就不再继续，即“短路”
    }

    @Test
    public void test4() {
        employees.stream()
                .filter((e) -> e.getSalary() > 5000)
                .skip(2)
                .distinct()
                .forEach(System.out::println);
    }

    /*
        映射
        map——接收Lambda收，将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
        flatMap——接收一个函数作为参数，将流中的个值都换成另一个流，然后把所有流连接成一个流
     */

    @Test
    public void test5() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");

        list.stream()
                .map((str) -> str.toUpperCase())
                .forEach(System.out::println);

        System.out.println("-----------------------");

        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);

        System.out.println("-----------------------");

        //Stream<Stream<Character>> stream=list.stream()
        //        .map(TestStreamAPI2::filterCharacter);//{{a,a,a},{b,b,b,},{c,c,c}}
        //
        //stream.forEach((sm)->{
        //    sm.forEach(System.out::println);
        //});

        System.out.println("-----------------------");

        Stream<Character> sm=list.stream()
                .flatMap(TestStreamAPI2::filterCharacter);//将map中的流转换为一整个流{a,a,a,b,b,b}
        /*
        类似add(Object obj)和addAll(Collection coll)的区别
        add添加集合相当于把一个个集合添加到当前集合中
        addAll添加集合相当于把集合中每一个元素添加到当前集合中
         */

        sm.forEach(System.out::println);
    }

    //将字符串转换为单个的字符流
    public static Stream<Character> filterCharacter(String str) {
        List<Character> list = new ArrayList<>();

        for (Character ch : str.toCharArray()) {
            list.add(ch);
        }

        return list.stream();
    }

    @Test
    public void test6(){
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");

        List list2=new ArrayList();

        list2.add(11);
        list2.add(22);
        /*
        add添加集合相当于把一个个集合添加到当前集合中
        addAll添加集合相当于把集合中每一个元素添加到当前集合中
         */
        list2.add(list);//输出[11, 22, [aaa, bbb, ccc, ddd, eee]]
        //list2.addAll(list);//输出[11, 22, aaa, bbb, ccc, ddd, eee]
        System.out.println(list2);
    }

    /*
    排序
    sorted()-自然排序(Comparable)
    sorted(Comparator com)-定制排序(Comparator)
     */
    @Test
    public void test7() {
        List<String> list = Arrays.asList("ccc", "bbb", "aaa", "ddd", "eee");

        list.stream()
                .sorted()//自然排序
                .forEach(System.out::println);

        System.out.println("-----------------------");

        employees.stream()
                .sorted((e1,e2)->{
                    if(e1.getAge()==(e2.getAge())){
                        return e1.getName().compareTo(e2.getName());
                    }else{
                        return e1.getAge()-e2.getAge();
                    }
                }).forEach(System.out::println);
    }
}