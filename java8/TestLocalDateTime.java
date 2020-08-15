package com.atguigu.java8;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

public class TestLocalDateTime {

    //1. LocalDate LocalTime LocalDateTime 人读的时间日期
    @Test
    public void test1() {
        //用now方法获取当前系统时间
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);//2020-08-15T16:25:28.068609600

        //用of方法获取LocalDateTime，指定年月日时分秒
        LocalDateTime ldt2 = LocalDateTime.of(2015, 10, 19, 13, 22, 33);
        System.out.println(ldt2);//2015-10-19T13:22:33

        //plus方法给时间对象增加指定时间
        LocalDateTime ldt3 = ldt2.plusYears(2);
        System.out.println(ldt3);//2017-10-19T13:22:33

        //minus方法给时间对象减掉指定时间
        LocalDateTime ldt4 = ldt2.minusMonths(2);
        System.out.println(ldt4);//2015-08-19T13:22:33

        //用get方法获取当前时间对象的年、月、日、时、分、秒
        System.out.println(ldt.getYear());
        System.out.println(ldt.getMonthValue());
        System.out.println(ldt.getDayOfMonth());
        System.out.println(ldt.getHour());
        System.out.println(ldt.getMinute());
        System.out.println(ldt.getSecond());
    }

    //2. Instant : 时间戳(以Unix 元年: 1970年1月1日 00:00:00到某个时间之间的毫秒值)，计算机读的时间
    @Test
    public void test2() {
        Instant ins1 = Instant.now();//默认获取UTC时区的时间
        System.out.println(ins1);//2020-08-15T08:36:51.543527400Z

        //带偏移量的时间日期
        OffsetDateTime odt = ins1.atOffset(ZoneOffset.ofHours(8));
        System.out.println(odt);//2020-08-15T16:36:37.428768100+08:00

        //转换为毫秒值时间戳
        System.out.println(ins1.toEpochMilli());//转换为毫秒值时间戳1597480682525

        //从Unix元年起加偏移量1秒
        Instant ins2 = Instant.ofEpochSecond(1);
        System.out.println(ins2);//1970-01-01T00:00:01Z
    }


    // Duration: 计算两个时间之间的间隔
    @Test
    public void test3() {
        //输出时间间隔
        Instant ins1 = Instant.now();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        Instant ins2 = Instant.now();

        Duration duration = Duration.between(ins1, ins2);
        System.out.println(duration);//输出时间间隔PT1.0104327S
        System.out.println(duration.toMillis());//输出毫秒用toMillis,1007

        System.out.println("------------------");

        LocalTime lt1 = LocalTime.now();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        LocalTime lt2 = LocalTime.now();
        System.out.println(Duration.between(lt1, lt2).toMillis());
    }

    // Period: 计算两个日期之间的间隔
    @Test
    public void test4() {
        LocalDate ld1 = LocalDate.of(2015, 1, 1);
        LocalDate ld2 = LocalDate.now();
        Period period = Period.between(ld1, ld2);
        System.out.println(period);//P5Y7M14D

        System.out.println(period.getYears());
        System.out.println(period.getMonths());
        System.out.println(period.getDays());

    }

    //TemporalAdjuster : 时间矫正器
    @Test
    public void test5() {
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);//2020-08-15T16:57:44.818890900

        //用with方法指定年月日
        LocalDateTime ldt2 = ldt.withDayOfMonth(10);
        System.out.println(ldt2);//2020-08-10T16:57:44.818890900

        //TemporalAdjuster是一个接口，可以通过TemporalAdjusters工具类来操控时间，也可以自定义
        LocalDateTime ldt3 = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));//下一个周日是什么时候
        System.out.println(ldt3);//2020-08-16T17:00:26.910562100

        //自定义：下一个工作日是哪天
        LocalDateTime ldt5 = ldt.with((l) -> {
            LocalDateTime ldt4 = (LocalDateTime) l;

            DayOfWeek dow = ldt4.getDayOfWeek();
            if (dow.equals(DayOfWeek.FRIDAY)) {
                return ldt4.plusDays(3);
            } else if (dow.equals(DayOfWeek.SATURDAY)) {
                return ldt4.plusDays(2);
            } else {
                return ldt4.plusDays(1);
            }
        });
        System.out.println(ldt5);
    }

    //DateTimeFormatter : 格式化时间/日期
    @Test
    public void test6() {
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE;//指定格式化标准
        LocalDateTime ldt=LocalDateTime.now();

        String strDate = ldt.format(dtf);//按指定格式化标准格式化时间
        System.out.println(strDate);
        System.out.println("---------------");

        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String strDate2 = dtf2.format(ldt);
        System.out.println(strDate2);//2020年08月15日 17:13:25

        //解析字符串为时间日期
        LocalDateTime newDate = ldt.parse(strDate2,dtf2);//按照dtf2的格式解析字符串strDate2
        System.out.println(newDate);//2020-08-15T17:14:55
    }

    //ZonedDate、ZonedTime、ZonedDateTime
    @Test
    public void test7(){
        //输出所有时区
        Set<String> set = ZoneId.getAvailableZoneIds();
        set.forEach(System.out::println);//2020-08-15T17:19:43.519457300+03:00[Europe/Tallinn]

    }

    @Test
    public void test8(){
        //指定时区来构建时间
        LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Europe/Tallinn"));
        System.out.println(ldt);

        //用atZone构建带时区的时间
        LocalDateTime ldt2 = LocalDateTime.now(ZoneId.of("Europe/Tallinn"));
        ZonedDateTime zdt = ldt2.atZone(ZoneId.of("Europe/Tallinn"));
        System.out.println(zdt);//2020-08-15T12:20:25.852530700+03:00[Europe/Tallinn]
    }

}
