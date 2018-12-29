package com.team9.NSTrafficAssistant.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.team9.dto.TicketDto;
import com.team9.dto.TicketReaderDto;
import com.team9.exceptions.NotFoundActivePricelistException;
import com.team9.exceptions.PriceItemNotFoundException;
import com.team9.exceptions.UserNotFoundException;
import com.team9.exceptions.WrongTicketTimeException;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.model.Address;
import com.team9.model.Passenger;
import com.team9.model.PriceItem;
import com.team9.model.PriceList;
import com.team9.model.Role;
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
				Role.PASSANGER, new Address(1L, "Vuka Karadzica", "Novi Sad", 21000), true, UserTicketType.STUDENT);
		Mockito.when(this.userService_mock.getUser("peraperic")).thenReturn(p_student);

		Passenger p = new Passenger("Pera Peric", "123456", "pericpera", "pass", "pera@gmail.com", Role.PASSANGER,
				new Address(1L, "Vuka Karadzica", "Novi Sad", 21000), false, UserTicketType.STUDENT);
		Mockito.when(this.userService_mock.getUser("pericpera")).thenReturn(p);

		Passenger p_senior = new Passenger("Mika Peric", "123456", "mikaperic", "pass", "pera@gmail.com",
				Role.PASSANGER, new Address(1L, "Vuka Karadzica", "Novi Sad", 21000), true, UserTicketType.SENIOR);
		Mockito.when(this.userService_mock.getUser("mikaperic")).thenReturn(p_senior);

		Passenger p_handycap = new Passenger("Petra Peric", "123456", "petraperic", "pass", "pera@gmail.com",
				Role.PASSANGER, new Address(1L, "Vuka Karadzica", "Novi Sad", 21000), true, UserTicketType.HANDYCAP);
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


	//testiranje metode koja pravi serijski broj karte, da li nam dobro izvuce prva slova 
	@Test
	public void test_generateSerialNumber(){
		String serial_no = this.ticketService.generateSerialNumber(TrafficType.BUS, TimeTicketType.MONTH, TrafficZone.FIRST, UserTicketType.HANDYCAP);
		assertTrue(serial_no.startsWith("BMFH"));
		
		
	}
}
