package com.team9.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team9.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{
	
	Address findByStreetAndCityAndZip(String street, String city, Integer zip);

}
