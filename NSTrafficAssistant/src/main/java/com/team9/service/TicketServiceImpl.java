package com.team9.service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.team9.dto.ReportDto;
import com.team9.dto.TicketDto;
import com.team9.dto.TicketReaderDto;
import com.team9.dto.TicketsAndSizeDto;
import com.team9.exceptions.LineNotFoundException;
import com.team9.exceptions.NotFoundActivePricelistException;
import com.team9.exceptions.PriceItemNotFoundException;
import com.team9.exceptions.TicketAlreadyUsedException;
import com.team9.exceptions.TicketIsNotUseException;
import com.team9.exceptions.TicketIsNotValidException;
import com.team9.exceptions.TicketNotFound;
import com.team9.exceptions.UserNotFoundException;
import com.team9.exceptions.WrongReportTypeException;
import com.team9.exceptions.WrongTicketTimeException;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.exceptions.ZonesDoNotMatchException;
import com.team9.model.Inspector;
import com.team9.model.Line;
import com.team9.model.Passenger;
import com.team9.model.PriceItem;
import com.team9.model.PriceList;
import com.team9.model.Ticket;
import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.model.UserTicketType;
import com.team9.repository.LineRepository;
import com.team9.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private PriceListService pricelistService;

	@Autowired
	private PricelistItemService priceItemService;
	
	@Autowired
	private LineRepository lineRepository;

	public Passenger getPassengerByUsername(String username) throws UserNotFoundException {

		Passenger p = (Passenger) this.userService.getUser(username);

		if (p == null) {
			throw new UserNotFoundException("User does not exist!");
		}

		return p;

	}

	@Override
	public TicketReaderDto buyTicket(TicketDto t, String username)
			throws WrongTrafficTypeException, UserNotFoundException, WrongTrafficZoneException,
			WrongTicketTimeException, PriceItemNotFoundException, NotFoundActivePricelistException {
		// kupovina karte
		// sa servera nam stignu potrebni podaci
		// izvucemo trazenog korisnika na osnovu username-a
		Ticket buyTicket = new Ticket();
		Passenger passenger = getPassengerByUsername(username);
		buyTicket.setPassenger(passenger);

		// proverimo da li su nam prosledjeni parametri ispravni
		// 1. tip prevoza

		buyTicket.setTrafficType(ConverterService.convertStringToTrafficType(t.getTrafficType()));
		// 2. zone

		buyTicket.setTrafficZone(ConverterService.convertStringToZone(t.getTrafficZone()));
		// 3. time type
		buyTicket.setTimeType(ConverterService.convertStringToTimeTicketType(t.getTimeType()));
		// na osnovu korisnika znamo kog je tipa karta
		UserTicketType ut = UserTicketType.REGULAR;
		if (passenger.getActivate() == true) {
			ut = passenger.getUserTicketType();
		}
		buyTicket.setUserType(ut);
		// treba da izracunamo trajanje karte, za pocetni datum stavimo danasnji
		// datum
		buyTicket.setIssueDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		// pozovemo funkciju koja ce da nam vrati izracunati period u odnosu na
		// datum kupovine karte i na izabrani period
		buyTicket.setExpirationDate(calculateExpirationDate(buyTicket.getTimeType(), buyTicket.getIssueDate()));
		buyTicket.setActive(true);
		buyTicket.setUsed(false);
		// jos izgenerisemo serijski broj za kartu
		buyTicket.setSerialNo(generateSerialNumber(buyTicket.getTrafficType(), buyTicket.getTimeType(),
				buyTicket.getTrafficZone(), ut));

		// cena karte, vec imamo povucenu cenu sa fronta, ali cemo je uporediti
		// sa cenom u bazi za svaki slucaj
		double price = getTicketPrice(t, username);
		/**
		 * TREBA RAZMISLITI DA LI JE POTREBNO UPOREDJIVATI ILI JE DOVOLJNO SAMO
		 * UZETI CENU IZ BAZE
		 */
		buyTicket.setPrice(price);

		// sacuvamo kartu
		Ticket saveTicket = this.ticketRepository.save(buyTicket);
		return convertToTicketDto(saveTicket);
	}

	private TicketReaderDto convertToTicketDto(Ticket saveTicket) {
		// konvertovanje ticket-a u dto objekat za slanje na front
		ArrayList<String> inspectors = new ArrayList<>();
		if (saveTicket.getCheckInspectors() != null) {
			for (Inspector i : saveTicket.getCheckInspectors()) {
				inspectors.add(i.getUsername());
			}
		}
		TicketReaderDto t = new TicketReaderDto(saveTicket.getId(), saveTicket.getSerialNo(), saveTicket.getIssueDate(),
				saveTicket.getExpirationDate(), saveTicket.getUserType(), saveTicket.getTimeType(),
				saveTicket.getTrafficZone(), saveTicket.getActive(), saveTicket.getTrafficType(), saveTicket.getPrice(),
				saveTicket.getPassenger().getUsername(), inspectors);
		return t;
	}

	@Override
	public String generateSerialNumber(TrafficType trafficType, TimeTicketType timeType, TrafficZone trafficZone,
			UserTicketType ut) {
		// prva slova od enuma i plus neki broj
		String serialNum = "";
		serialNum += trafficType.name().substring(0, 1) + timeType.name().substring(0, 1)
				+ trafficZone.name().substring(0, 1) + ut.name().substring(0, 1);
		long uniqueNum = (new java.util.Date()).getTime();
		serialNum += uniqueNum;
		return serialNum;
	}

	@Override
	public Date calculateExpirationDate(TimeTicketType timeType, Date issueDate) {
		// vraca nam izracunati period vazenja karte u odnosu na datum kupovine
		// karte i izabrani period
		LocalDate date = issueDate.toLocalDate();
		if (timeType == TimeTicketType.ANNUAL) {
			// povecamo samo godinu
			date = date.plusYears(1);
		} else if (timeType == TimeTicketType.DAILY) {
			// vazi tog istog dana do 24h
			date = date.plusDays(1);
		} else if (timeType == TimeTicketType.MONTH) {
			// menjamo mesec
			date = date.plusMonths(1);
		} else if (timeType == TimeTicketType.SINGLE) {
			// vazi 15 dana, a moze samo jednom da se upotrebi
			date = date.plusDays(15);
		}

		return java.sql.Date.valueOf(date);
	}

	@Override
	public TicketsAndSizeDto myTicket(String username, int page, int size) throws UserNotFoundException {
		// return all tickets for one passenger
		Passenger passenger = getPassengerByUsername(username);
		Page<Ticket> allTicket = ticketRepository.findByPassenger(passenger, new PageRequest(page, size));

		List<TicketReaderDto> tickets = new ArrayList<TicketReaderDto>();

		for (Ticket t : allTicket) {
			tickets.add(convertToTicketDto(t));
		}
		
		int numOfTicket = getNumberOfTicket(username);
		
		TicketsAndSizeDto result = new TicketsAndSizeDto(tickets, numOfTicket);

		return result;
	}

	@Override
	public double getTicketPrice(TicketDto t, String username)
			throws PriceItemNotFoundException, UserNotFoundException, NotFoundActivePricelistException,
			WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException {
		// TODO Auto-generated method stub
		PriceList pl = pricelistService.getPricelist();
		double price = 0;
		// nadjemo korisnika da bi videli koja mu je uloga
		Passenger p = getPassengerByUsername(username);
		UserTicketType ut = UserTicketType.REGULAR;
		if (p.getActivate() == true) {
			ut = p.getUserTicketType();
		}

		// pronadjemo priceitem na osnovu svih zadatih parametara
		TrafficType type = ConverterService.convertStringToTrafficType(t.getTrafficType());
		TimeTicketType time = ConverterService.convertStringToTimeTicketType(t.getTimeType());
		TrafficZone zone = ConverterService.convertStringToZone(t.getTrafficZone());
		PriceItem priceItem = this.priceItemService.getPriceItem(type, time, zone, pl);
		// sad posto smo nasli treba da izracunamo cenu

		if (priceItem != null) {
			price = calculatePrice(priceItem, ut);
		}
		return price;
	}

	private double calculatePrice(PriceItem priceItem, UserTicketType ut) {
		// na osnovu regularne cene i tipa korisnika racunamo cenu karte
		double discount = 0;
		if (ut == UserTicketType.HANDYCAP) {
			discount = priceItem.getHandycapDiscount();
		} else if (ut == UserTicketType.SENIOR) {
			discount = priceItem.getSeniorDiscount();
		} else if (ut == UserTicketType.STUDENT) {
			discount = priceItem.getStudentDiscount();
		}
		double regular_price = priceItem.getPrice();

		double price = regular_price - ((discount / 100) * regular_price);

		return price;
	}

	private String convertToString(Date date) {
		// TODO Auto-generated method stub
		if (date == null) {
			return "";
		}
		SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("dd-MM-yyyy");
		String date_to_string = dateformatyyyyMMdd.format(date);
		return date_to_string;
	}

	private Date convertToDate(String string_date) {
		// TODO Auto-generated method stub
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
		Date sqlDate = null;
		try {
			sqlDate = new java.sql.Date(dateFormat.parse(string_date).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sqlDate;
	}

	@Override
	public boolean useTicket(String serialNo, String username, Long line_id) throws TicketNotFound,
			TicketAlreadyUsedException, TicketIsNotValidException,  ZonesDoNotMatchException, LineNotFoundException {
		// konvertujemo string u zone
		Line l = this.lineRepository.findById(line_id).orElseThrow(() -> new LineNotFoundException("Line with id: " + line_id  + " does not exist!"));
		
		// 1. na osnovu serijskog broja pronadjemo kartu u bazi
		Ticket foundTicket = this.ticketRepository.findBySerialNo(serialNo)
				.orElseThrow(() -> new TicketNotFound("Ticket with serialNo: " + serialNo + " does not exist!"));
		Date today = new Date(new java.util.Date().getTime());
		if (foundTicket.getTrafficZone() == l.getZone()) {
			if (foundTicket.getIssueDate().before(today) && today.before(foundTicket.getExpirationDate())) {
				// proverimo da li je karta single, ako jeste i ako je vec
				// koriscena
				// bacamo gresku
				if (foundTicket.getTimeType() == TimeTicketType.SINGLE) {
					if (foundTicket.isUsed() == true) {
						throw new TicketAlreadyUsedException(
								"Ticket with serialNo: " + serialNo + " has already been used!");
					} else {
						foundTicket.setUsed(true); // ako nije koriscena
													// setujemo na
													// true
					}
				}
				Ticket saveTicket = this.ticketRepository.save(foundTicket);// update
																			// karte
				return true;// iskoriscena karta
			} else {
				throw new TicketIsNotValidException("Ticket with serialNo: " + serialNo + " has expired!"); // kada
																											// je
																											// istekla
																											// karta
			}
		} else {
			// bacamo gresku da karta moze da se koristi samo u zoni za koju je
			// kupljena
			throw new ZonesDoNotMatchException("The ticket can be used in the zone for which it was purchased.");
		}

	}

	@Override
	public TicketReaderDto checkTicket(String serialNo, String username, Long line_id)
			throws TicketNotFound, TicketIsNotUseException, TicketIsNotValidException, UserNotFoundException,
			 ZonesDoNotMatchException, LineNotFoundException {
		
		Line l = this.lineRepository.findById(line_id).orElseThrow(() -> new LineNotFoundException("Line with id: " + line_id  + " does not exist!"));

		// 1. pronadjemo kartu na osnovu serijskog broja
		Ticket foundTicket = this.ticketRepository.findBySerialNo(serialNo)
				.orElseThrow(() -> new TicketNotFound("Ticket with serialNo: " + serialNo + " does not exist!"));
		Date today = new Date(new java.util.Date().getTime());
		Inspector i = (Inspector) this.userService.getUser(username);// ovde
																		// treba
																		// proveriti
																		// da li
																		// postoji
																		// inspektor
		if (i == null) {
			throw new UserNotFoundException();
		}
		// prvo cemo proveriti da li je karta za odgovarajucu zonu
		if (foundTicket.getTrafficZone() == l.getZone()) {
			// proverimo da li je karta jos uvek vazeca
			if (foundTicket.getIssueDate().before(today) && today.before(foundTicket.getExpirationDate())) {
				// karta je vazeca proverimo da li je single
				if (foundTicket.getTimeType() == TimeTicketType.SINGLE) {
					if (foundTicket.isUsed() == false) {
						// karta nije upotrebljena
						throw new TicketIsNotUseException("Ticket with serialNo: " + serialNo + " was not used!");
					}

				}
				// ok , dodamo inspektora koji je proverio kartu
				Set<Inspector> inspectors = foundTicket.getCheckInspectors();
				inspectors.add(i);
				// sacuvamo u bazi
				foundTicket.setCheckInspectors(inspectors);
				Ticket updateTicket = this.ticketRepository.save(foundTicket);
				return convertToTicketDto(updateTicket);
			} else {
				throw new TicketIsNotValidException("Ticket with serialNo: " + serialNo + " has expired!");
			}
		} else {
			// bacamo error da karta moze da se koristi samo u zoni za koju je
			// kupljena
			throw new ZonesDoNotMatchException(
					"The ticket with serialNo: " + serialNo + " was not used in the appropriate zone.");
		}
	}

	@Override
	public ReportDto getReport(int month, int year, String reportType)
			throws IllegalArgumentException, WrongReportTypeException {
		// TODO Auto-generated method stub
		Collection<Ticket> allTicket = (Collection<Ticket>) this.ticketRepository.findAll();

		List<Ticket> report = new ArrayList<Ticket>();
		if ((month > 12 || month < 1) || (year > 2019)) {
			throw new IllegalArgumentException();
		}
		// ovo su sve karte koje su kupljene u trazenom mesecu, sad treba da
		// izdvojimo odredjene karte
		if (reportType.toUpperCase().equals("MONTH")) {
			report = allTicket.stream().filter(t -> t.getIssueDate().toLocalDate().getMonthValue() == month
					&& t.getIssueDate().toLocalDate().getYear() == year).collect(Collectors.toList());
		} else if (reportType.toUpperCase().equals("YEAR")) {
			report = allTicket.stream().filter(t -> t.getIssueDate().toLocalDate().getYear() == year)
					.collect(Collectors.toList());
		} else {
			throw new WrongReportTypeException("Wrong report type!");
		}

		ReportDto result = new ReportDto();

		// 1. izracunamo sve sto je potrebno za studentske karte
		int numStudentMonthTicket = report.stream()
				.filter(t -> t.getUserType() == UserTicketType.STUDENT && t.getTimeType() == TimeTicketType.MONTH)
				.collect(Collectors.toList()).size();
		int numStudentYearTicket = report.stream()
				.filter(t -> t.getUserType() == UserTicketType.STUDENT && t.getTimeType() == TimeTicketType.ANNUAL)
				.collect(Collectors.toList()).size();
		int numStudentDailyTicket = report.stream()
				.filter(t -> t.getUserType() == UserTicketType.STUDENT && t.getTimeType() == TimeTicketType.DAILY)
				.collect(Collectors.toList()).size();
		;
		int numStudentSingleTicket = report.stream()
				.filter(t -> t.getUserType() == UserTicketType.STUDENT && t.getTimeType() == TimeTicketType.SINGLE)
				.collect(Collectors.toList()).size();
		;

		result.setNumOfStudentDailyTicket(numStudentDailyTicket);
		result.setNumOfStudentMonthTicket(numStudentMonthTicket);
		result.setNumOfStudentSingleTicket(numStudentSingleTicket);
		result.setNumOfStudentYearTicket(numStudentYearTicket);

		// 2. izracunamo sve za penzionere
		int numSeniorMonthTicket = report.stream()
				.filter(t -> t.getUserType() == UserTicketType.SENIOR && t.getTimeType() == TimeTicketType.MONTH)
				.collect(Collectors.toList()).size();
		int numSeniorYearTicket = report.stream()
				.filter(t -> t.getUserType() == UserTicketType.SENIOR && t.getTimeType() == TimeTicketType.ANNUAL)
				.collect(Collectors.toList()).size();
		int numSeniorDailyTicket = report.stream()
				.filter(t -> t.getUserType() == UserTicketType.SENIOR && t.getTimeType() == TimeTicketType.DAILY)
				.collect(Collectors.toList()).size();
		;
		int numSeniorSingleTicket = report.stream()
				.filter(t -> t.getUserType() == UserTicketType.SENIOR && t.getTimeType() == TimeTicketType.SINGLE)
				.collect(Collectors.toList()).size();
		;

		result.setNumOfSeniorDailyTicket(numSeniorDailyTicket);
		result.setNumOfSeniorMonthTicket(numSeniorMonthTicket);
		result.setNumOfSeniorSingleTicket(numSeniorSingleTicket);
		result.setNumOfSeniorYearTicket(numSeniorYearTicket);

		// 3. izracunamo sve za invalide
		int numHandycapMonthTicket = report.stream()
				.filter(t -> t.getUserType() == UserTicketType.HANDYCAP && t.getTimeType() == TimeTicketType.MONTH)
				.collect(Collectors.toList()).size();
		int numHandycapYearTicket = report.stream()
				.filter(t -> t.getUserType() == UserTicketType.HANDYCAP && t.getTimeType() == TimeTicketType.ANNUAL)
				.collect(Collectors.toList()).size();
		int numHandycapDailyTicket = report.stream()
				.filter(t -> t.getUserType() == UserTicketType.HANDYCAP && t.getTimeType() == TimeTicketType.DAILY)
				.collect(Collectors.toList()).size();
		;
		int numHandycapSingleTicket = report.stream()
				.filter(t -> t.getUserType() == UserTicketType.HANDYCAP && t.getTimeType() == TimeTicketType.SINGLE)
				.collect(Collectors.toList()).size();
		;

		result.setNumOfHandycapDailyTicket(numHandycapDailyTicket);
		result.setNumOfHandycapMonthTicket(numHandycapMonthTicket);
		result.setNumOfHandycapSingleTicket(numHandycapSingleTicket);
		result.setNumOfHandycapYearTicket(numHandycapYearTicket);

		// 4. regularne karte
		int numRegularMonthTicket = report.stream()
				.filter(t -> t.getUserType() == UserTicketType.REGULAR && t.getTimeType() == TimeTicketType.MONTH)
				.collect(Collectors.toList()).size();
		int numRegularYearTicket = report.stream()
				.filter(t -> t.getUserType() == UserTicketType.REGULAR && t.getTimeType() == TimeTicketType.ANNUAL)
				.collect(Collectors.toList()).size();
		int numRegularDailyTicket = report.stream()
				.filter(t -> t.getUserType() == UserTicketType.REGULAR && t.getTimeType() == TimeTicketType.DAILY)
				.collect(Collectors.toList()).size();
		;
		int numRegularSingleTicket = report.stream()
				.filter(t -> t.getUserType() == UserTicketType.REGULAR && t.getTimeType() == TimeTicketType.SINGLE)
				.collect(Collectors.toList()).size();
		;

		result.setNumOfRegularDailyTicket(numRegularDailyTicket);
		result.setNumOfRegularMonthTicket(numRegularMonthTicket);
		result.setNumOfRegularSingleTicket(numRegularSingleTicket);
		result.setNumOfRegularYearTicket(numRegularYearTicket);

		// 5. izracunamo za vrstu prevoza
		int numBusTicket = report.stream().filter(t -> t.getTrafficType() == TrafficType.BUS)
				.collect(Collectors.toList()).size();
		int numMetroTicket = report.stream().filter(t -> t.getTrafficType() == TrafficType.METRO)
				.collect(Collectors.toList()).size();
		int numTramTicket = report.stream().filter(t -> t.getTrafficType() == TrafficType.TRAM)
				.collect(Collectors.toList()).size();

		result.setNumOfTramTicket(numTramTicket);
		result.setNumOfBusTicket(numBusTicket);
		result.setNumOfMetroTicket(numMetroTicket);

		// 6. ukupna zarada
		double money = report.stream().mapToDouble(t -> t.getPrice()).sum();
		result.setMoney(money);

		return result;
	}

	@Override
	public TicketsAndSizeDto getAll(int page, int size) {
		// TODO Auto-generated method stub
		Page<Ticket> result = this.ticketRepository.findAll(new PageRequest(page, size));
		ArrayList<TicketReaderDto> res = new ArrayList<>();
		for (Ticket t : result) {
			res.add(convertToTicketDto(t));
		}
		
		int numOfTickets = getNumberOfAllTickets();
		TicketsAndSizeDto r = new TicketsAndSizeDto(res, numOfTickets);

		return r;

	}

	@Override
	public int getNumberOfTicket(String username) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Passenger p = getPassengerByUsername(username);
		ArrayList<Ticket> t = (ArrayList<Ticket>) this.ticketRepository.findByPassenger(p);
		return t.size();
	}

	@Override
	public int getNumberOfAllTickets() {
		// TODO Auto-generated method stub
		ArrayList<Ticket> t = (ArrayList<Ticket>)this.ticketRepository.findAll();
		return t.size();
	}

}
