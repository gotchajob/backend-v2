package com.example.gcj.Service_Layer.dto.cv;

import jakarta.validation.constraints.Min;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCvRequestDTO implements Serializable {
    @Min(1)
    private long cvTemplateId;
}
