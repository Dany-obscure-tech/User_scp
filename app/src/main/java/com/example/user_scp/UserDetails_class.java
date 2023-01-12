package com.example.user_scp;

class UserDetails_class {
    // Static variable reference of single_instance
    // of type Singleton
    private static UserDetails_class userDetails_class = null;

    // Declaring a variable of type String

    public String token;
    public String name;
    public String parking;
    public String car_no;
    public String ph_no;

    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private UserDetails_class()
    {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getCar_no() {
        return car_no;
    }

    public void setCar_no(String car_no) {
        this.car_no = car_no;
    }

    public String getPh_no() {
        return ph_no;
    }

    public void setPh_no(String ph_no) {
        this.ph_no = ph_no;
    }

    // Static method
    // Static method to create instance of Singleton class
    public static UserDetails_class getInstance()
    {
        if (userDetails_class == null)
            userDetails_class = new UserDetails_class();

        return userDetails_class;
    }
}
