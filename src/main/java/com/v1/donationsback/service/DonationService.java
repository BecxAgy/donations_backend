package com.v1.donationsback.service;

import com.v1.donationsback.dto.DonationDTO;
import com.v1.donationsback.models.CategoryModel;
import com.v1.donationsback.models.DonationModel;
import com.v1.donationsback.models.StatusModel;
import com.v1.donationsback.repository.DonationRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private CategoryService categoryService;

    public List<DonationModel> findAll() {
        return donationRepository.findAll();
    }

    public Optional<DonationModel> findDonationById(Long id) {
        return donationRepository.findById(id);

        //tratar
    }
    @Transactional
    public DonationModel saveDonation(DonationDTO donationDTO) {
        DonationModel donation = convertDtoToModel(donationDTO);
        //setando a data atual
        donation.setCreated_at(new Timestamp(System.currentTimeMillis()));
        //ativando o status, inicialmente ativo
        StatusModel status = new StatusModel(Long.valueOf(2), "ativo");
        System.out.println("status"+ status.getId());
        donation.setStatus(status);

        return donationRepository.save(donation);
    }

    public DonationModel updateDonation( DonationModel oldDonation, DonationDTO updatedDonationDTO){
        //os campos ja vem atualizados, falta procurar so a categoria associada e pegar status
        oldDonation.setDescription(updatedDonationDTO.description());
        oldDonation.setTitle(updatedDonationDTO.title());
        oldDonation.setQuantity(updatedDonationDTO.quantity());
        if(oldDonation.getCategory().getId() != updatedDonationDTO.category_id()){
            CategoryModel category = categoryService.findById(oldDonation.getId());
            oldDonation.setCategory(category);
        }
        //nao alteramos status aqui
        return donationRepository.save(oldDonation);

    }


    private DonationModel convertDtoToModel(DonationDTO donationDTO) {
        DonationModel donation = new DonationModel();
        donation.setTitle(donation.getTitle());
        donation.setDescription(donationDTO.description());
        donation.setQuantity(donationDTO.quantity());

        CategoryModel category = categoryService.findById(donationDTO.category_id());
        donation.setCategory(category);

        return donation;
    }
    @Transactional
    public void deleteDonation(Long id) {
        donationRepository.deleteById(id);
    }

}
