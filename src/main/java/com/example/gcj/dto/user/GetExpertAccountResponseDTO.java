package com.example.gcj.dto.user;

import com.example.gcj.model.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetExpertAccountResponseDTO {
    private List<ExpertAccountResponse> list;
    private long total;

}
