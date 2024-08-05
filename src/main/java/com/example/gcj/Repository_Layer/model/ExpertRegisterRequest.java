package com.example.gcj.Repository_Layer.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpertRegisterRequest extends AbstractEntity {
    private String email;
    private String url;
    private String note;
    private int status;
    private Long expertId;
}
