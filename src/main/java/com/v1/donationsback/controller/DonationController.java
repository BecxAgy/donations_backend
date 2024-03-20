package com.v1.donationsback.controller;


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
    public ResponseEntity<List<DonationModel>> listarDoacoes() {
        List<DonationModel> doacoes = donationService.listarDoacoes();
        return ResponseEntity.ok(doacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationModel> buscarDoacaoPorId(@PathVariable Long id) {
        DonationModel doacao = donationService.buscarDoacaoPorId(id);
        return ResponseEntity.ok(doacao);
    }

    @PostMapping
    public ResponseEntity<DonationModel> salvarDoacao(@RequestBody DonationModel doacao) {
        DonationModel novaDoacao = donationService.salvarDoacao(doacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaDoacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDoacao(@PathVariable Long id) {
        donationService.deletarDoacao(id);
        return ResponseEntity.noContent().build();
    }
}
