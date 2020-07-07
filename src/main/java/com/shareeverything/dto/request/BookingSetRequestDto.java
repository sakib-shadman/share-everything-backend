package com.shareeverything.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingSetRequestDto {

    String postId;
    String startDate;
    String endDate;
    Double amountToPay;
}
