package com.example.gcj.Repository_Layer.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Blog extends AbstractEntity {
    private String thumbnail;
    private String title;
    private String shortDescription;
    private String content;
    private int status;
    private long authorId;

    @ManyToOne(fetch = FetchType.LAZY)
    private BlogCategory category;

    public Blog(long id) {
        this.setId(id);
    }
}
