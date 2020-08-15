package com.atguigu.java8;

import java.util.Objects;

public class Employee {
    private int id;
    private String name;
    private int age;
    private double salary;
    private Status Status;

//    public Employee(int i, String 李四, int i1, double v) {
//        super();
//    }

    public Employee(){

    }
    public Employee(int age){
        this.age=age;
    }

    public Employee(int age,String name){
        this.age=age;
        this.name=name;
    }

    public Employee(int id,String name, int age, double salary) {
        this.id=id;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", Status=" + Status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                age == employee.age &&
                Double.compare(employee.salary, salary) == 0 &&
                Objects.equals(name, employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, salary);
    }

    public enum Status{
        FREE,
        BUSY,
        VOCATION;
    }

    public Employee(int id, String name, int age, double salary, Employee.Status status) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
        Status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee.Status getStatus() {
        return Status;
    }

    public void setStatus(Employee.Status status) {
        Status = status;
    }
}
