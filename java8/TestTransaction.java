package com.atguigu.java8;

import org.junit.Before;
import org.junit.Test;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestTransaction {
    List<Transaction> transaction=null;

    @Before
    public void before(){
        Trader raoul=new Trader("Raoul","Cambridge");
        Trader mario=new Trader("Mario","Milan");
        Trader alan=new Trader("Alan","Cambridge");
        Trader brian=new Trader("Brian","Cambridge");

        transaction= Arrays.asList(
                new Transaction(brian,2011,300),
                new Transaction(raoul,2012,1000),
                new Transaction(raoul,2011,400),
                new Transaction(mario,2012,710),
                new Transaction(mario,2012,700),
                new Transaction(alan,2012,950)
        );
    }

    //1.找出2011年发生的所有交易，并按交易额排序（从低到高）
    //2.交易员都在哪些不同的城市工作过？
    //3.查找所有来自剑桥的交易员，并按姓名排序
    //4.返回所有交易员的姓名字符串，按字母顺序排序
    //5.有没有交易员是在米兰工作的？
    //6.打印生活在剑桥的交易员的所有交易额
    //7.所有交易中，最高的交易额是多少
    //8.找到交易额最小的交易

    //1.找出2011年发生的所有交易，并按交易额排序（从低到高）
    @Test
    public void test1(){
        transaction.stream()
                .filter((t)->t.getYear()==2011)//过滤出2011年的交易
                .sorted((t1,t2)->Integer.compare(t1.getValue(),t2.getValue()))//自定义按照交易额排序
                .forEach(System.out::println);
    }

    //2.交易员都在哪些不同的城市工作过？
    @Test
    public void test2(){
        transaction.stream()
                .map((t)->t.getTrader().getCity())
                .distinct()//去重，去掉重复的
                .forEach(System.out::println);
    }

    //3.查找所有来自剑桥的交易员，并按姓名排序
    @Test
    public void test3(){
        transaction.stream()
                .filter((t)->t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getTrader)
                .sorted((t1,t2)->t1.getName().compareTo(t2.getName()))
                .distinct()
                .forEach(System.out::println);
    }

    /*4.返回所有交易员的姓名字符串，按字母顺序排序
    4.1先按姓名字母排序然后连接字符串
    4.2先连接字符串然后按字母排序
    */
    @Test
    public void test4(){
        transaction.stream()
                .map((t)->t.getTrader().getName())
                .distinct()
                .sorted()
                .forEach(System.out::println);
        System.out.println("---------------");

        String str=transaction.stream()
                .map((t)->t.getTrader().getName())
                .sorted()
                .reduce("",String::concat);//连接字符串
        System.out.println(str);
        System.out.println("---------------");

        transaction.stream()
                .map((t)->t.getTrader().getName())
                .flatMap(TestTransaction::filterCharacter)
                .sorted((s1,s2)->s1.compareToIgnoreCase(s2))//忽略字母大小写进行排序
                .forEach(System.out::print);

    }

    //将字符串提取成字符流
    public static Stream<String> filterCharacter(String str){
        List<String> list=new ArrayList<>();

        for(Character ch:str.toCharArray()){
            list.add(ch.toString());
        }

        return list.stream();
    }

    //5.有没有交易员是在米兰工作的？
    @Test
    public void test5(){
        boolean bl=transaction.stream()
                .anyMatch((t)->t.getTrader().getCity().equals("Milan"));
        System.out.println(bl);
    }

    //6.打印生活在剑桥的交易员的所有交易额
    @Test
    public void test6(){
        Optional<Integer> sum=transaction.stream()
                .filter((t)->t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .reduce(Integer::sum);
        System.out.println(sum.get());
    }

    //7.所有交易中，最高的交易额是多少
    @Test
    public void test7(){
        Optional<Integer> max=transaction.stream()
                .map((t)->t.getValue())
                .max(Integer::compare);
        System.out.println(max.get());
    }

    //8.找到交易额最小的交易
    @Test
    public void test8(){
        Optional<Transaction> min=transaction.stream()
                .min((t1,t2)->Integer.compare(t1.getValue(),t2.getValue()));
        System.out.println(min.get());
    }
}
