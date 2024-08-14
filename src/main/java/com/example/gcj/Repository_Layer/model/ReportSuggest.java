package com.example.gcj.Repository_Layer.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReportSuggest extends AbstractEntity {
    private String report;
    private String description;
    private int status;

    public ReportSuggest(long id) {
        this.setId(id);
    }
}
