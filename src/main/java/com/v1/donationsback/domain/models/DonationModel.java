package com.v1.donationsback.domain.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.lang.annotation.ElementType;
import java.sql.Blob;
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
    @Lob
    private byte[] image;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryModel category;

    @Column(nullable = false, updatable = false)
    private Timestamp created_at;
}
