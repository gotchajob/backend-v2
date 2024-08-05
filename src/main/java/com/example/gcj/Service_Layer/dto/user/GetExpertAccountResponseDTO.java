package com.example.gcj.Service_Layer.dto.user;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetExpertAccountResponseDTO {
    private List<ExpertAccountResponse> list;
    private long total;

}
