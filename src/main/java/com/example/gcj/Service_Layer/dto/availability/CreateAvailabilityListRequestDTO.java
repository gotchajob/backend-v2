package com.example.gcj.Service_Layer.dto.availability;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAvailabilityListRequestDTO {
    private List<CreateAvailabilityRequestDTO> request;
}
