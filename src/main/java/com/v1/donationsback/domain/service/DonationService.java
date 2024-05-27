package com.v1.donationsback.domain.service;

import com.v1.donationsback.dto.DonationDTO;
import com.v1.donationsback.exceptions.DonationNotFoundException;
import com.v1.donationsback.domain.models.CategoryModel;
import com.v1.donationsback.domain.models.DonationModel;
import com.v1.donationsback.domain.repository.DonationRepository;

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

    public DonationModel findDonationById(Long id) {
        Optional<DonationModel>optDonation =donationRepository.findById(id);

        if(optDonation.isPresent()){
            return optDonation.get();
        }
        //notfounddonationexception
        throw new DonationNotFoundException(id);

    }
    @Transactional
    public DonationModel saveDonation(DonationDTO donationDTO) {
        DonationModel donation = convertDtoToModel(donationDTO);
        //setando a data atual
        donation.setCreated_at(new Timestamp(System.currentTimeMillis()));

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
        findDonationById(id);
        donationRepository.deleteById(id);
    }

}
