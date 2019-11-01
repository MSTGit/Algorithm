package com.xd.PriorityQueue;

public class Person implements Comparable<Person>{
    private String name;
    private int boneBreak;

    public Person(String name, int boneBreak) {
        this.name = name;
        this.boneBreak = boneBreak;
    }

    @Override
    public int compareTo(Person o) {
        return boneBreak - o.boneBreak;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", boneBreak=" + boneBreak +
                '}';
    }
}
