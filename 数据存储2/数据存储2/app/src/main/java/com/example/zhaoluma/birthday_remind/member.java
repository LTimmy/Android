package com.example.zhaoluma.birthday_remind;

/**
 * Created by zhaoluma on 2017/12/7.
 */

public class member {
    private String name;
    private String birthday;
    private String gift;

    member(String n, String b, String g) {
        name = n;
        birthday = b;
        gift = g;
    }

    public void setName(String n) {name = n;}
    public String getName() {return name;}

    public void setBirthday(String b) {birthday = b;}
    public String getBirthday() {return birthday;}

    public void setGift(String g) {gift = g;}
    public String getGift() {return gift;}
}
