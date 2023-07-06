package com.jagrutijadhav.ajp;

public class Student {
    public Student(){}
    String name;
    String username;
    String password;
    String email;
    String urlname;
    String phoneNo;
    String color_;
    String friend;
    String ajprp;
    String manrp;
    String estrp;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }

    public void setColor_(String color_) { this.color_ = color_; }

    public void setFriend(String friend) { this.friend = friend; }

    public String getAjprp() {
        return ajprp;
    }

    public void setAjprp(String ajprp) {
        this.ajprp = ajprp;
    }

    public void setManrp(String manrp) {
        this.manrp = manrp;
    }

    public void setEstrp(String estrp) {
        this.estrp = estrp;
    }

    public String getManrp() {
        return manrp;
    }

    public String getEstrp() {
        return estrp;
    }

    public String getPhoneNo() { return phoneNo; }


    public String getColor_() { return color_; }

    public String getFriend() { return friend; }

    public Student(String name, String username, String email, String phoneNo, String password, String urlname, String color_, String friend
    ,String ajprp,String estrp,String manrp)
    {
       this.name=name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.urlname = urlname;
        this.color_=color_;
        this.friend=friend;
        this.phoneNo=phoneNo;
        this.ajprp=ajprp;
        this.estrp=estrp;
        this.manrp=manrp;
    }

    public String getUrlname() {
        return urlname;
    }
    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
