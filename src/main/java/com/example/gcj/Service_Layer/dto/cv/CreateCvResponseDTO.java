package com.example.gcj.Service_Layer.dto.cv;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class CreateCvResponseDTO implements Serializable {
    private long id;
}
