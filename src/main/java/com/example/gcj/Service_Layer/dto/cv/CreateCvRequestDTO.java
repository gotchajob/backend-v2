package com.example.gcj.Service_Layer.dto.cv;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCvRequestDTO implements Serializable {
    private long cvTemplateId;
}
