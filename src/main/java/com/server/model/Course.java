package com.server.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String year;

    @Column
    private Long maxStudents;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Collection<Lecture> lectures = new ArrayList<>();

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private Collection<User> users = new ArrayList<>();
}
