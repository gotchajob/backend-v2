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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    public Blog(long id) {
        this.setId(id);
    }
}
