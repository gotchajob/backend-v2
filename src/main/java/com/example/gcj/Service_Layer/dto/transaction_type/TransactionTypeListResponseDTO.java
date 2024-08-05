package com.example.gcj.Service_Layer.dto.transaction_type;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransactionTypeListResponseDTO {
    private long id;
    private String type;
    private String description;
}
