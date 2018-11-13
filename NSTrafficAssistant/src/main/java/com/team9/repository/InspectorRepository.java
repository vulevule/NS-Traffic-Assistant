package com.team9.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team9.model.Inspector;

public interface InspectorRepository extends JpaRepository<Inspector, Long>{

	Inspector findByUsername(String userName);
}
