package com.v1.donationsback.domain.repository;


import com.v1.donationsback.domain.models.DonationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends JpaRepository<DonationModel, Long> {
}
