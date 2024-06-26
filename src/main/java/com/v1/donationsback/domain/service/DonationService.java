package com.v1.donationsback.domain.service;

import com.v1.donationsback.dto.DonationDTO;
import com.v1.donationsback.exceptions.DonationNotFoundException;
import com.v1.donationsback.domain.models.CategoryModel;
import com.v1.donationsback.domain.models.DonationModel;
import com.v1.donationsback.domain.repository.DonationRepository;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.util.List;

import java.util.Optional;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private GoogleDriveService driveService;

    public List<DonationModel> findAll() {
        return donationRepository.findAll();
    }

    public DonationModel findDonationById(Long id) {
        Optional<DonationModel>optDonation = donationRepository.findById(id);

        if(optDonation.isPresent()){
            DonationModel donation = optDonation.get();
            return donation;
        }
        //notfounddonationexception
        throw new DonationNotFoundException(id);

    }
    @Transactional
    public DonationModel saveDonation(DonationDTO donationDTO, MultipartFile image) throws IOException, GeneralSecurityException {
        DonationModel donation = convertDtoToModel(donationDTO);
        if(image.isEmpty()) {
            throw new RuntimeException("Image is mandatory");
        }
        File tempFile = File.createTempFile("temp", null);
        image.transferTo(tempFile);
        String imagerUrl = driveService.uploadImageToDrive(tempFile);
        donation.setImageUrl(imagerUrl);
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
        donation.setTitle(donationDTO.title());
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
