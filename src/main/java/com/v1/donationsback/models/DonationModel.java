package com.v1.donationsback.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@NoArgsConstructor
@Entity
@Data
@Table(name = "donations")
public class DonationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryModel category;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusModel status;
    @Column(nullable = false, updatable = false)
    private Timestamp created_at;
}
