package com.shareeverything.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zone extends BasicEntity {

    String name;
}
