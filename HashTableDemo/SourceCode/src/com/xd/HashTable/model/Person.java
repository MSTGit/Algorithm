package com.xd.HashTable.model;

public class Person {
    private int age;
    private float height;
    private String name;
    public Person(int age,float height,String name) {
        this.age = age;
        this.height = height;
        this.name = name;
    }
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != getClass()) return false;
        Person person = (Person)obj;
        return person.age == age
                && person.height == height
                && (person.name == null ? name == null : person.name.equals(name));
    }

    public int hashCode(){
        int hashCode = Integer.hashCode(age);
        hashCode = hashCode * 31 + Float.hashCode(height);
        hashCode = hashCode * 31 + (name != null ? name.hashCode() : 0);
        return hashCode;
    }
}
