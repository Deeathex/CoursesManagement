package com.server.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "lectures")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "filename")
    private String filename;

    @Column(name = "attachment")
    @Lob
    private Blob attachment;

    @Column(name = "date")
    private Date date;

    @OneToMany(mappedBy = "lectures", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> professors = new ArrayList<>();

    @OneToMany(mappedBy = "lectures", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses = new ArrayList<>();
}
