package com.gdg.jpq.maltratocero.model;

public class User {
    private String fullname;
    private String dni;
    private String birthdate;
    private String telephone;
    private String mobilephone;
    private String address;
    private String email;
    private String party;

    public User() {}

    public User(String fullname, String dni, String birthdate, String telephone, String mobilephone, String address, String email, String party) {
        this.fullname = fullname;
        this.dni = dni;
        this.birthdate = birthdate;
        this.telephone = telephone;
        this.mobilephone = mobilephone;
        this.address = address;
        this.email = email;
        this.party = party;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {this.telephone = telephone;}

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }
}
