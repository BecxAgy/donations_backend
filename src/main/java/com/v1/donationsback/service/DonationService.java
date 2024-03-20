package com.v1.donationsback.service;

import com.v1.donationsback.models.DonationModel;
import com.v1.donationsback.models.StatusModel;
import com.v1.donationsback.repository.DonationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    public List<DonationModel> listarDoacoes() {
        return donationRepository.findAll();
    }

    public DonationModel buscarDoacaoPorId(Long id) {
        return donationRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Doação não encontrada"));
    }

    public DonationModel salvarDoacao(DonationModel doacao) {
        doacao.setCreated_at(new Timestamp(System.currentTimeMillis()));
        //ativando o status, inicialmente ativo
        StatusModel status = new StatusModel(Long.valueOf(2), "ATIVO");
        doacao.setId(status.getId());

        return donationRepository.save(doacao);
    }

    public void deletarDoacao(Long id) {
        donationRepository.deleteById(id);
    }
}
