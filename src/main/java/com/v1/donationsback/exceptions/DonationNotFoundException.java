package com.v1.donationsback.exceptions;

public class DonationNotFoundException extends RuntimeException{
    public DonationNotFoundException(Long id){
    super(String.format("Donation with id %d not found", id));
    }
}
