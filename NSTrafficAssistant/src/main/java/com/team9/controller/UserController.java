
package com.team9.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.team9.dto.AddressDto;
import com.team9.dto.EditDto;
import com.team9.dto.LoginDto;
import com.team9.dto.LoginUserDto;
import com.team9.dto.UpdateProfileDto;
import com.team9.dto.UserDto;
import com.team9.dto.ValidationDTO;
import com.team9.exceptions.UserNotFoundException;
import com.team9.model.Inspector;
import com.team9.model.Passenger;
import com.team9.model.User;
import com.team9.security.TokenUtils;
import com.team9.service.UserService;







@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Autowired
	TokenUtils tokenUtils;
	
	@RequestMapping(
			value = "/user/create", 
			method=RequestMethod.POST, 
			consumes="application/json",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addUser(@RequestBody UserDto user){
		try {
		
		if(userService.getUser(user.getUsername()) != null) {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
		userService.saveUser(userService.UserDtoToUser(user));
//		if(user.getRole().equalsIgnoreCase("PASSENGER")) {
//			
//			userService.sendNotificaitionSync(user);
//		}
//		
		return new ResponseEntity<String>(HttpStatus.CREATED);}
catch(Exception ex) {
			
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
			
		}
		
		
	}
	
	
	@RequestMapping(
			value="/user/login",
			method=RequestMethod.POST,
			consumes="application/json",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoginUserDto> login(@RequestBody LoginDto log){
		logger.info(">> login: username - " + log.getUsername() + " password - " + log.getPassword());
		
		try {
			
			
			UsernamePasswordAuthenticationToken token = 
        			new UsernamePasswordAuthenticationToken(
					log.getUsername(), log.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            HttpHeaders headers = new HttpHeaders();
           
            
            // Reload user details so we can generate token
            UserDetails details = userDetailsService.
            		loadUserByUsername(log.getUsername());
            
            String authToken = tokenUtils.generateToken(details);
            headers.add("X-Auth-Token", authToken);
            String userRole = "";
            LoginUserDto lu = new LoginUserDto();
            User user=userService.getUser(log.getUsername());
            logger.info(user.getUsername() + ' ' + user.getPassword());
    		if (user!=null){
    			userRole = user.getRole().name();
    			logger.info(userRole);
    			lu.setRole(userRole);
    			lu.setToken(authToken);
    		}else{
    			logger.info("not exists user");
    			return new ResponseEntity<LoginUserDto>(lu,HttpStatus.BAD_REQUEST);
    		}
    			
            
            
            
            logger.info("<< ok login");
            return new ResponseEntity<LoginUserDto>(lu,headers,  HttpStatus.OK);
			
			
		
		/*User user=userService.getUser(log.getUsername(), log.getPassword());
		if (user==null) {
			
			return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);*/
		
		}catch(Exception ex) {
			logger.info("invalid login");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		}
	
	
	
	
	@RequestMapping(value="/user/profileUpdate",method=RequestMethod.PUT, consumes="application/json")
	public ResponseEntity<User> updateProfile(@RequestBody UpdateProfileDto profileDTO){
		
		User user = userService.UpdateDtoToUser(profileDTO); 
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		
		boolean updated=userService.SaveUpdated(user);
		
		if(updated==true) {
		return new ResponseEntity<User>(user, HttpStatus.OK);
		
		
		}
	
		
		
	
	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

}
	
	
	@RequestMapping(value="/user/profileValidated",method=RequestMethod.PUT, consumes="application/json")
	public ResponseEntity<User> profileValidatedByAdmin(@RequestBody ValidationDTO u,HttpServletRequest request){
		
		Passenger pas=null;
  		User user=getLoggedUser(request);
  		Inspector insp=(Inspector)user;
		
		try {
			pas = userService.validationProcess(u);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (pas == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		pas.setInspector(insp);
	
		boolean updated=userService.SaveUpdated(pas);
		
		if(updated==true) {
		return new ResponseEntity<User>(pas, HttpStatus.OK);
		
		
		}
		
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	
	@RequestMapping(value="/user/validateProfile",method=RequestMethod.POST)
	public ResponseEntity<?> validateProfile(@RequestParam("file") MultipartFile file,HttpServletRequest request) throws IOException  {
		System.out.println("Tuuu");
		
	
		   if (!file.isEmpty())
		     { String path="";
		      String orgName = file.getOriginalFilename();
			  // this line to retreive just file name 
			  
      String name=orgName.substring(orgName.lastIndexOf("\\")+1,orgName.length());
      path= request.getServletContext().getRealPath("/images/");
      if(! new File(path).exists())
            {
			   new File(path).mkdir();
            }
    
     // path ="/static/images/";
      System.out.println(path);
      User user=getLoggedUser(request);
      userService.store(file);

			user.setImagePath(file.getOriginalFilename());
			userService.SaveUpdated(user);
			
			return new ResponseEntity<>(HttpStatus.CREATED);
		      
		      
		        
		    }
		   else {
			   
			   
			   return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		   }
		
		   
		
		
		
	}
	
	
	@GetMapping(value="/user/notValidated", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Passenger>> getNotValidatedUsers() {
		return new ResponseEntity<ArrayList<Passenger>>(userService.readyToValidate(),HttpStatus.OK);	
	
	}
	
	public User getLoggedUser(HttpServletRequest http) {
		HttpServletRequest httpRequest = (HttpServletRequest) http;
  		String authToken = httpRequest.getHeader("X-Auth-Token");
  		String username = this.tokenUtils.getUsernameFromToken(authToken);
  		User user=userService.getUser(username);
  		return user;
		
		
	}
	
	@GetMapping(value="/user/getUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EditDto> getUser(HttpServletRequest http) {
		User u=getLoggedUser(http);
		System.out.println(u.getName());
		EditDto user=new EditDto();
		user.setUsername(u.getUsername());
		user.setEmail(u.getEmail());
		user.setName(u.getName());
		user.setPassword(u.getPassword());
		AddressDto add=new AddressDto();
		add.setStreet(u.getAddress().getStreet());
		add.setCity(u.getAddress().getCity());
		add.setZip(u.getAddress().getZip());
		user.setAddress(add);
		
		
		
		
		
		
		return new ResponseEntity<EditDto>(user,HttpStatus.OK);
		
		
	}
}