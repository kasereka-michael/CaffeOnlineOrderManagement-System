package com.webgroupEproject.myproject23526.Model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;


import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author michael
 */
@Entity
public class RecServices implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "required")
    private String prodname;
    @NotBlank(message = "required")
    private String prodquantity;
    @NotBlank(message = "required")
    private String prodcomment;
    @NotBlank(message = "required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date recdate;
    @NotBlank(message = "required")
    private String currentStatus;
    @Size(max=10000000, message="File size must be less than 10MB")
    private byte[] productphoto;

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

    public byte[] getProductphoto() {
        return productphoto;
    }

    public void setProductphoto(byte[] productphoto) {
        this.productphoto = productphoto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecServices that)) return false;
        return id == that.id && prodname.equals(that.prodname) && prodquantity.equals(that.prodquantity) && prodcomment.equals(that.prodcomment) && recdate.equals(that.recdate) && currentStatus.equals(that.currentStatus) && Arrays.equals(productphoto, that.productphoto);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, prodname, prodquantity, prodcomment, recdate, currentStatus);
        result = 31 * result + Arrays.hashCode(productphoto);
        return result;
    }
}
