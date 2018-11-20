package com.team9.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.team9.model.User;
import com.team9.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User u = userRepository.findUserByUsername(username);
		if (u == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));

		} else {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(u.getAuthority().getName());
			ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(grantedAuthority);

			return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(),
					authorities);
		}

	}

}
