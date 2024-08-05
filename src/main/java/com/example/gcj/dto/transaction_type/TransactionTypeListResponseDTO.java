package com.example.gcj.dto.transaction_type;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransactionTypeListResponseDTO {
    private long id;
    private String type;
    private String description;
}
