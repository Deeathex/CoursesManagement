package com.server.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Date;

@Data

@Entity
@Table(name = "lectures")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    @Column
    private String title;

    @Column
    private String filename;

    @Lob
    @Column(columnDefinition = "mediumblob")
    private byte[] attachment;

    @Column
    private Date date;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
