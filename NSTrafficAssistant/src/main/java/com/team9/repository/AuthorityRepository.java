package com.team9.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team9.model.Authority;
import com.team9.model.User;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	Authority findByUser(User user);

}
