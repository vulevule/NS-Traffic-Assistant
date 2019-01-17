package com.team9.NSTrafficAssistant.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.team9.exceptions.NotFoundActivePricelistException;
import com.team9.exceptions.PriceItemNotFoundException;
import com.team9.exceptions.TicketAlreadyUsedException;
import com.team9.exceptions.TicketIsNotUseException;
import com.team9.exceptions.TicketIsNotValidException;
import com.team9.exceptions.TicketNotFound;
import com.team9.exceptions.UserNotFoundException;
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
		assertTrue(tickets.size() == 6);
	}

	// 6. testiranje check metode - kada karta ne postoji
	@Test(expected = TicketNotFound.class)
	@Transactional
	@Rollback(true)
	public void test_checkTicket_whenTicketNotFound()
			throws TicketNotFound, TicketIsNotUseException, TicketIsNotValidException, UserNotFoundException, WrongTrafficZoneException, ZonesDoNotMatchException {
		TicketReaderDto result = this.service.checkTicket("BMSKK88778", "lenalukic", "second");
	}

	// 7. check metoda - kada ne postoji inspektor
	@Test(expected = UserNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void test_checkTicket_whenUserNotFound()
			throws TicketNotFound, TicketIsNotUseException, TicketIsNotValidException, UserNotFoundException, WrongTrafficZoneException, ZonesDoNotMatchException {
		TicketReaderDto result = this.service.checkTicket("BMFS12121212000", "mmmm", "second");
	}

	// 8. check metoda - kada single karta nije jos uvek iskoriscena
	@Test(expected = TicketIsNotUseException.class)
	@Transactional
	@Rollback(true)
	public void test_checkTicket_whenTicketIsNotUse()
			throws TicketNotFound, TicketIsNotUseException, TicketIsNotValidException, UserNotFoundException, WrongTrafficZoneException, ZonesDoNotMatchException {
		TicketReaderDto result = this.service.checkTicket("BMFS12121212023", "lenalukic", "first");
	}

	// 9. check metoda - kada je isteklo vreme trajanja karte
	@Test(expected = TicketIsNotValidException.class)
	@Transactional
	@Rollback(true)
	public void test_checkTicket_whenTicketIsNotValid()
			throws TicketNotFound, TicketIsNotUseException, TicketIsNotValidException, UserNotFoundException, WrongTrafficZoneException, ZonesDoNotMatchException {
		TicketReaderDto result = this.service.checkTicket("BMFS12121212025", "lenalukic", "first");
	}

	// 10. check metoda - sve ok, postoji i karta i inspektor
	@Test
	@Transactional
	@Rollback(true)
	public void test_checkTicket_OK()
			throws TicketNotFound, TicketIsNotUseException, TicketIsNotValidException, UserNotFoundException, WrongTrafficZoneException, ZonesDoNotMatchException {
		TicketReaderDto result = this.service.checkTicket("BMFS12121212000", "lenalukic", "first");

		assertNotNull(result);
		assertTrue(result.getInspectors().size() == 1);
		assertTrue(result.getInspectors().get(0).equals("lenalukic"));

	}
	
	//11. check metoda - kada je pogresna zina poslata 
	@Test(expected = WrongTrafficZoneException.class)
	@Transactional
	@Rollback(true)
	public void test_checkTicket_whenWrongTrafficZone()
			throws TicketNotFound, TicketIsNotUseException, TicketIsNotValidException, UserNotFoundException, WrongTrafficZoneException, ZonesDoNotMatchException {
		TicketReaderDto result = this.service.checkTicket("BMFS12121212025", "lenalukic", "f");
	}
	
	//12. kada je karta koriscena u pogresnoj zoni
	@Test(expected = ZonesDoNotMatchException.class)
	@Transactional
	@Rollback(true)
	public void test_checkTicket_whenZonesDoNotMatch()
			throws TicketNotFound, TicketIsNotUseException, TicketIsNotValidException, UserNotFoundException, WrongTrafficZoneException, ZonesDoNotMatchException {
		TicketReaderDto result = this.service.checkTicket("BMFS12121212000", "lenalukic", "second");
	}

	// 13. use metoda - karta koja ne postoji
	@Test(expected = TicketNotFound.class)
	@Transactional
	@Rollback(true)
	public void test_useTicket_whenTicketNotFound()
			throws TicketNotFound, TicketAlreadyUsedException, TicketIsNotValidException, WrongTrafficZoneException, ZonesDoNotMatchException {
		boolean result = this.service.useTicket("BMSKK88778", "lenalukic", "second");
	}

	// 14. use metoda - karta koja je vec koriscena

	@Test(expected = TicketAlreadyUsedException.class)
	@Transactional
	@Rollback(true)
	public void test_useTicket_whenTicketAlreadyUse()
			throws TicketNotFound, TicketAlreadyUsedException, TicketIsNotValidException, WrongTrafficZoneException, ZonesDoNotMatchException {
		boolean result = this.service.useTicket("BMFS12121212024", "pavleperic", "first");
	}

	// 15. use metoda - karta kojoj je isteklo vreme trajanja
	@Test(expected = TicketIsNotValidException.class)
	@Transactional
	@Rollback(true)
	public void test_useTicket_whenTicketIsNotValid()
			throws TicketNotFound, TicketAlreadyUsedException, TicketIsNotValidException, WrongTrafficZoneException, ZonesDoNotMatchException {
		boolean result = this.service.useTicket("BMFS12121212025", "pavleperic", "first");
	}

	// 16. use metoda - karta koja je single i mesecne karte
	@Test
	@Transactional
	@Rollback(true)
	public void test_useSingleTicket() throws TicketNotFound, TicketAlreadyUsedException, TicketIsNotValidException, WrongTrafficZoneException, ZonesDoNotMatchException {
		boolean result = this.service.useTicket("BMFS12121212023", "pavleperic", "first");
		assertTrue(result);
		// posto se za single kartu menja atribut u bazi, izvucicemo kartu po
		// serijskom broju i proveriti promenjeni atribut
		Optional<Ticket> t = this.repository.findBySerialNo("BMFS12121212023");
		assertTrue(t.get().isUsed());

		boolean res = this.service.useTicket("BMFS12121212000", "peraperic", "first");
		assertTrue(res);
	}
	
	//17. use metoda kada je pogresna zona prosledjena
	@Test(expected = WrongTrafficZoneException.class)
	@Transactional
	@Rollback(true)
	public void test_useTicket_whenWrongTrafficZone()
			throws TicketNotFound, TicketAlreadyUsedException, TicketIsNotValidException, WrongTrafficZoneException, ZonesDoNotMatchException {
		boolean result = this.service.useTicket("BMFS12121212024", "pavleperic", "f");
	}
	
	//18. use metoda - kada se karta koristi u pogresnoj zoni
	@Test(expected = ZonesDoNotMatchException.class)
	@Transactional
	@Rollback(true)
	public void test_use_whenZonesDoNotMatch() throws TicketNotFound, TicketAlreadyUsedException, TicketIsNotValidException, WrongTrafficZoneException, ZonesDoNotMatchException {
		boolean result = this.service.useTicket("BMFS12121212023", "pavleperic", "second");
	}

	// 19. getMonthReport - kada prosledimo pogresne argumenta za mesec i godinu
	@Test(expected = IllegalArgumentException.class)
	public void test_monthReport_whenMonthEquals0() {
		ReportDto report = this.service.getMonthReport(0, 2019);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_monthReport_whenYearEquals2020() {
		ReportDto report = this.service.getMonthReport(0, 2020);
	}

	// 20. getMonthReport - kada su parametri korektni
	@Test
	public void test_monthReport_OK() {
		ReportDto result = this.service.getMonthReport(12, 2018);

		assertNotNull(result);
		assertTrue(result.getNumOfStudentMonthTicket() == 1);
		assertTrue(result.getNumOfSeniorMonthTicket() == 1);
		assertTrue(result.getNumOfBusTicket() == 2);
		assertTrue(result.getMoney() == 1850);
	}

}
