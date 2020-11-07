package com.shareeverything.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Notification {
    String title;
    String message;
    String action;
}

