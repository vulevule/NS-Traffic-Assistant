package com.team9.service;

import com.team9.model.Address;

public interface AddressService {
	
	Address findByStreetAndCityAndZip(String street, String city, int zip);

}
