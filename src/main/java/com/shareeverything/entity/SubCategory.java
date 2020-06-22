package com.shareeverything.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubCategory extends BasicEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    Category category;

    String name;
}
