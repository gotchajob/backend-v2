package com.example.gcj.dto.expert_register_request;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class BanEmailRequestDTO implements Serializable {
    private String email;
}
