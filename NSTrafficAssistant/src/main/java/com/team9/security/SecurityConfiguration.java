package com.team9.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.team9.model.Role;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	
	@Autowired
	public void configureAuthentication(
			AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
		authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean 
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	
	@Bean
	public AuthenticationTokenFilter authenticationTokenFilterBean()
			throws Exception {
		AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
		authenticationTokenFilter
				.setAuthenticationManager(authenticationManagerBean());
		return authenticationTokenFilter;
	}
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
//		httpSecurity
//		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//		//za neautorizovane zahteve posalji 401 gresku
//		.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
//		.authorizeRequests()
//		//svim korisnicima dopustiti da pristupe loginu i registraciji, i cenovniku
//		.antMatchers("/user/login").permitAll()
//		.antMatchers("/user/create").permitAll()
//		.antMatchers("/pricelist/getPricelist").permitAll()
//		//svaki zahtev mora biti autorizovan
//		.anyRequest().authenticated().and()
		httpSecurity
			.csrf().disable()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
			.authorizeRequests()
				.antMatchers("/user/login").permitAll()
					.antMatchers("/user/create").permitAll()
					.antMatchers("/pricelist/addPricelist").hasAuthority("ADMIN")
					.antMatchers("/ticket/buyTicket").hasAuthority(Role.PASSENGER.name())
					.antMatchers("/ticket/myTicket").hasAuthority(Role.PASSENGER.name())
					.antMatchers("/pricelist/getPricelist").permitAll()
					.antMatchers("/ticket/useTicket").hasAuthority(Role.PASSENGER.name())
					.antMatchers("/ticket/checkTicket").hasAuthority(Role.INSPECTOR.name())
					.antMatchers("/ticket/monthReport").hasAuthority(Role.ADMIN.name())
					.antMatchers("/station/create").hasAuthority(Role.ADMIN.name())
					.antMatchers("/station/update").hasAuthority(Role.ADMIN.name())
					.antMatchers("/station/delete/{id}").hasAuthority(Role.ADMIN.name())

				.anyRequest().authenticated();

				//if we use AngularJS on client side
				//.and().csrf().csrfTokenRepository(csrfTokenRepository()); 
		
//		// Custom JWT based authentication
		httpSecurity.addFilterBefore(authenticationTokenFilterBean(),
				UsernamePasswordAuthenticationFilter.class);
		httpSecurity.csrf().disable();
	} 
	
}		
