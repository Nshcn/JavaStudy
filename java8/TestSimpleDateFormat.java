package com.atguigu.java8;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;

public class TestSimpleDateFormat {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

//        //创建线程池执行的实例
//        Callable<Date> task = new Callable<Date>() {
//            @Override
//            public Date call() throws Exception {
//                //return sdf.parse("20161218");
//                return DateFormatThreadLocal.convert("20161218");
//            }
//        };
//        //创建一个长度为10的线程池
//        ExecutorService pool = Executors.newFixedThreadPool(10);
//
//        List<Future<Date>> results = new ArrayList<>();
//        //线程执行10次call调用，都去解析”20161218“这个时间格式
//        for (int i = 0; i < 10; i++) {
//            results.add(pool.submit(task));
//        }
//        //遍历集合的内容
//        for(Future<Date> future:results){
//            System.out.println(future.get());
//        }
//
//        pool.shutdown();

        DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyyMMdd");
        Callable<LocalDate> task = new Callable<LocalDate>() {
            @Override
            public LocalDate call() throws Exception {
                return LocalDate.parse("20161218",dtf);
            }
        };
        //创建一个长度为10的线程池
        ExecutorService pool = Executors.newFixedThreadPool(10);
        List<Future<LocalDate>> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            results.add(pool.submit(task));
        }
        for(Future<LocalDate> future:results){
            System.out.println(future.get());
        }
        pool.shutdown();

    }
}
