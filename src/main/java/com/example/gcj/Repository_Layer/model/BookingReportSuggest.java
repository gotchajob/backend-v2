package com.example.gcj.Repository_Layer.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookingReportSuggest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long reportId;
    @ManyToOne(fetch = FetchType.LAZY)
    private ReportSuggest suggest;
}
