package com.carshop.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String city;
    private String birthDate;
    private Double latitude;
    private Double longitude;
    private String profileImageUrl;
}
