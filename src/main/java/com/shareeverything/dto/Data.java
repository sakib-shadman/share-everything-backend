package com.shareeverything.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Data {

    String title;
    String message;
    String action;
}
