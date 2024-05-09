package com.example.gcj.dto.user;

import com.example.gcj.model.User;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetMentorAccountResponseDTO{
    private List<MentorAccountResponse> list;
    private long total;

    public GetMentorAccountResponseDTO(List<User> users, long total) {
        this.list = new ArrayList<>();
        if (!users.isEmpty()) {
            for (User user : users) {
                MentorAccountResponse mentorAccountResponse = new MentorAccountResponse(user);
                this.list.add(mentorAccountResponse);
            }
        }

        this.total = total;
    }
}
