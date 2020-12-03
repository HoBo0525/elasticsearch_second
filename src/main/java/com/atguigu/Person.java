package com.atguigu;

/**
 * @author Hobo
 * @create 2020-12-03 14:29
 */

public class Person {
    private String id;
    private String address;
    private Integer age;
    private String name;

    public Person() {
    }

    public Person(String id, String address, Integer age, String name) {
        this.id = id;
        this.address = address;
        this.age = age;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
