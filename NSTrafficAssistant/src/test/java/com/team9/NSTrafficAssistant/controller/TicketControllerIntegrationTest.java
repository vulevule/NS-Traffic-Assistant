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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.dto.LoginDto;
import com.team9.dto.LoginUserDto;
import com.team9.dto.ReportDto;
import com.team9.dto.TicketDto;
import com.team9.dto.TicketReaderDto;
import com.team9.model.Ticket;
import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
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

	private Long ticket1;
	private Long ticket2;

	@Before
	public void login() {
		ResponseEntity<LoginUserDto> result = restTemplate.postForEntity("/user/login",
				new LoginDto("laralukic", "7777"), LoginUserDto.class);
		token_admin = result.getBody().getToken();

		ResponseEntity<LoginUserDto> result_passenger = restTemplate.postForEntity("/user/login",
				new LoginDto("peraperic", "1111"), LoginUserDto.class);
		token_passenger_active = result_passenger.getBody().getToken();

		ResponseEntity<LoginUserDto> result_p = restTemplate.postForEntity("/user/login",
				new LoginDto("mikaperic", "5555"), LoginUserDto.class);
		token_passenger_no_active = result_p.getBody().getToken();

		ResponseEntity<LoginUserDto> result_inspector = restTemplate.postForEntity("/user/login",
				new LoginDto("lenalukic", "6666"), LoginUserDto.class);
		token_inspector = result_inspector.getBody().getToken();
	}

	// get all
	@Test
	@Transactional
	@Rollback(true)
	public void test_getAllTicket1() {
		// pre izvrsavanja cemo izbrisati one dve karte koje smo dodali u
		// prethodnom primeru

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_active);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		String type = "bus";
		String zone = "first";
		String time = "month";
		ResponseEntity<TicketReaderDto[]> result = restTemplate.exchange("/ticket/all", HttpMethod.GET, httpEntity,
				TicketReaderDto[].class);

		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertTrue(result.getBody().length == 6);
		assertTrue(result.getBody()[0].getTimeType() == TimeTicketType.MONTH);
		assertTrue(result.getBody()[0].getTrafficType() == TrafficType.BUS);
		assertTrue(result.getBody()[0].getTrafficZone() == TrafficZone.FIRST);
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
		// za brisanje
		this.ticket1 = result.getBody().getId();

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

	}

	/// *
	// /*
	// * testiramo upotrebu karte
	// */
	// // 6. kada karta ne postoji
	@Test
	public void test_useTicket_whenTicketNotFound() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_no_active);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		String serialNo = "BMSKK88778";
		Long line = 1L;
		ResponseEntity<String> result = restTemplate.exchange(
				"/ticket/useTicket?serialNo=" + serialNo + "&line=" + line, HttpMethod.GET, httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
		assertTrue(result.getBody().equals("Ticket with serialNo: " + serialNo + " does not exist!"));
	}

	//
	// // 7. kada je single karta vec koriscena
	@Test
	public void test_useTicket_whenTicketAlreadyUse() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_no_active);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		String serialNo = "BMFS12121212024";
		Long line = 1L;
		ResponseEntity<String> result = restTemplate.exchange(
				"/ticket/useTicket?serialNo=" + serialNo + "&line=" + line, HttpMethod.GET, httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertTrue(result.getBody().equals("Ticket with serialNo: " + serialNo + " has already been used!"));
	}

	//
	// // 8. kada je karta istekla
	@Test
	public void test_useTicket_whenTicketIsNotValid() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_no_active);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		Long line = 1L;
		String serialNo = "BMFS12121212025";
		ResponseEntity<String> result = restTemplate.exchange(
				"/ticket/useTicket?serialNo=" + serialNo + "&line=" + line, HttpMethod.GET, httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertTrue(result.getBody().equals("Ticket with serialNo: " + serialNo + " has expired!"));
	}

	//
	// // 9. koristimo single kartu
	@Test
	@Transactional
	@Rollback(true)
	public void test_useTicket_whenTicketIsSingle() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_no_active);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		Long line = 1L;
		String serialNo = "BMFS12121212023";
		ResponseEntity<String> result = restTemplate.exchange(
				"/ticket/useTicket?serialNo=" + serialNo + "&line=" + line, HttpMethod.GET, httpEntity, String.class);

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

	//
	// // 10. koristimo bilo koju drugu kartu koja nije single
	//
	@Test
	public void test_useTicket_OK() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_no_active);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		String serialNo = "BMFS12121212000";
		Long line = 1L;
		ResponseEntity<String> result = restTemplate.exchange(
				"/ticket/useTicket?serialNo=" + serialNo + "&line=" + line, HttpMethod.GET, httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertTrue(result.getBody().equals("The ticket was successfully used"));

	}

	//
	// //11. kada posaljemo liniju koja ne postoji
	@Test
	public void test_useTicket_whenLineNotFound() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_no_active);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		String serialNo = "BMFS12121212000";
		Long line = 23L;
		ResponseEntity<String> result = restTemplate.exchange(
				"/ticket/useTicket?serialNo=" + serialNo + "&line=" + line, HttpMethod.GET, httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertTrue(result.getBody().equals("Line with id: " + line + " does not exist!"));

	}

	//
	// //12. kada koristimo kartu u pogresnoj zoni
	@Test
	public void test_useTicket_whenZonesDoNotMatch() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_no_active);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		String serialNo = "BMFS12121212003";
		Long line = 1L;
		ResponseEntity<String> result = restTemplate.exchange(
				"/ticket/useTicket?serialNo=" + serialNo + "&line=" + line, HttpMethod.GET, httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.CONFLICT);
		assertTrue(result.getBody().equals("The ticket can be used in the zone for which it was purchased."));

	}

	// 13. kada koristimo kartu u pogresnoj vrsti prevoza
	@Test
	public void test_useTicket_whenTrafficTypeDoNotMatch() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_no_active);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		String serialNo = "BMFS12121212000";
		Long line = 2L;
		ResponseEntity<String> result = restTemplate.exchange(
				"/ticket/useTicket?serialNo=" + serialNo + "&line=" + line, HttpMethod.GET, httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertTrue(result.getBody().equals("The ticket can be used in the traffic type for which it was purchased."));

	}

	//
	// /*
	// * testiramo metodu check ticket
	// */
	// // 13. kada karta ne postoji
	@Test
	public void test_checkTicket_whenTicketNotFound() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_inspector);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		Long line = 1L;
		String serialNo = "BMSKK88778";
		ResponseEntity<String> result = restTemplate.exchange(
				"/ticket/checkTicket?serialNo=" + serialNo + "&line=" + line, HttpMethod.GET, httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
		assertTrue(result.getBody().equals("Ticket with serialNo: " + serialNo + " does not exist!"));

	}

	//
	// // 14. single karta koja nije koriscena
	@Test
	public void test_checkTicket_whenTicketNotUse() {
		/*
		 * proverim da li je ta karta mozda koriscena
		 * 
		 */
		String serialNo = "BMFS12121212023";
		Long line = 1L;
		Ticket t = this.repository.findBySerialNo(serialNo).get();
		assertTrue(t.isUsed());
		t.setUsed(false);
		this.repository.save(t);
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_inspector);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		ResponseEntity<String> result = restTemplate.exchange(
				"/ticket/checkTicket?serialNo=" + serialNo + "&line=" + line, HttpMethod.GET, httpEntity, String.class);

		assertTrue(result.getStatusCode() == HttpStatus.BAD_REQUEST);
		assertTrue(result.getBody().equals("Ticket with serialNo: " + serialNo + " was not used!"));

	}

	//
	// // 15. karta koja je istekla
	@Test
	public void test_checkTicket_whenTicketIsNotValid() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_inspector);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		Long line = 1L;
		String serialNo = "BMFS12121212025";
		ResponseEntity<String> result = restTemplate.exchange(
				"/ticket/checkTicket?serialNo=" + serialNo + "&line=" + line, HttpMethod.GET, httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.CONFLICT);
		assertTrue(result.getBody().equals("Ticket with serialNo: " + serialNo + " has expired!"));

	}

	//
	// // 16. kada je sve ok
	@Test
	public void test_checkTicket_OK() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_inspector);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		String serialNo = "BMFS12121212000";
		Long line = 1L;
		ResponseEntity<String> result = restTemplate.exchange(
				"/ticket/checkTicket?serialNo=" + serialNo + "&line=" + line, HttpMethod.GET, httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertTrue(result.getBody().equals("The ticket was successfully checked"));

		Ticket t = this.repository.findBySerialNo(serialNo).get();
		assertTrue(t.getCheckInspectors().size() == 1);
		// vratimo bazu na staro
		t.setCheckInspectors(null);
		this.repository.save(t);

	}

	//
	// //17. kada je pogresna vrednost zone
	@Test
	public void test_checkTicket_whenLineNotFound() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_inspector);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		String serialNo = "BMFS12121212000";
		Long line = 4L;
		ResponseEntity<String> result = restTemplate.exchange(
				"/ticket/checkTicket?serialNo=" + serialNo + "&line=" + line, HttpMethod.GET, httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertTrue(result.getBody().equals("Line with id: " + line + " does not exist!"));

	}

	//
	//
	// //18. kada je karta koriscena u pogresnoj zoni
	//
	@Test
	public void test_checkTicket_whenZonesDoNotMatch() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_inspector);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		String serialNo = "BMFS12121212003";
		Long line = 1L;
		ResponseEntity<String> result = restTemplate.exchange(
				"/ticket/checkTicket?serialNo=" + serialNo + "&line=" + line, HttpMethod.GET, httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.CONFLICT);
		assertTrue(result.getBody()
				.equals("The ticket with " + serialNo + "  was purchased for another zone of transport"));

	}

	// 19. cekiranje karte, a tip prevoza se ne podudara
	@Test
	public void test_checkTicket_whenTrafficTypeDoNotMatch() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_inspector);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		String serialNo = "BMFS12121212000";
		Long line = 2L;
		ResponseEntity<String> result = restTemplate.exchange(
				"/ticket/checkTicket?serialNo=" + serialNo + "&line=" + line, HttpMethod.GET, httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertTrue(result.getBody()
				.equals("The ticket with " + serialNo + "  was purchased for another type of transport"));

	}

	// /*
	// * izvestaj
	// */
	//
	// // 19. kada pogresimo neki od parametara
	@Test
	@Transactional
	@Rollback(true)
	public void test_monthReport_invalidParam() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_admin);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		int month = 0;
		int year = 2019;
		String type = "month";
		ResponseEntity<ReportDto> result = restTemplate.exchange(
				"/ticket/report?month=" + month + "&year=" + year + "&type=" + month, HttpMethod.GET, httpEntity,
				ReportDto.class);

		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);

	}

	// pogresan tip izvestaja
	@Test
	@Transactional
	@Rollback(true)
	public void test_monthReport_invalidReportType() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_admin);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		int month = 0;
		int year = 2019;
		String type = "m";
		ResponseEntity<ReportDto> result = restTemplate.exchange(
				"/ticket/report?month=" + month + "&year=" + year + "&type=" + month, HttpMethod.GET, httpEntity,
				ReportDto.class);

		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);

	}

	//
	// // 20. svi parametri ok
	@Test
	@Transactional
	@Rollback(true)
	public void test_monthReport_OK() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_admin);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		int month = 1;
		int year = 2019;
		String type = "month";
		ResponseEntity<ReportDto> result = restTemplate.exchange(
				"/ticket/report?month=" + month + "&year=" + year + "&type=" + type, HttpMethod.GET, httpEntity,
				ReportDto.class);

		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertNotNull(result.getBody());
		assertTrue(result.getBody().getNumOfStudentMonthTicket() == 1);
		assertTrue(result.getBody().getNumOfSeniorMonthTicket() == 2);
		assertTrue(result.getBody().getNumOfHandycapSingleTicket() == 2);
		assertTrue(result.getBody().getNumOfHandycapDailyTicket() == 1);
		assertTrue(result.getBody().getNumOfBusTicket() == 6);
		assertTrue(result.getBody().getMoney() == 3085);

	}

	//
	// /*
	// * sad gledamo pravo pristupa
	// */
	// 21. admin kupuje kartu
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

	// 22. inspektor kupuje kartu
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

	//
	// 23. putnik gleda izvestaj
	@Test
	@Transactional
	@Rollback(true)
	public void test_monthReport_passenger() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_active);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		int month = 1;
		int year = 2019;
		ResponseEntity<ReportDto> result = restTemplate.exchange(
				"/ticket/report?month=" + month + "&year=" + year + "&type=month", HttpMethod.GET, httpEntity,
				ReportDto.class);

		assertEquals(result.getStatusCode(), HttpStatus.FORBIDDEN);

	}

	// 24. inspektor gleda izvestaj
	@Test
	@Transactional
	@Rollback(true)
	public void test_monthReport_inspector() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_inspector);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		int month = 1;
		int year = 2019;
		ResponseEntity<ReportDto> result = restTemplate.exchange(
				"/ticket/report?month=" + month + "&year=" + year + "&type=month", HttpMethod.GET, httpEntity,
				ReportDto.class);

		assertEquals(result.getStatusCode(), HttpStatus.FORBIDDEN);

	}

	// 25. admin upotrebljava kartu

	@Test
	@Transactional
	@Rollback(true)
	public void test_useTicket_admin() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_admin);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		String serialNo = "BMFS12121212000";
		ResponseEntity<String> result = restTemplate.exchange("/ticket/useTicket?serialNo=" + serialNo + "&line=" + 1L,
				HttpMethod.GET, httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.FORBIDDEN);

	}

	// 26. putnik pregleda kartu
	@Test
	@Transactional
	@Rollback(true)
	public void test_checkTicket_passenger() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_no_active);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		String serialNo = "BMFS12121212000";
		ResponseEntity<String> result = restTemplate.exchange(
				"/ticket/checkTicket?serialNo=" + serialNo + "&line=" + 1L, HttpMethod.GET, httpEntity, String.class);

		assertEquals(result.getStatusCode(), HttpStatus.FORBIDDEN);
	}

	// getTicketPrice
	// 1. pogresan tip prevoza
	@Test
	public void test_getPrice_WrongTrafficType() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_no_active);

		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		String type = "plain";
		String zone = "first";
		String time = "annual";
		ResponseEntity<String> result = restTemplate.exchange(
				"/ticket/price?type=" + type + "&zone="+zone + "&time=" + time, HttpMethod.GET, httpEntity,
				String.class);

		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertTrue(result.getBody().equals("Incorect traffic type: " + type));
	}

	//
	// // 2. pogresno vreme
	@Test
	public void test_getPrice_WrongTicketTime() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_active);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		String type = "bus";
		String zone = "first";
		String time = "weekly";
		ResponseEntity<String> result = restTemplate.exchange(
				"/ticket/price?type=" + type + "&zone="+zone + "&time=" + time, HttpMethod.GET, httpEntity,
				String.class);

		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertTrue(result.getBody().equals("Incorect time ticket: " + time));
	}

	//
	// // 2. pogresna zona
	@Test
	@Transactional
	@Rollback(true)
	public void test_getPrice_WrongZone() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_no_active);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		String type = "bus";
		String zone = "fourth";
		String time = "annual";
		ResponseEntity<String> result = restTemplate.exchange(
				"/ticket/price?type=" + type + "&zone="+zone + "&time=" + time, HttpMethod.GET, httpEntity,
				String.class);

		assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertTrue(result.getBody().equals("Incorect traffic zone: " + zone));
	}

	//
	@Test
	public void test_getPrice_NoActivePassenger() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_no_active);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		String type = "bus";
		String zone = "first";
		String time = "month";
		ResponseEntity<String> result = restTemplate.exchange(
				"/ticket/price?type=" + type + "&zone="+zone + "&time=" + time, HttpMethod.GET, httpEntity,
				String.class);

		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertTrue(result.getBody().equals("1000.0"));
	}

	//
	@Test

	public void test_getPrice_student() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_active);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		String type = "bus";
		String zone = "first";
		String time = "month";
		ResponseEntity<String> result = restTemplate.exchange(
				"/ticket/price?type=" + type + "&zone="+zone + "&time=" + time, HttpMethod.GET, httpEntity,
				String.class);

		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertTrue(result.getBody().equals("900.0"));
	}

	// getMyTicket
	// 2. pogresno vreme
	@Test
	@Transactional
	@Rollback(true)
	public void test_getMyTicket() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", token_passenger_active);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		String type = "bus";
		String zone = "first";
		String time = "month";
		ResponseEntity<TicketReaderDto[]> result = restTemplate.exchange("/ticket/myTicket", HttpMethod.GET, httpEntity,
				TicketReaderDto[].class);

		assertEquals(result.getStatusCode(), HttpStatus.OK);
		assertTrue(result.getBody().length == 2);
		assertTrue(result.getBody()[0].getTimeType() == TimeTicketType.MONTH);
		assertTrue(result.getBody()[0].getTrafficType() == TrafficType.BUS);
		assertTrue(result.getBody()[0].getTrafficZone() == TrafficZone.FIRST);
	}

}
