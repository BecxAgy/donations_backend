package com.v1.donationsback.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DonationDTO(@NotBlank String description, @NotNull int quantity,@NotNull Long category_id) {
}
