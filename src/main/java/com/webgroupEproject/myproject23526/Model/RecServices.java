package com.webgroupEproject.myproject23526.Model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;


import jakarta.validation.constraints.*;
import java.io.Serializable;

import java.util.Date;


/**
 *
 * @author michael
 */
@Entity
public class RecServices implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull(message = "required")
    private String prodname;
    @NotNull(message = "required")
    private String prodquantity;
    @NotNull(message = "required")
    private String prodcomment;
    @NotNull(message = "required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date recdate;

    @NotNull(message = "required")
    private String currentStatus;

    public RecServices() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProdname() {
        return prodname;
    }

    public void setProdname(String prodname) {
        this.prodname = prodname;
    }

    public String getProdquantity() {
        return prodquantity;
    }

    public void setProdquantity(String prodquantity) {
        this.prodquantity = prodquantity;
    }

    public String getProdcomment() {
        return prodcomment;
    }

    public void setProdcomment(String prodcomment) {
        this.prodcomment = prodcomment;
    }

    public Date getRecdate() {
        return recdate;
    }

    public void setRecdate(Date recdate) {
        this.recdate = recdate;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }



}
