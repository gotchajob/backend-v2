package com.example.gcj.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User extends AbstractEntity{
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String avatar;
    private int status;
    private int roleId;

    @OneToOne(mappedBy = "user")
    private MentorProfile mentorProfile;

    @OneToMany(mappedBy = "author")
    private List<Blog> blogs;


    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = 1;
        this.roleId = 2;
    }

    public User(long id) {
        this.setId(id);
    }
}
