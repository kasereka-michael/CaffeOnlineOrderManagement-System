package com.webgroupEproject.myproject23526.Model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 512, nullable = false, unique = true)
    private String name;
    private long size;

    private Date uplaodtime;
    @Lob
    private byte[] content;

    public Document() {
    }

    public Long getId() {
        return id;
    }

    public Document(Long id, String name, long size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getUplaodtime() {
        return uplaodtime;
    }

    public void setUplaodtime(Date uplaodtime) {
        this.uplaodtime = uplaodtime;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
