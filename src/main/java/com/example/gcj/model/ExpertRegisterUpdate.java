package com.example.gcj.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExpertRegisterUpdate extends AbstractEntity {
    private long requestId;
    private String note;
    private String updateUrl;

    @Min(1) @Max(2)
    private Integer status;
    private Integer count;

}
