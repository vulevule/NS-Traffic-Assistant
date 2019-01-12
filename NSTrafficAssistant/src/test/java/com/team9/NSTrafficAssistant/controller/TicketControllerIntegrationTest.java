package com.team9.NSTrafficAssistant.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.dto.LoginDto;
import com.team9.dto.ReportDto;
import com.team9.dto.TicketDto;
import com.team9.dto.TicketReaderDto;
import com.team9.model.Ticket;
import com.team9.model.TimeTicketType;
import com.team9.model.UserTicketType;
import com.team9.repository.TicketRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TicketControllerIntegrationTest {

	@Autowired
	private TicketRepository repository;

	@Autowired
	private TestRestTemplate restTemplate;

	private String token_passenger_active;

	private String token_passenger_no_active;

	private String token_admin;

	private String token_inspector;

	@Before
	public void login() {
		ResponseEntity<String> result = restTemplate.postForEntity("/user/login", new LoginDto("laralukic", "7777"),
				String.class);
		token_admin = result.getBody();

		ResponseEntity<String> result_passenger = restTemplate.postForEntity("/user/login",
				new LoginDto("peraperic", "1111"), String.class);
		token_passenger_active = result_passenger.getBody();

		ResponseEntity<String> result_p = restTemplate.postForEntity("/user/login", new LoginDto("mikaperic", "5555"),
				String.class);
		token_passenger_no_active = result_p.getBody();

		ResponseEntity<String> result_inspector = restTemplate.postForEntity("/user/login",
				new LoginDto("lenalukic", "6666"), String.class);
		token_inspector = result_inspector.getBody();
	}

	/*
	 * testiramo kupovinu karata
	 */

	// 1. kada je pogresan tip prevoza
	@Test
	public void test_buyTicket_whenWrongTrafficType() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_active);

		TicketDto t = new TicketDto("month", "first", "plain", 520);
		HttpEntity<TicketDto> httpEntity = new HttpEntity<TicketDto>(t, headers);

		ResponseEntity<String> result = restTemplate.exchange("/ticket/buyTicket", HttpMethod.POST, httpEntity,
				String.class);

		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertTrue(result.getBody().startsWith("Incorect traffic type: "));
	}

	// 2. kada je pogresno vreme
	@Test
	public void test_buyTicket_whenWrongTicketTime() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_active);

		TicketDto t = new TicketDto("weekly", "first", "bus", 520);
		HttpEntity<TicketDto> httpEntity = new HttpEntity<TicketDto>(t, headers);

		ResponseEntity<String> result = restTemplate.exchange("/ticket/buyTicket", HttpMethod.POST, httpEntity,
				String.class);

		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertTrue(result.getBody().startsWith("Incorect time ticket: "));
	}

	// 3. pogresna zona
	@Test
	public void test_buyTicket_whenWrongZone() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_active);

		TicketDto t = new TicketDto("month", "fourth", "bus", 520);
		HttpEntity<TicketDto> httpEntity = new HttpEntity<TicketDto>(t, headers);

		ResponseEntity<String> result = restTemplate.exchange("/ticket/buyTicket", HttpMethod.POST, httpEntity,
				String.class);

		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertTrue(result.getBody().startsWith("Incorect traffic zone: "));
	}

	// 4. kupujemo kartu za aktivnog korisnika
	@Test
	public void test_buyTicket_whenUserIsActive() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_active);

		TicketDto t = new TicketDto("month", "first", "bus", 520);
		HttpEntity<TicketDto> httpEntity = new HttpEntity<TicketDto>(t, headers);

		ResponseEntity<TicketReaderDto> result = restTemplate.exchange("/ticket/buyTicket", HttpMethod.POST, httpEntity,
				TicketReaderDto.class);

		assertEquals(result.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(result.getBody());
		// proverimo cenu
		assertTrue(result.getBody().getPrice() == 900);
		assertTrue(result.getBody().getUserType() == UserTicketType.STUDENT);
		assertTrue(result.getBody().getTimeType() == TimeTicketType.MONTH);
		// proverimo vreme trajanja
		java.sql.Date d = new java.sql.Date(new java.util.Date().getTime());
		// exp date
		java.sql.Date exp = java.sql.Date.valueOf(d.toLocalDate().plusMonths(1));
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		assertTrue(fmt.format(result.getBody().getIssueDate()).equals(fmt.format(d)));
		assertTrue(fmt.format(result.getBody().getExpirationDate()).equals(fmt.format(exp)));
		// izbrisemo kupljenu kartu
		this.repository.deleteById(result.getBody().getId());

	}

	// 5. kupujemo kartu za korisnika koji nije aktivan
	@Test
	public void test_buyTicket_whenUserIsNotActive() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_no_active);

		TicketDto t = new TicketDto("annual", "second", "metro", 520);
		HttpEntity<TicketDto> httpEntity = new HttpEntity<TicketDto>(t, headers);

		ResponseEntity<TicketReaderDto> result = restTemplate.exchange("/ticket/buyTicket", HttpMethod.POST, httpEntity,
				TicketReaderDto.class);

		assertEquals(result.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(result.getBody());
		// proverimo cenu
		assertTrue(result.getBody().getPrice() == 10000);
		assertTrue(result.getBody().getUserType() == UserTicketType.REGULAR);
		assertTrue(result.getBody().getTimeType() == TimeTicketType.ANNUAL);
		// proverimo vreme trajanja
		java.sql.Date d = new java.sql.Date(new java.util.Date().getTime());
		// exp date
		java.sql.Date exp = java.sql.Date.valueOf(d.toLocalDate().plusYears(1));
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		assertTrue(fmt.format(result.getBody().getIssueDate()).equals(fmt.format(d)));
		assertTrue(fmt.format(result.getBody().getExpirationDate()).equals(fmt.format(exp)));
		// izbrisemo kupljenu kartu
		this.repository.deleteById(result.getBody().getId());

	}

	/*
	 * testiramo upotrebu karte
	 */
	// 6. kada karta ne postoji
	@Test
	public void test_useTicket_whenTicketNotFound() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_no_active);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		String serialNo = "BMSKK88778";
		ResponseEntity<String> result = restTemplate.exchange("/ticket/useTicket?serialNo=" + serialNo, HttpMethod.PUT,
				httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
		assertTrue(result.getBody().equals("Ticket with serialNo: " + serialNo + " does not exist!"));
	}

	// 7. kada je single karta vec koriscena
	@Test
	public void test_useTicket_whenTicketAlreadyUse() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_no_active);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		String serialNo = "BMFS12121212024";
		ResponseEntity<String> result = restTemplate.exchange("/ticket/useTicket?serialNo=" + serialNo, HttpMethod.PUT,
				httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertTrue(result.getBody().equals("Ticket with serialNo: " + serialNo + " has already been used!"));
	}

	// 8. kada je karta istekla
	@Test
	public void test_useTicket_whenTicketIsNotValid() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_no_active);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		String serialNo = "BMFS12121212025";
		ResponseEntity<String> result = restTemplate.exchange("/ticket/useTicket?serialNo=" + serialNo, HttpMethod.PUT,
				httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertTrue(result.getBody().equals("Ticket with serialNo: " + serialNo + " has expired!"));
	}

	// 9. koristimo single kartu
	@Test
	@Transactional
	@Rollback(true)
	public void test_useTicket_whenTicketIsSingle() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_no_active);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		String serialNo = "BMFS12121212023";
		ResponseEntity<String> result = restTemplate.exchange("/ticket/useTicket?serialNo=" + serialNo, HttpMethod.PUT,
				httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertTrue(result.getBody().equals("The ticket was successfully used"));
		// mozemo i da pronadjemo tu kartu u bazi i da vidimo da li je setovan
		// atribut used na true
		Ticket t = this.repository.findBySerialNo(serialNo).get();
		assertTrue(t.isUsed());
		// vratimo bazu na staro
		t.setUsed(false);
		this.repository.save(t);
	}

	// 10. koristimo bilo koju drugu kartu koja nije single

	@Test
	public void test_useTicket_OK() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_no_active);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		String serialNo = "BMFS12121212000";
		ResponseEntity<String> result = restTemplate.exchange("/ticket/useTicket?serialNo=" + serialNo, HttpMethod.PUT,
				httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertTrue(result.getBody().equals("The ticket was successfully used"));

	}

	/*
	 * testiramo metodu check ticket
	 */
	// 11. kada karta ne postoji
	@Test
	public void test_checkTicket_whenTicketNotFound() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_inspector);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		String serialNo = "BMSKK88778";
		ResponseEntity<String> result = restTemplate.exchange("/ticket/checkTicket?serialNo=" + serialNo,
				HttpMethod.PUT, httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
		assertTrue(result.getBody().equals("Ticket with serialNo: " + serialNo + " does not exist!"));

	}

	// 12. single karta koja nije koriscena
	@Test
	public void test_checkTicket_whenTicketNotUse() {
		/*
		 * proverim da li je ta karta mozda koriscena
		 * 
		 */
		String serialNo = "BMFS12121212023";
		Ticket t = this.repository.findBySerialNo(serialNo).get();
		assertTrue(t.isUsed());
		t.setUsed(false);
		this.repository.save(t);
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_inspector);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		ResponseEntity<String> result = restTemplate.exchange("/ticket/checkTicket?serialNo=" + serialNo,
				HttpMethod.PUT, httpEntity, String.class);

		assertTrue(result.getStatusCode() == HttpStatus.BAD_REQUEST);
		assertTrue(result.getBody().equals("Ticket with serialNo: " + serialNo + " was not used!"));

	}

	// 13. karta koja je istekla
	@Test
	public void test_checkTicket_whenTicketIsNotValid() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_inspector);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		String serialNo = "BMFS12121212025";
		ResponseEntity<String> result = restTemplate.exchange("/ticket/checkTicket?serialNo=" + serialNo,
				HttpMethod.PUT, httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.CONFLICT);
		assertTrue(result.getBody().equals("Ticket with serialNo: " + serialNo + " has expired!"));

	}

	// 14. kada je sve ok
	@Test
	public void test_checkTicket_OK() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_inspector);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		String serialNo = "BMFS12121212000";
		ResponseEntity<String> result = restTemplate.exchange("/ticket/checkTicket?serialNo=" + serialNo,
				HttpMethod.PUT, httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertTrue(result.getBody().equals("The ticket was successfully checked"));

		Ticket t = this.repository.findBySerialNo(serialNo).get();
		assertTrue(t.getCheckInspectors().size() == 1);
		// vratimo bazu na staro
		t.setCheckInspectors(null);
		this.repository.save(t);

	}

	/*
	 * izvestaj
	 */

	// 15. kada pogresimo neki od parametara
	@Test
	@Transactional
	@Rollback(true)
	public void test_monthReport_invalidParam() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_admin);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		int month = 0;
		int year = 2019;
		ResponseEntity<ReportDto> result = restTemplate.exchange("/ticket/monthReport?month=" + month + "&year=" + year,
				HttpMethod.GET, httpEntity, ReportDto.class);

		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);

	}

	// 16. svi parametri ok
	@Test
	@Transactional
	@Rollback(true)
	public void test_monthReport_OK() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_admin);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		int month = 1;
		int year = 2019;
		ResponseEntity<ReportDto> result = restTemplate.exchange("/ticket/monthReport?month=" + month + "&year=" + year,
				HttpMethod.GET, httpEntity, ReportDto.class);

		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertNotNull(result.getBody());
		assertTrue(result.getBody().getNumOfHandycapSingleTicket() == 2);
		assertTrue(result.getBody().getNumOfHandycapDailyTicket() == 1);
		assertTrue(result.getBody().getNumOfBusTicket() == 3);
		assertTrue(result.getBody().getMoney() == 285);

	}

	/*
	 * sad gledamo pravo pristupa
	 */
	// 17. admin kupuje kartu
	@Test
	@Transactional
	@Rollback(true)
	public void test_buyTicket_admin() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_admin);

		TicketDto t = new TicketDto("month", "first", "bus", 520);
		HttpEntity<TicketDto> httpEntity = new HttpEntity<TicketDto>(t, headers);

		ResponseEntity<TicketReaderDto> result = restTemplate.exchange("/ticket/buyTicket", HttpMethod.POST, httpEntity,
				TicketReaderDto.class);

		assertEquals(result.getStatusCode(), HttpStatus.FORBIDDEN);

	}

	// 18. inspektor kupuje kartu
	@Test
	@Transactional
	@Rollback(true)
	public void test_buyTicket_inspector() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_inspector);

		TicketDto t = new TicketDto("month", "first", "bus", 520);
		HttpEntity<TicketDto> httpEntity = new HttpEntity<TicketDto>(t, headers);

		ResponseEntity<TicketReaderDto> result = restTemplate.exchange("/ticket/buyTicket", HttpMethod.POST, httpEntity,
				TicketReaderDto.class);

		assertEquals(result.getStatusCode(), HttpStatus.FORBIDDEN);

	}

	// 19. putnik gleda izvestaj
	@Test
	@Transactional
	@Rollback(true)
	public void test_monthReport_passenger() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_active);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		int month = 1;
		int year = 2019;
		ResponseEntity<ReportDto> result = restTemplate.exchange("/ticket/monthReport?month=" + month + "&year=" + year,
				HttpMethod.GET, httpEntity, ReportDto.class);

		assertEquals(result.getStatusCode(), HttpStatus.FORBIDDEN);

	}

	// 20. inspektor gleda izvestaj
	@Test
	@Transactional
	@Rollback(true)
	public void test_monthReport_inspector() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_inspector);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		int month = 1;
		int year = 2019;
		ResponseEntity<ReportDto> result = restTemplate.exchange("/ticket/monthReport?month=" + month + "&year=" + year,
				HttpMethod.GET, httpEntity, ReportDto.class);

		assertEquals(result.getStatusCode(), HttpStatus.FORBIDDEN);

	}

	// 21. admin upotrebljava kartu

	@Test
	@Transactional
	@Rollback(true)
	public void test_useTicket_admin() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_admin);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		String serialNo = "BMFS12121212000";
		ResponseEntity<String> result = restTemplate.exchange("/ticket/useTicket?serialNo=" + serialNo, HttpMethod.PUT,
				httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.FORBIDDEN);

	}

	// 22. putnik pregleda kartu
	@Test
	@Transactional
	@Rollback(true)
	public void test_checkTicket_passenger() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_no_active);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		String serialNo = "BMFS12121212000";
		ResponseEntity<String> result = restTemplate.exchange("/ticket/checkTicket?serialNo=" + serialNo,
				HttpMethod.PUT, httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.FORBIDDEN);
	}
}
