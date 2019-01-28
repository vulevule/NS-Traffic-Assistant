package com.team9.NSTrafficAssistant.service;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.repository.LineRepository;
import com.team9.service.LineService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LineServiceUnitTest {

	@Autowired
	private LineService lineService;
	
	@MockBean
	private LineRepository lineRepoMocked;
	
	@Before
	public void setUp() {
		
	}

}
