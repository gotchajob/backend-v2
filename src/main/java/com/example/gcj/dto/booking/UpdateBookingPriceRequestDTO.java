package com.example.gcj.dto.booking;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NotNull
public class UpdateBookingPriceRequestDTO {
    @Min(value = 5_000, message = "price cannot be smaller than 5000vnd")
    @Max(value = 1_000_000_000, message = "price cannot be larger than 1,000,0000vnd")
    private long price;
}
