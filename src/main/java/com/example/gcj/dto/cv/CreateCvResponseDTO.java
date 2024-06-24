package com.example.gcj.dto.cv;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class CreateCvResponseDTO implements Serializable {
    private long id;
}
