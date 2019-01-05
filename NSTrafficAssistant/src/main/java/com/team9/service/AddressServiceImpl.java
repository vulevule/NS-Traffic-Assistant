package com.team9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team9.model.Address;
import com.team9.repository.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService{
	
	@Autowired
	private AddressRepository addressRepository;

	@Override
	public Address findByStreetAndCityAndZip(String street, String city, int zip) {
		return addressRepository.findByStreetAndCityAndZip(street, city, zip);
	}

	

}
