package com.team9.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team9.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

}
