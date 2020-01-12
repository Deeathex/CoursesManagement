package com.server.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collection;

@Data

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String surname;

    @Column(columnDefinition = "mediumblob")
    @Lob
    private Blob picture;

    @ManyToOne
    @JoinColumn(name = "user_role_id", nullable = false)
    UserRole userRole;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "courses_management",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")}
    )
    Collection<Course> courses = new ArrayList<>();
}
