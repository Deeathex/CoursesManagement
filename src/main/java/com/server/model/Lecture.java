package com.server.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

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
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
