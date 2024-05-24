package com.example.gcj.dto.user;

import com.example.gcj.model.User;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class UserProfileDTO implements Serializable {
    private long id;
    private String avatar;
    private String fullName;
}
