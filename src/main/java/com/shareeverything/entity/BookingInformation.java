package com.shareeverything.entity;

import com.shareeverything.constant.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingInformation extends BasicEntity {

    @ManyToOne
    User user;

    @ManyToOne
    Post post;

    @Enumerated(EnumType.STRING)
    BookingStatus bookingStatus;

    @Temporal(TemporalType.TIMESTAMP)
    Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    Date endDate;

    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    Date updatedAt;

}