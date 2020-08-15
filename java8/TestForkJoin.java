package com.atguigu.java8;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.LongSummaryStatistics;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class TestForkJoin {

    /*
    ForkJoin框架
     */
    @Test
    public void test1(){
        Instant start = Instant.now();

        //ForkJoin的执行需要ForkJoinPool线程池的支持
        ForkJoinPool pool=new ForkJoinPool();
        ForkJoinTask<Long> task=new ForkJoinCalculate(0,1000000000L);
        Long sum = pool.invoke(task);
        System.out.println(sum);

        Instant end = Instant.now();

        //数值越大越能体现ForkJoin的效率，如果数值较小，拆分也需要时间，不一定有普通for循环快
        System.out.println("耗费时间"+Duration.between(start,end).toMillis());
    }

    /*
    普通for
     */
    @Test
    public void test2(){
        Instant start = Instant.now();
        long sum=0L;

        for(int i=0;i<=1000000000L;i++){
            sum+=i;
        }
        System.out.println(sum);

        Instant end = Instant.now();

        System.out.println("耗费时间"+Duration.between(start,end).toMillis());
    }

    /*
    java8并行流
     */
    @Test
    public void test3(){
        Instant start = Instant.now();
        //累加操作，如果用顺序流，效率低
        LongStream.rangeClosed(0,10000000000L)//LongStream是Stream的Long的表现形式
                .parallel()//转换为并行流，提高效率
                .reduce(0,Long::sum);
        Instant end = Instant.now();

        System.out.println("耗费时间"+Duration.between(start,end).toMillis());
    }
}
