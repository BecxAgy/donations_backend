package com.v1.donationsback.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DonationDTO(@NotBlank(message = "Title is mandatoru") String title, @NotBlank(message = "Description is mandatory") String description, @Positive int quantity, @NotNull Long category_id) {
}
