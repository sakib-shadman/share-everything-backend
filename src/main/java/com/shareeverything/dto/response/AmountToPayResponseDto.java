package com.shareeverything.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmountToPayResponseDto {

    String startDate;
    String endDate;
    Double amountToPay;
}
