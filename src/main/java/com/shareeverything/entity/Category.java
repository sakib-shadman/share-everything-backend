package com.shareeverything.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BasicEntity {

    String name;

   /* @OneToMany(fetch = FetchType.LAZY)
    SubCategory subCategory;*/
}
