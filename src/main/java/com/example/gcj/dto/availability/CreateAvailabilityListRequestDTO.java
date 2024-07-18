package com.example.gcj.dto.availability;

import lombok.*;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAvailabilityListRequestDTO {
    private List<CreateAvailabilityRequestDTO> request;
}
