package com.example.loginpage;

public class ResidentInformationSearch {
    String firstName,lastName, FatherName, Nid, mobile, email, password, dob, address;

    int id;

    public ResidentInformationSearch(int id,String firstName, String lastName, String fatherName, String nid, String mobile, String email, String password, String dob, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.FatherName = fatherName;
        this.Nid = nid;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.address = address;
        this.id=id;
    }
    public ResidentInformationSearch(int id) {
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFatherName() {
        return FatherName;
    }

    public void setFatherName(String fatherName) {
        FatherName = fatherName;
    }

    public String getNid() {
        return Nid;
    }

    public void setNid(String nid) {
        Nid = nid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
