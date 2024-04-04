package com.v1.donationsback.controller;


import com.v1.donationsback.dto.DonationDTO;
import com.v1.donationsback.models.DonationModel;
import com.v1.donationsback.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doacoes")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @GetMapping
    public ResponseEntity<List<DonationModel>> list() {
        List<DonationModel> doacoes = donationService.listDonations();
        return ResponseEntity.ok(doacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationModel> find(@PathVariable Long id) {
        DonationModel doacao = donationService.findDonationById(id);
        return ResponseEntity.ok(doacao);
    }

    @PostMapping
    public ResponseEntity<DonationModel> save(@RequestBody DonationDTO donationDTO) {
        DonationModel novaDoacao = donationService.saveDonation(donationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaDoacao);
    }
//    @PutMapping("/{id}")
//    public ResponseEntity<DonationModel> update(@RequestBody DonationDTO donationDTO) {
//
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        donationService.deleteDonation(id);
        return ResponseEntity.noContent().build();
    }
}
