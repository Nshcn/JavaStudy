package com.atguigu.java8;

import java.util.*;
import java.util.stream.Collectors;

import com.atguigu.java8.Employee.Status;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

/**
 * 终止操作
 */
public class TestStreamAPI3 {

    List<Employee> employees = Arrays.asList(
            new Employee(102, "李四", 59, 6666.66, Status.FREE),
            new Employee(102, "李四", 59, 6666.66, Status.FREE),
            new Employee(102, "李四", 59, 6666.66, Status.FREE),
            new Employee(102, "李四", 59, 6666.66, Status.FREE),
            new Employee(102, "李四", 59, 6666.66, Status.FREE),
            new Employee(101, "张三", 18, 9999.99, Status.BUSY),
            new Employee(103, "王五", 28, 3333.33, Status.VOCATION),
            new Employee(104, "赵六", 8, 7777.77, Status.FREE),
            new Employee(105, "田七", 38, 8888.88, Status.BUSY)
    );

    /*
    查找与匹配
    allMatch——检查是否匹配所有元素
    anyMatch——检查是否至少匹配一个元素
    noneMatch——检查是否没有匹配的元素
    findFirst——返回第一个元素
    findAny——返回当前流中的任意元素
    count——返回流中元素的总个数
    max——返回流中最大值
    min——返回流中最小值
    */

    @Test
    public void test2() {
        Long count = employees.stream()
                .count();
        System.out.println(count);

        //获取工资最小的员工信息
        Optional<Employee> op1 = employees.stream()
                .max((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
        System.out.println(op1.get());

        //获取工资的最小值
        Optional<Double> op2 = employees.stream()
                .map(Employee::getSalary)//先用map提取出工资
                .min(Double::compare);
        System.out.println(op2.get());
    }


    @Test
    public void test1() {
        //allMatch——检查是否匹配所有元素
        boolean b1 = employees.stream()
                .allMatch((e) -> e.getStatus().equals((Status.BUSY)));//都是BUSY的时候返回true
        System.out.println(b1);

        //anyMatch——检查是否至少匹配一个元素
        boolean b2 = employees.stream()
                .anyMatch((e) -> e.getStatus().equals((Status.BUSY)));
        System.out.println(b2);

        //noneMatch——检查是否没有匹配的元素
        boolean b3 = employees.stream()
                .noneMatch((e) -> e.getStatus().equals((Status.BUSY)));
        System.out.println(b3);

        //findFirst——返回第一个元素
        Optional<Employee> op = employees.stream()
                .sorted((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()))
                .findFirst();//对象的值有可能为空时就将其封装到Optional容器中，可以使用op.orElse(other)，指的是如果op里面为空，可以用other来替代，解决了空指针异常
        System.out.println(op.get());

        //findAny——返回当前流中的任意元素
        Optional op2 = employees.parallelStream()
                .filter((e) -> e.getStatus().equals(Status.FREE))
                .findAny();
        System.out.println(op2.get());
    }

    /*
    归约
    reduce(T identity,BinaryOperator b)——可以将流中的元素反复结合起来，得到一个值，返回T
    reduce(BinaryOperator b)——可以将流中元素反复结合起来，得到一个值，返回Optional<T>
     */

    @Test
    public void test3() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        Integer sum = list.stream()
                .reduce(0, (x, y) -> x + y);//将0作为x，将1作为y，运算得到1作为x，再将2作为y，继续...
        System.out.println(sum);

        System.out.println("------------------");


        Optional<Double> op = employees.stream()
                /*
                 * map和reduce的连接通常称为map-reduce模式，因Google用它来进行网络搜索而出名
                 * */
                .map(Employee::getSalary)
                .reduce(Double::sum);
        System.out.println(op.get());
    }

    /*
    收集
    collect——将流转换为其他形式。接收一个Collector接口的实现，用于给Stream中元素做汇总的方法
     */

    @Test
    public void test4() {
        List<String> list = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());//按什么方式进行一个结果的收集
        list.forEach(System.out::println);

        System.out.println("--------------");

        //用Set集合收集去掉重复元素
        Set<String> set = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());
        set.forEach(System.out::println);

        System.out.println("--------------");

        HashSet<String> hs = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));//想放到哪个集合中就指定哪个集合即可
        hs.forEach(System.out::println);
    }

    @Test
    public void test5() {
        //总数
        Long count = employees.stream()
                .collect(Collectors.counting());
        System.out.println(count);

        System.out.println("--------------");

        //平均值
        Double avg = employees.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(avg);

        //总和
        Double sum = employees.stream()
                .collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(sum);

        //最大值
        Optional<Employee> max = employees.stream()
                .collect(Collectors.maxBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
        System.out.println(max.get());

        //最小值
        Optional<Employee> min = employees.stream()
                .collect(Collectors.minBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
        System.out.println(min.get());
    }

    //分组
    @Test
    public void test6() {
        Map<Status, List<Employee>> map = employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus));//按照状态分组
        System.out.println(map);
    }

    //多级分组
    @Test
    public void test7() {
        //先按状态分，再按年龄分
        Map<Status,Map<String,List<Employee>>> map=employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
                    if (e.getAge() <= 35) {
                        return "青年";
                    } else if (e.getAge() <= 50) {
                        return "老年";
                    } else {
                        return "老年";
                    }

                })));
        System.out.println(map);
    }

    //分区
    @Test
    //分true和false两个区，满足条件的一个区，不满足条件的一个区
    public void test8(){
        Map<Boolean,List<Employee>> map=employees.stream()
                .collect(Collectors.partitioningBy((e)->e.getSalary()>8000));
        System.out.println(map);
    }

    @Test
    public void test9(){
        DoubleSummaryStatistics dss=employees.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(dss.getSum());
        System.out.println(dss.getAverage());
        System.out.println(dss.getMax());
    }

    //连接成字符串
    @Test
    public void test10(){
        String str=employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(","));
        System.out.println(str);
        //输出：李四,李四,李四,李四,李四,张三,王五,赵六,田七
    }
}

