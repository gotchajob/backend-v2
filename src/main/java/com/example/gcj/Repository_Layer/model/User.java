package com.example.gcj.Repository_Layer.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@NamedEntityGraph(name = "User.noAssociations",
        attributeNodes = {
                @NamedAttributeNode("email"),
                @NamedAttributeNode("password"),
                @NamedAttributeNode("firstName"),
                @NamedAttributeNode("lastName"),
                @NamedAttributeNode("phone"),
                @NamedAttributeNode("address"),
                @NamedAttributeNode("avatar"),
                @NamedAttributeNode("status"),
                @NamedAttributeNode("roleId")
        }
)
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

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Expert expert;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Account account;

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
