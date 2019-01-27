package com.team9.NSTrafficAssistant.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.dto.ReportDto;
import com.team9.dto.TicketDto;
import com.team9.dto.TicketReaderDto;
import com.team9.exceptions.LineNotFoundException;
import com.team9.exceptions.NotFoundActivePricelistException;
import com.team9.exceptions.PriceItemNotFoundException;
import com.team9.exceptions.TicketAlreadyUsedException;
import com.team9.exceptions.TicketIsNotUseException;
import com.team9.exceptions.TicketIsNotValidException;
import com.team9.exceptions.TicketNotFound;
import com.team9.exceptions.TrafficTypeDoNotMatchException;
import com.team9.exceptions.UserNotFoundException;
import com.team9.exceptions.WrongReportTypeException;
import com.team9.exceptions.WrongTicketTimeException;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.exceptions.ZonesDoNotMatchException;
import com.team9.model.Ticket;
import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.model.UserTicketType;
import com.team9.repository.TicketRepository;
import com.team9.service.TicketService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TicketServiceIntegrationTest {

	@Autowired
	private TicketService service;

	@Autowired
	private TicketRepository repository;

	// kupovina karte
	// prvo cemo proveriti sve exceptione, pa cemo onda kupiti kartu
	// 1. user not found exception
	@Test(expected = UserNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void buyTicket_whenUserNotExists()
			throws WrongTrafficTypeException, UserNotFoundException, WrongTrafficZoneException,
			WrongTicketTimeException, PriceItemNotFoundException, NotFoundActivePricelistException {
		TicketDto t = new TicketDto("month", "first", "bus", 10.0);
		String username = "mikamilanovic";
		TicketReaderDto result = this.service.buyTicket(t, username);
	}

	// 2. wrong traffic type
	@Test(expected = WrongTrafficTypeException.class)
	@Transactional
	@Rollback(true)
	public void buyTicket_whenWrongTrafficType()
			throws WrongTrafficTypeException, UserNotFoundException, WrongTrafficZoneException,
			WrongTicketTimeException, PriceItemNotFoundException, NotFoundActivePricelistException {
		TicketDto t = new TicketDto("month", "first", "plain", 10.0);
		String username = "peraperic";
		TicketReaderDto result = this.service.buyTicket(t, username);
	}

	// 3. wrong ticket time
	@Test(expected = WrongTicketTimeException.class)
	@Transactional
	@Rollback(true)
	public void buyTicket_whenWrongTicketTime()
			throws WrongTrafficTypeException, UserNotFoundException, WrongTrafficZoneException,
			WrongTicketTimeException, PriceItemNotFoundException, NotFoundActivePricelistException {
		TicketDto t = new TicketDto("weekly", "first", "bus", 10.0);
		String username = "peraperic";
		TicketReaderDto result = this.service.buyTicket(t, username);
	}

	// 4. wrong zone
	@Test(expected = WrongTrafficZoneException.class)
	@Transactional
	@Rollback(true)
	public void buyTicket_whenWrongTrafficZone()
			throws WrongTrafficTypeException, UserNotFoundException, WrongTrafficZoneException,
			WrongTicketTimeException, PriceItemNotFoundException, NotFoundActivePricelistException {
		TicketDto t = new TicketDto("month", "fourth", "bus", 10.0);
		String username = "peraperic";
		TicketReaderDto result = this.service.buyTicket(t, username);
	}

	// da stavka ne postoji i da ne postoji aktivan cenovnik ne mozemo testirati
	// zato sto u bazi imamo cenovnik
	// 5. ok, kupujemo kartu
	@Test
	@Transactional
	@Rollback(true)
	public void test_buyTicket_OK() throws WrongTrafficTypeException, UserNotFoundException, WrongTrafficZoneException,
			WrongTicketTimeException, PriceItemNotFoundException, NotFoundActivePricelistException {
		TicketDto t = new TicketDto("month", "first", "bus", 10.0);
		String username = "peraperic";
		TicketReaderDto result = this.service.buyTicket(t, username);

		// proverimo da li nam je vratio dobro sve podatke
		assertNotNull(result);
		assertTrue(result.getPassenger_username().equals("peraperic"));
		assertTrue(result.getTimeType() == TimeTicketType.MONTH);
		assertTrue(result.getTrafficZone() == TrafficZone.FIRST);
		assertTrue(result.getTrafficType() == TrafficType.BUS);
		assertTrue(result.getUserType() == UserTicketType.STUDENT);
		// proverimo da li cena dobra = 1000-10% = 900
		assertTrue(result.getPrice() == 900);
		// proverimo serijski broj karte
		assertTrue(result.getSerialNo().startsWith("BMFS"));
		// proverimo datum vazenja karte
		// issue = danasnji dan ; exp = issue + 1 mesec
		Date issue = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		Date exp = Date.valueOf(issue.toLocalDate().plusMonths(1));
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		assertTrue(fmt.format(result.getIssueDate()).equals(fmt.format(issue)));
		assertTrue(fmt.format(result.getExpirationDate()).equals(fmt.format(exp)));
		// proverimo koliko karti imamo u bazi, moramo imati 6, zato sto vec
		// imamo 5 i ova jos jedna dodata
		List<Ticket> tickets = (List<Ticket>) this.repository.findAll();
		assertTrue(tickets.size() == 7);
	}

	// 6. testiranje check metode - kada karta ne postoji
	@Test(expected = TicketNotFound.class)
	@Transactional
	@Rollback(true)
	public void test_checkTicket_whenTicketNotFound() throws TicketNotFound, TicketIsNotUseException, TicketIsNotValidException, UserNotFoundException, ZonesDoNotMatchException, LineNotFoundException, TrafficTypeDoNotMatchException{
		TicketReaderDto result = this.service.checkTicket("BMSKK88778", "lenalukic", 1L);
	}

	// 7. check metoda - kada ne postoji inspektor
	@Test(expected = UserNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void test_checkTicket_whenUserNotFound() throws TicketNotFound, TicketIsNotUseException, TicketIsNotValidException, UserNotFoundException, ZonesDoNotMatchException, LineNotFoundException, TrafficTypeDoNotMatchException {
		TicketReaderDto result = this.service.checkTicket("BMFS12121212000", "mmmm", 1L);
	}

	// 8. check metoda - kada single karta nije jos uvek iskoriscena
	@Test(expected = TicketIsNotUseException.class)
	@Transactional
	@Rollback(true)
	public void test_checkTicket_whenTicketIsNotUse() throws TicketNotFound, TicketIsNotUseException, TicketIsNotValidException, UserNotFoundException, ZonesDoNotMatchException, LineNotFoundException, TrafficTypeDoNotMatchException{
		TicketReaderDto result = this.service.checkTicket("BMFS12121212023", "lenalukic", 1L);
	}

	// 9. check metoda - kada je isteklo vreme trajanja karte
	@Test(expected = TicketIsNotValidException.class)
	@Transactional
	@Rollback(true)
	public void test_checkTicket_whenTicketIsNotValid() throws TicketNotFound, TicketIsNotUseException, TicketIsNotValidException, UserNotFoundException, ZonesDoNotMatchException, LineNotFoundException, TrafficTypeDoNotMatchException{
		TicketReaderDto result = this.service.checkTicket("BMFS12121212025", "lenalukic", 1L);
	}

	// 10. check metoda - sve ok, postoji i karta i inspektor
	@Test
	@Transactional
	@Rollback(true)
	public void test_checkTicket_OK() throws TicketNotFound, TicketIsNotUseException, TicketIsNotValidException, UserNotFoundException, ZonesDoNotMatchException, LineNotFoundException, TrafficTypeDoNotMatchException{
		TicketReaderDto result = this.service.checkTicket("BMFS12121212000", "lenalukic", 1L);

		assertNotNull(result);
		assertTrue(result.getInspectors().size() == 1);
		assertTrue(result.getInspectors().get(0).equals("lenalukic"));

	}
	
	//11. check metoda - linija koja ne postoji
	@Test(expected = LineNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void test_checkTicket_whenLineNotFound() throws TicketNotFound, TicketIsNotUseException, TicketIsNotValidException, UserNotFoundException, ZonesDoNotMatchException, LineNotFoundException, TrafficTypeDoNotMatchException{
		TicketReaderDto result = this.service.checkTicket("BMFS12121212003", "lenalukic", 4L);
	}
	
	//12. kada je karta cekirana u pogresnoj zoni
	@Test(expected = ZonesDoNotMatchException.class)
	@Transactional
	@Rollback(true)
	public void test_checkTicket_whenZonesDoNotMatch() throws TicketNotFound, TicketIsNotUseException, TicketIsNotValidException, UserNotFoundException, ZonesDoNotMatchException, LineNotFoundException, TrafficTypeDoNotMatchException {
		TicketReaderDto result = this.service.checkTicket("BMFS12121212003", "lenalukic", 1L);
	}
	
	//13. kada se karta cekira u pogresnom tipu prevoza
	@Test(expected = TrafficTypeDoNotMatchException.class)
	@Transactional
	@Rollback(true)
	public void test_checkTicket_whenTrafficTypeDoNotMatch() throws TicketNotFound, TicketIsNotUseException, TicketIsNotValidException, UserNotFoundException, ZonesDoNotMatchException, LineNotFoundException, TrafficTypeDoNotMatchException {
		TicketReaderDto result = this.service.checkTicket("BMFS12121212000", "lenalukic", 2L);
	}
	

	// 13. use metoda - karta koja ne postoji
	@Test(expected = TicketNotFound.class)
	@Transactional
	@Rollback(true)
	public void test_useTicket_whenTicketNotFound() throws TicketNotFound, TicketAlreadyUsedException, TicketIsNotValidException, ZonesDoNotMatchException, LineNotFoundException, TrafficTypeDoNotMatchException {
		boolean result = this.service.useTicket("BMSKK88778", "lenalukic", 1L);
	}

	// 14. use metoda - karta koja je vec koriscena

	@Test(expected = TicketAlreadyUsedException.class)
	@Transactional
	@Rollback(true)
	public void test_useTicket_whenTicketAlreadyUse() throws TicketNotFound, TicketAlreadyUsedException, TicketIsNotValidException, ZonesDoNotMatchException, LineNotFoundException, TrafficTypeDoNotMatchException{
		boolean result = this.service.useTicket("BMFS12121212024", "pavleperic", 1L);
	}

	// 15. use metoda - karta kojoj je isteklo vreme trajanja
	@Test(expected = TicketIsNotValidException.class)
	@Transactional
	@Rollback(true)
	public void test_useTicket_whenTicketIsNotValid() throws TicketNotFound, TicketAlreadyUsedException, TicketIsNotValidException, ZonesDoNotMatchException, LineNotFoundException, TrafficTypeDoNotMatchException{
		boolean result = this.service.useTicket("BMFS12121212025", "pavleperic", 1L);
	}

	// 16. use metoda - karta koja je single i mesecne karte
	@Test
	@Transactional
	@Rollback(true)
	public void test_useSingleTicket() throws TicketNotFound, TicketAlreadyUsedException, TicketIsNotValidException, ZonesDoNotMatchException, LineNotFoundException, TrafficTypeDoNotMatchException{
		boolean result = this.service.useTicket("BMFS12121212023", "pavleperic", 1L);
		assertTrue(result);
		// posto se za single kartu menja atribut u bazi, izvucicemo kartu po
		// serijskom broju i proveriti promenjeni atribut
		Optional<Ticket> t = this.repository.findBySerialNo("BMFS12121212023");
		assertTrue(t.get().isUsed());

		boolean res = this.service.useTicket("BMFS12121212000", "peraperic", 1L);
		assertTrue(res);
	}
	
	//17. use metoda kada je proslednjena linija koja ne postoji
	@Test(expected = LineNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void test_useTicket_whenLineNotFound() throws TicketNotFound, TicketAlreadyUsedException, TicketIsNotValidException, ZonesDoNotMatchException, LineNotFoundException, TrafficTypeDoNotMatchException{
		boolean result = this.service.useTicket("BMFS12121212024", "pavleperic", 4L);
	}
	
	//18. use metoda - kada se karta koristi u pogresnoj zoni
	@Test(expected = ZonesDoNotMatchException.class)
	@Transactional
	@Rollback(true)
	public void test_use_whenZonesDoNotMatch() throws TicketNotFound, TicketAlreadyUsedException, TicketIsNotValidException, ZonesDoNotMatchException, LineNotFoundException, TrafficTypeDoNotMatchException {
		boolean result = this.service.useTicket("BMFS12121212003", "pavleperic", 1L);
	}

	//use metoda - karta se koristi u pogresnom tipu prevoza
	@Test(expected = TrafficTypeDoNotMatchException.class)
	@Transactional
	@Rollback(true)
	public void test_useTicket_trafficTypeDoNotMatch() throws TicketNotFound, TicketAlreadyUsedException, TicketIsNotValidException, ZonesDoNotMatchException, LineNotFoundException, TrafficTypeDoNotMatchException{
		boolean result = this.service.useTicket("BMFS12121212024", "pavleperic", 2L);
	}
	
	// 19. getMonthReport - kada prosledimo pogresne argumenta za mesec i godinu
	@Test(expected = IllegalArgumentException.class)
	public void test_monthReport_whenMonthEquals0() throws IllegalArgumentException, WrongReportTypeException {
		ReportDto report = this.service.getReport(0, 2019, "MONTH");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_monthReport_whenYearEquals2020() throws IllegalArgumentException, WrongReportTypeException {
		ReportDto report = this.service.getReport(1, 2020, "month");
	}

	@Test(expected = WrongReportTypeException.class)
	public void test_monthReport_whenWrongReportType() throws IllegalArgumentException, WrongReportTypeException {
		ReportDto report = this.service.getReport(1, 2019, "m");
	}
	
	// 20. getMonthReport - kada su parametri korektni
	@Test
	public void test_monthReport_OK() throws IllegalArgumentException, WrongReportTypeException {
		ReportDto result = this.service.getReport(1, 2019, "month");

		assertNotNull(result);
		assertTrue(result.getNumOfStudentMonthTicket() == 1);
		assertTrue(result.getNumOfSeniorMonthTicket() == 2);
		assertTrue(result.getNumOfHandycapSingleTicket() == 2);
		assertTrue(result.getNumOfHandycapDailyTicket() == 1);
		assertTrue(result.getNumOfBusTicket() == 6);
		assertTrue(result.getMoney() == 3085);
	}

	//21. getallTicket
	@Test
	public void test_getAllTicket(){
		Collection<TicketReaderDto> tickets =this.service.getAll();	
		assertNotNull(tickets);
		assertTrue(tickets.size() == 6);
	}
	
	//22. karte jednog korisnika
	@Test
	public void test_myTicket() throws UserNotFoundException{
		Collection<TicketReaderDto> tickets = this.service.myTicket("peraperic");
		assertNotNull(tickets);
		assertTrue(tickets.size() == 1);
	}
	
	//23. karte za korisnika koji ne postoji
	@Test(expected = UserNotFoundException.class)
	public void test_myTicket_notFoundUser() throws UserNotFoundException{
		Collection<TicketReaderDto> tickets = this.service.myTicket("mm");
		
	}
	
	//testiranje metode getPrice
	//24. kada korisnik ne postoji
	@Test(expected = UserNotFoundException.class)
	public void getPrice_whenUserNotFound() throws PriceItemNotFoundException, UserNotFoundException, NotFoundActivePricelistException, WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException{
		TicketDto t = new TicketDto("month", "first", "bus", 0);
		double d = this.service.getTicketPrice(t, "kkkk");
	}
	
	//25. kada je pogresan tip prevoza
		@Test(expected = WrongTrafficTypeException.class)
		public void getPrice_whenWrongTrafficType() throws PriceItemNotFoundException, UserNotFoundException, NotFoundActivePricelistException, WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException{
			TicketDto t = new TicketDto("month", "first", "plain", 0);
			double d = this.service.getTicketPrice(t, "peraperic");
		}
	
		//26. kadaje pogresna zona
		@Test(expected = WrongTrafficZoneException.class)
		public void getPrice_whenWrongTrafficZone() throws PriceItemNotFoundException, UserNotFoundException, NotFoundActivePricelistException, WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException{
			TicketDto t = new TicketDto("month", "fourth", "bus", 0);
			double d = this.service.getTicketPrice(t, "peraperic");
		}
	
		//27. kada je pogresna vremenska odrednica
		@Test(expected = WrongTicketTimeException.class)
		public void getPrice_whenWrongTicketTime() throws PriceItemNotFoundException, UserNotFoundException, NotFoundActivePricelistException, WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException{
			TicketDto t = new TicketDto("weekly", "first", "bus", 0);
			double d = this.service.getTicketPrice(t, "peraperic");
		}
		
		//28. kada je sve ok, i imamo razlicite korisnik
		//24. kada korisnik ne postoji
		@Test()
		public void getPrice_ok() throws PriceItemNotFoundException, UserNotFoundException, NotFoundActivePricelistException, WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException{
			TicketDto t = new TicketDto("month", "first", "bus", 0);
			
			//1. kada je putnik student
			double d = this.service.getTicketPrice(t, "peraperic");
			assertTrue(d == 900);
			
			//3. kada je putnik penzioner ili invalid posto im je isti popust
			double d2 = this.service.getTicketPrice(t, "petraperic");
			assertTrue(d2 == 950);
			
			//4. kada putnik nije aktivan
			double d3 = this.service.getTicketPrice(t, "mikaperic");
			assertTrue(d3 == 1000);
		}
}
