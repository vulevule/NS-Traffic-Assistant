package com.team9.repository;

import com.team9.model.Address;

public interface AddressRepository {
	
	Address findByStreetAndCityAndZip(String street, String city, Integer zip);

}
