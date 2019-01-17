package com.team9.NSTrafficAssistant.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import com.team9.model.Address;
import com.team9.model.Inspector;
import com.team9.model.Passenger;
import com.team9.model.PriceItem;
import com.team9.model.PriceList;
import com.team9.model.Role;
import com.team9.model.Ticket;
import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.model.UserTicketType;
import com.team9.repository.TicketRepository;
import com.team9.service.PriceListService;
import com.team9.service.PricelistItemService;
import com.team9.service.TicketService;
import com.team9.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TicketServiceUnitTest {

	@Autowired
	private TicketService ticketService;

	@MockBean
	private TicketRepository ticketRepository_mock;

	@MockBean
	private UserService userService_mock;

	@MockBean
	private PriceListService pricelistService_mock;

	@MockBean
	private PricelistItemService pricelistItemService_mock;

	@Before
	public void setUp() {
		/*
		 * user-i koje vracamo, trebaju nam razliciti zbog racunanja cene karte
		 */
		Passenger p_student = new Passenger("Pera Peric", "123456", "peraperic", "pass", "pera@gmail.com",
				Role.PASSENGER, new Address(1L, "Vuka Karadzica", "Novi Sad", 21000), true, UserTicketType.STUDENT);
		Mockito.when(this.userService_mock.getUser("peraperic")).thenReturn(p_student);

		Passenger p = new Passenger("Pera Peric", "123456", "pericpera", "pass", "pera@gmail.com", Role.PASSENGER,
				new Address(1L, "Vuka Karadzica", "Novi Sad", 21000), false, UserTicketType.STUDENT);
		Mockito.when(this.userService_mock.getUser("pericpera")).thenReturn(p);

		Passenger p_senior = new Passenger("Mika Peric", "123456", "mikaperic", "pass", "pera@gmail.com",
				Role.PASSENGER, new Address(1L, "Vuka Karadzica", "Novi Sad", 21000), true, UserTicketType.SENIOR);
		Mockito.when(this.userService_mock.getUser("mikaperic")).thenReturn(p_senior);

		Passenger p_handycap = new Passenger("Petra Peric", "123456", "petraperic", "pass", "pera@gmail.com",
				Role.PASSENGER, new Address(1L, "Vuka Karadzica", "Novi Sad", 21000), true, UserTicketType.HANDYCAP);
		Mockito.when(this.userService_mock.getUser("petraperic")).thenReturn(p_handycap);

		PriceList list = new PriceList(1L, new Date(new java.util.Date().getTime()), null, null, true);
		try {
			Mockito.when(this.pricelistService_mock.getPricelist()).thenReturn(list);
		} catch (NotFoundActivePricelistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PriceItem item = new PriceItem(1000, TrafficType.BUS, TimeTicketType.MONTH, TrafficZone.FIRST, 15, 5, 8, list);
		try {
			Mockito.when(this.pricelistItemService_mock.getPriceItem(TrafficType.BUS, TimeTicketType.MONTH,
					TrafficZone.FIRST, list)).thenReturn(item);
		} catch (PriceItemNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Mockito.when(this.pricelistItemService_mock.getPriceItem(TrafficType.TRAM, TimeTicketType.MONTH,
					TrafficZone.FIRST, list)).thenThrow(PriceItemNotFoundException.class);
		} catch (PriceItemNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// karta kojoj je istekao period vazenja
		Date issueDate = new Date(new GregorianCalendar(2017, Calendar.FEBRUARY, 11).getTime().getTime());
		Date expirationDate = new Date(new GregorianCalendar(2018, Calendar.FEBRUARY, 11).getTime().getTime());
		Ticket t_is_not_valid = new Ticket(1L, "MSDE3445", issueDate, expirationDate, UserTicketType.STUDENT,
				TimeTicketType.ANNUAL, TrafficZone.SECOND, true, TrafficType.BUS, 10000.00, false, p);

		Mockito.when(this.ticketRepository_mock.findBySerialNo("MSDE3445")).thenReturn(Optional.of(t_is_not_valid));

		// single karta koja je vec koriscena
		// uzmemo danasnji datum i oduzmemo 5 dana = issue date, i dodamo 10
		// dana = expiration date
		Date today = new Date(new java.util.Date().getTime());
		LocalDate ld = today.toLocalDate();
		Date issueDate1 = Date.valueOf(ld.minusDays(5));
		Date expirationDate1 = Date.valueOf(ld.plusDays(10));
		Ticket t_is_used = new Ticket(1L, "MDS345", issueDate1, expirationDate1, UserTicketType.STUDENT,
				TimeTicketType.SINGLE, TrafficZone.SECOND, true, TrafficType.BUS, 100.00, true, p);

		Mockito.when(this.ticketRepository_mock.findBySerialNo("MDS345")).thenReturn(Optional.of(t_is_used));

		// karta koja je single, a nije koriscena
		Ticket t_not_used = new Ticket(1L, "MDS3456", issueDate1, expirationDate1, UserTicketType.STUDENT,
				TimeTicketType.SINGLE, TrafficZone.SECOND, true, TrafficType.BUS, 100.00, false, p);
		Mockito.when(this.ticketRepository_mock.findBySerialNo("MDS3456")).thenReturn(Optional.of(t_not_used));

		// inspektor za proveru karata
		Inspector i = new Inspector("Danka Novkovic", "12348787", "dankica", "dankadanka", "danka@gmail.com",
				Role.INSPECTOR, new Address(1L, "Vuka Karadzica", "Novi Sad", 21000));
		Mockito.when(this.userService_mock.getUser("dankica")).thenReturn(i);

		// izvestaj, stavimo karte u
		ArrayList<Ticket> tickets = new ArrayList<>();
		tickets.add(t_not_used);
		tickets.add(t_is_not_valid);
		tickets.add(t_is_used);
		Mockito.when(this.ticketRepository_mock.findAll()).thenReturn(tickets);
	}

	/*
	 * 1. test koji baca user not found exception
	 */

	@Test(expected = UserNotFoundException.class)
	public void buyTicket_whenUserNotExists_throwException()
			throws WrongTrafficTypeException, UserNotFoundException, WrongTrafficZoneException,
			WrongTicketTimeException, PriceItemNotFoundException, NotFoundActivePricelistException {
		TicketDto t = new TicketDto("month", "first", "plain", 10.0);
		String username = "perap";
		TicketReaderDto buyTicket = this.ticketService.buyTicket(t, username);
	}

	/*
	 * 2. test koji baca wrong traffic type
	 */
	@Test(expected = WrongTrafficTypeException.class)
	public void buyTicket_whenSendWrongTrafficType_throwException()
			throws WrongTrafficTypeException, UserNotFoundException, WrongTrafficZoneException,
			WrongTicketTimeException, PriceItemNotFoundException, NotFoundActivePricelistException {
		TicketDto t = new TicketDto("month", "first", "plain", 10.0);
		String username = "peraperic";
		TicketReaderDto buyTicket = this.ticketService.buyTicket(t, username);
	}

	/*
	 * 3. test koji baca wrong zone type
	 */

	@Test(expected = WrongTrafficZoneException.class)
	public void buyTicket_whenSendWrongZone_throwException()
			throws WrongTrafficTypeException, UserNotFoundException, WrongTrafficZoneException,
			WrongTicketTimeException, PriceItemNotFoundException, NotFoundActivePricelistException {
		TicketDto t = new TicketDto("month", "fourth", "bus", 10.0);
		String username = "peraperic";
		TicketReaderDto buyTicket = this.ticketService.buyTicket(t, username);
	}

	/*
	 * 4. test koji baca wrong time type
	 */
	@Test(expected = WrongTicketTimeException.class)
	public void buyTicket_whenSendWrongTicketTimeType_throwException()
			throws WrongTrafficTypeException, UserNotFoundException, WrongTrafficZoneException,
			WrongTicketTimeException, PriceItemNotFoundException, NotFoundActivePricelistException {
		TicketDto t = new TicketDto("weekly", "first", "bus", 10.0);
		String username = "peraperic";
		TicketReaderDto buyTicket = this.ticketService.buyTicket(t, username);
	}

	/*
	 * 5. cenovnik ne postoji treba da nam baci exception da stavka cenovnika ne
	 * postoji
	 */

	@Test(expected = PriceItemNotFoundException.class)
	public void getTicketPrice_whenSendWrongTrafficType_throwException()
			throws PriceItemNotFoundException, UserNotFoundException, NotFoundActivePricelistException,
			WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException {
		TicketDto t = new TicketDto("month", "first", "tram", 10.0);
		String username = "peraperic";
		double price = this.ticketService.getTicketPrice(t, username);
	}

	/*
	 * 6. vrati nam stavku cenovnika, samo treba da proverimo da li dobro
	 * izracuna cenu za trazenog korisnika
	 */
	@Test
	public void getTicketPrice_whenPassengerIsStudent()
			throws PriceItemNotFoundException, UserNotFoundException, NotFoundActivePricelistException,
			WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException {
		TicketDto t = new TicketDto("month", "first", "bus", 10.0);
		String username = "peraperic";
		double price = this.ticketService.getTicketPrice(t, username);

		assertTrue(price == 850);
	}

	/*
	 * 7. proveravamo cenu za regularnog korisnika
	 */
	@Test
	public void getTicketPrice_whenPassengerIsRegular()
			throws PriceItemNotFoundException, UserNotFoundException, NotFoundActivePricelistException,
			WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException {
		TicketDto t = new TicketDto("month", "first", "bus", 10.0);
		String username = "pericpera";
		double price = this.ticketService.getTicketPrice(t, username);

		assertTrue(price == 1000);
	}
	/*
	 * 8. proveravamo cenu za invalida
	 */

	@Test
	public void getTicketPrice_whenPassengerIsHandycap()
			throws PriceItemNotFoundException, UserNotFoundException, NotFoundActivePricelistException,
			WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException {
		TicketDto t = new TicketDto("month", "first", "bus", 10.0);
		String username = "petraperic";
		double price = this.ticketService.getTicketPrice(t, username);

		assertTrue(price == 920);

	}

	/*
	 * 9. proveravamo cenu za penzionera
	 */
	@Test
	public void getTicketPrice_whenPassengerIsSenior()
			throws PriceItemNotFoundException, UserNotFoundException, NotFoundActivePricelistException,
			WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException {
		TicketDto t = new TicketDto("month", "first", "bus", 10.0);
		String username = "mikaperic";
		double price = this.ticketService.getTicketPrice(t, username);

		assertTrue(price == 950);
	}

	/*
	 * 10. testiramo metodu koja racuna vreme trajanja karte
	 */

	@Test
	public void test_calculateExpirationDate() {
		Date issueDate = new java.sql.Date(new GregorianCalendar(2018, Calendar.FEBRUARY, 11).getTime().getTime());
		// sad pozivamo funkciju

		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

		Date expirationDate = this.ticketService.calculateExpirationDate(TimeTicketType.ANNUAL, issueDate);
		// proverimo da li smo dobili ispravan datum
		// treba da dobijemo 11.februar.2019
		Date trueDate = new java.sql.Date(new GregorianCalendar(2019, Calendar.FEBRUARY, 11).getTime().getTime());
		assertTrue("annual ticket ", fmt.format(expirationDate).equals(fmt.format(trueDate)));

		/*
		 * sad proverimo za mesec
		 */
		//
		Date expirationDateMonth = this.ticketService.calculateExpirationDate(TimeTicketType.MONTH, issueDate);
		// proverimo da li smo dobili ispravan datum
		// treba da dobijemo 11.mart.2018
		Date trueDateMonth = new java.sql.Date(new GregorianCalendar(2018, Calendar.MARCH, 11).getTime().getTime());
		assertTrue("month ticket", fmt.format(expirationDateMonth).equals(fmt.format(trueDateMonth)));

		/*
		 * za dnevnu kartu
		 */
		Date expirationDateDaily = this.ticketService.calculateExpirationDate(TimeTicketType.DAILY, issueDate);
		// proverimo da li smo dobili ispravan datum
		// treba da dobijemo 12.februar.2018
		Date trueDateDaily = new java.sql.Date(new GregorianCalendar(2018, Calendar.FEBRUARY, 12).getTime().getTime());
		assertTrue("daily ticket", fmt.format(expirationDateDaily).equals(fmt.format(trueDateDaily)));

		/*
		 * za jednokratnu kartu, ona vazi 15 dana
		 */
		Date expirationDateSingle = this.ticketService.calculateExpirationDate(TimeTicketType.SINGLE, issueDate);
		// proverimo da li smo dobili ispravan datum
		// treba da dobijemo 26.februar.2018
		Date trueDateSingle = new java.sql.Date(new GregorianCalendar(2018, Calendar.FEBRUARY, 26).getTime().getTime());
		assertTrue("single ticket", fmt.format(expirationDateSingle).equals(fmt.format(trueDateSingle)));
	}

	// testiranje metode koja pravi serijski broj karte, da li nam dobro izvuce
	// prva slova
	@Test
	public void test_generateSerialNumber() {
		String serial_no = this.ticketService.generateSerialNumber(TrafficType.BUS, TimeTicketType.MONTH,
				TrafficZone.FIRST, UserTicketType.HANDYCAP);
		assertTrue(serial_no.startsWith("BMFH"));

	}

	// testiranje upotrebe karte
	/*
	 * a. karta kojoj je istekao rok trajanja
	 */
	@Test(expected = TicketIsNotValidException.class)
	public void test_useTicket_ticketIsNotValid()
			throws TicketNotFound, TicketAlreadyUsedException, TicketIsNotValidException, WrongTrafficZoneException, ZonesDoNotMatchException {
		boolean t = this.ticketService.useTicket("MSDE3445", "pericpera", "second");
	}

	/*
	 * b. trazen karta ne postoji
	 */
	@Test(expected = TicketNotFound.class)
	public void test_useTicket_whenTicketWithSerialNo_does_not_exist()
			throws TicketNotFound, TicketAlreadyUsedException, TicketIsNotValidException, WrongTrafficZoneException, ZonesDoNotMatchException {
		boolean t = this.ticketService.useTicket("MDF1236", "pericpera", "second");
	}

	/*
	 * c. kada je karta vec koriscena, a single
	 */
	@Test(expected = TicketAlreadyUsedException.class)
	public void test_useTicket_whenSingleTicketAlreadyUsed()
			throws TicketNotFound, TicketAlreadyUsedException, TicketIsNotValidException, WrongTrafficZoneException, ZonesDoNotMatchException {
		boolean t = this.ticketService.useTicket("MDS345", "pericpera", "second");
	}
	/*
	 * d. pogresna zona, treba da baci wrong traffic zone
	 */
	@Test(expected = WrongTrafficZoneException.class)
	public void test_useTicket_whenWrongTrafficZone()
			throws TicketNotFound, TicketAlreadyUsedException, TicketIsNotValidException, WrongTrafficZoneException, ZonesDoNotMatchException {
		boolean t = this.ticketService.useTicket("MDS345", "pericpera", "f");
	}
	
	/*
	 *  e. kada je karta kupljena za drugu zonu
	 */
	@Test(expected = ZonesDoNotMatchException.class)
	public void test_useTicket_whenZonesDoNotMatch()
			throws TicketNotFound, TicketAlreadyUsedException, TicketIsNotValidException, WrongTrafficZoneException, ZonesDoNotMatchException {
		boolean t = this.ticketService.useTicket("MDS345", "pericpera", "first");
	}
	

	/*
	 * testiranje provere karte od strane inspektora
	 */

	// a. testiranje kada inspektor koji pregleda kartu ne postoji
	@Test(expected = UserNotFoundException.class)
	public void test_checkTicket_whenInspectorNotFound()
			throws TicketNotFound, TicketIsNotUseException, TicketIsNotValidException, UserNotFoundException, WrongTrafficZoneException, ZonesDoNotMatchException {
		TicketReaderDto t = this.ticketService.checkTicket("MDS3456", "kkkkk", "first");
	}

	// b. testiranje kada karta ne postoji
	@Test(expected = TicketNotFound.class)
	public void test_checkTicket_whenTicketNotFound()
			throws TicketNotFound, TicketIsNotUseException, TicketIsNotValidException, UserNotFoundException, WrongTrafficZoneException, ZonesDoNotMatchException {
		TicketReaderDto t = this.ticketService.checkTicket("MSSS1236", "dankica", "first");
	}

	// c. testiranje kada je istekao period vazenja karte
	@Test(expected = TicketIsNotValidException.class)
	public void test_checkTicket_whenTicketIsNotValid()
			throws TicketNotFound, TicketIsNotUseException, TicketIsNotValidException, UserNotFoundException, WrongTrafficZoneException, ZonesDoNotMatchException {
		TicketReaderDto t = this.ticketService.checkTicket("MSDE3445", "dankica", "second");
	}

	// d. testiranje kada single karta nije iskoriscena
	@Test(expected = TicketIsNotUseException.class)
	public void test_checkTicket_whenTicketIsNotUse()
			throws TicketNotFound, TicketIsNotUseException, TicketIsNotValidException, UserNotFoundException, WrongTrafficZoneException, ZonesDoNotMatchException {
		TicketReaderDto t = this.ticketService.checkTicket("MDS3456", "dankica", "second");
	}
	
	// e. kada  je pogresna zona
	@Test(expected = WrongTrafficZoneException.class)
	public void test_checkTicket_whenWrongTrafficZone()
			throws TicketNotFound, TicketIsNotUseException, TicketIsNotValidException, UserNotFoundException, WrongTrafficZoneException, ZonesDoNotMatchException {
		TicketReaderDto t = this.ticketService.checkTicket("MDS3456", "dankica", "s");
	}
	
	//f. ako je karta ocitana u pogresnoj zoni 
	@Test(expected = ZonesDoNotMatchException.class)
	public void test_checkTicket_whenZonesDoNotMatch()
			throws TicketNotFound, TicketIsNotUseException, TicketIsNotValidException, UserNotFoundException, WrongTrafficZoneException, ZonesDoNotMatchException {
		TicketReaderDto t = this.ticketService.checkTicket("MDS3456", "dankica", "first");
	}

	/*
	 * TESTIRANJE MESECNOG IZVESTAJA
	 */
	// kada format meseca nije dobar
	@Test(expected = IllegalArgumentException.class)
	public void test_monthReports_whenMonthNotCorrect() {
		ReportDto result = this.ticketService.getMonthReport(0, 2018);

	}

	// pogresna godina
	@Test(expected = IllegalArgumentException.class)
	public void test_monthReports_whenYearNotCorrect() {
		ReportDto result = this.ticketService.getMonthReport(1, 2020);

	}

	// kada nam vrati izvstaj za odredjeni mesec, npr za februar, 2017, treba da
	// nam vrati 1 kartu
	@Test
	public void test_monthReports_OK() {
		ReportDto result = this.ticketService.getMonthReport(2, 2017);
		assertNotNull(result);
		assertTrue(result.getNumOfStudentYearTicket() == 1);
		assertTrue(result.getNumOfBusTicket() == 1);
		assertTrue(result.getMoney() == 10000);

	}

	// test kada se traze karte za korisnika koji ne postoji
	@Test(expected = UserNotFoundException.class)
	public void test_getTicket_whenUsetNotFound() throws UserNotFoundException {
		Collection<TicketReaderDto> result = this.ticketService.allTicket("username", null);
	}
}
