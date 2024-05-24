package com.example.gcj.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Faq extends AbstractEntity{
    private String question;
    private String answer;
    private String status;
}
