package com.webgroupEproject.myproject23526.Model;


import jakarta.persistence.*;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class UserClient implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "required")
    private String fullname;

    @NotNull(message = "required")
    private String companyname;

    @NotNull(message = "required")
    @Email(message = "Invalid email format")
    private String email;

    @Column(columnDefinition = "TEXT")
    private String explain_order;
    @NotBlank(message = "required")
    private String address;

    @NotNull(message = "Phone number is required")
    @Size(min = 10, max = 10, message = "Phone number should be 10 digits")
    private String phonenumber;
    private Date date;

    public UserClient() {

    }


    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExplain_order() {
        return explain_order;
    }

    public void setExplain_order(String explain_order) {
        this.explain_order = explain_order;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    }

