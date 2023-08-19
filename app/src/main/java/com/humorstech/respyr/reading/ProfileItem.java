package com.humorstech.respyr.reading;

public class ProfileItem {


    String name;
    int id;
    String gender;

    public ProfileItem(String name, int id, String gender, String age) {
        this.name = name;
        this.id = id;
        this.gender = gender;
        this.age = age;
    }

    String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
