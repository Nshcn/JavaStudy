package com.atguigu.java8;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/*
RecursiveAction: 方法没有返回值
RecursiveTask: 方法有返回值
 */

public class ForkJoinCalculate extends RecursiveTask<Long> {

    private static final long serialVersionUID=13456970987L;//序列号要提供序列号
    //拆分的区间
    private long start;
    private long end;

    private static final long THRESHOLD=10000;//设置一个临界值作为拆分的下限

    public ForkJoinCalculate(long start,long end) {
        this.start=start;
        this.end=end;
    }
    @Override
    protected Long compute() {
        long length=end-start;

        if(length<=THRESHOLD){
            long sum=0;
            for(long i=start;i<=end;i++){
                sum+=i;
            }
            return sum;
        }else{
            long middle=(start+end)/2;
            ForkJoinCalculate left=new ForkJoinCalculate(start,middle);
            left.fork();//拆分子任务，同时压入线程队列

            ForkJoinCalculate right=new ForkJoinCalculate(middle+1,end);
            right.fork();

            return left.join()+right.join();
        }
    }
}
