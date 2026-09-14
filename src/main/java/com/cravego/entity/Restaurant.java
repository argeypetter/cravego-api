package com.cravego.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(nullable = false,length = 255)
    private String address;

    @Column(length = 20)
    private String phone;

    @Column(length = 255)
    private String image;

    @Column(nullable = false)
    private boolean active;
}
