package com.carshop.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    private String title;
    private String price;
    private String year;
    private String mileage;
    private String variant;
    private String imageUrl;

    private Double meetingLat;
    private Double meetingLng;

    private LocalDateTime createdAt = LocalDateTime.now();
}
