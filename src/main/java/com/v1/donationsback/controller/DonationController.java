package com.v1.donationsback.controller;


import com.v1.donationsback.domain.service.GoogleDriveService;
import com.v1.donationsback.dto.DonationDTO;
import com.v1.donationsback.domain.models.DonationModel;
import com.v1.donationsback.domain.service.DonationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.File;
import java.security.GeneralSecurityException;
import java.util.List;


@RestController
@RequestMapping("/api/doacoes")
public class DonationController {

    @Autowired
    private DonationService donationService;


    @GetMapping
    public ResponseEntity<List<DonationModel>> list() {
        //implementar paginação
        List<DonationModel> doacoes = donationService.findAll();
        return ResponseEntity.ok(doacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationModel> find(@PathVariable Long id) {
        DonationModel doacao = donationService.findDonationById(id);
         return ResponseEntity.ok(doacao);

    }

    @PostMapping
    public ResponseEntity<DonationModel> save(@Valid @ModelAttribute DonationDTO donationDTO) throws GeneralSecurityException, IOException {

        DonationModel novaDoacao = donationService.saveDonation(donationDTO, donationDTO.image());
        return ResponseEntity.status(HttpStatus.CREATED).body(novaDoacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonationModel> update(@PathVariable Long id,@Valid  @RequestBody DonationDTO updatedDonationDTO) {
        DonationModel oldDonation = donationService.findDonationById(id);

        DonationModel donationModel = donationService.updateDonation(oldDonation, updatedDonationDTO );
        return ResponseEntity.status(HttpStatus.OK).body(donationModel);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        donationService.deleteDonation(id);
        //verificar se existe
        return ResponseEntity.noContent().build();
    }
}
