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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.team9.dto.ReportDto;
import com.team9.dto.TicketDto;
import com.team9.dto.TicketReaderDto;
import com.team9.exceptions.NotFoundActivePricelistException;
import com.team9.exceptions.PriceItemNotFoundException;
import com.team9.exceptions.TicketAlreadyUsedException;
import com.team9.exceptions.TicketNotFound;
import com.team9.exceptions.UserNotFoundException;
import com.team9.exceptions.WrongTicketTimeException;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.model.Passenger;
import com.team9.model.PriceItem;
import com.team9.model.PriceList;
import com.team9.model.Ticket;
import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.model.UserTicketType;
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

	public Passenger getPassengerByUsername(String username) throws UserNotFoundException {
		
		Passenger p = (Passenger) this.userService.getUser(username);

		if (p == null) {
			throw new UserNotFoundException("User does not exist!");
		}
		
		return p;

	}

	@Override
	public TicketReaderDto buyTicket(TicketDto t, String username) throws WrongTrafficTypeException, UserNotFoundException, WrongTrafficZoneException, WrongTicketTimeException, PriceItemNotFoundException, NotFoundActivePricelistException {
		// kupovina karte
		// sa servera nam stignu potrebni podaci
		// izvucemo trazenog korisnika na osnovu username-a
		Ticket buyTicket = new Ticket();
		Passenger passenger = getPassengerByUsername(username);
		buyTicket.setPassenger(passenger);
		
		//proverimo da li su nam prosledjeni parametri ispravni 
		//1. tip prevoza
		
		buyTicket.setTrafficType(ConverterService.convertStringToTrafficType(t.getTrafficType()));
		//2. zone 
		
		buyTicket.setTrafficZone(ConverterService.convertStringToZone(t.getTrafficZone()));
		//3. time type
		buyTicket.setTimeType(ConverterService.convertStringToTimeTicketType(t.getTimeType()));
		//na osnovu korisnika znamo kog je tipa karta
		UserTicketType ut = UserTicketType.REGULAR;
		if(passenger.getActivate() == true){
			ut = passenger.getUserTicketType();
		}
		buyTicket.setUserType(ut);
		//treba da izracunamo trajanje karte, za pocetni datum stavimo danasnji datum
		buyTicket.setIssueDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		//pozovemo funkciju koja ce da nam vrati izracunati period u odnosu na datum  kupovine karte i na izabrani period
		buyTicket.setExpirationDate(calculateExpirationDate(buyTicket.getTimeType(), buyTicket.getIssueDate()));
		buyTicket.setActive(true);
		buyTicket.setUsed(false);
		//jos izgenerisemo serijski broj za kartu
		buyTicket.setSerialNo(generateSerialNumber(buyTicket.getTrafficType(), buyTicket.getTimeType(), buyTicket.getTrafficZone(), ut));
		
		//cena karte, vec imamo povucenu cenu sa fronta, ali cemo je uporediti  sa cenom u bazi za svaki slucaj 
		double price = getTicketPrice(t, username);
		/**
		 * TREBA RAZMISLITI DA LI JE POTREBNO UPOREDJIVATI ILI JE DOVOLJNO SAMO UZETI CENU IZ BAZE
		 */
		buyTicket.setPrice(price);
		
		//sacuvamo kartu
		Ticket saveTicket = this.ticketRepository.save(buyTicket);
		return convertToTicketDto(saveTicket);
	}

	private TicketReaderDto convertToTicketDto(Ticket saveTicket) {
		// konvertovanje ticket-a u dto objekat za slanje na front
		 TicketReaderDto t = new TicketReaderDto(saveTicket.getId(), saveTicket.getSerialNo(), saveTicket.getIssueDate(), saveTicket.getExpirationDate(), saveTicket.getUserType(),saveTicket.getTimeType()
				 , saveTicket.getTrafficZone(), saveTicket.getActive(), saveTicket.getTrafficType(),saveTicket.getPrice(), saveTicket.getPassenger().getUsername());
		return t;
	}

	@Override
	public String generateSerialNumber(TrafficType trafficType, TimeTicketType timeType, TrafficZone trafficZone,
			UserTicketType ut) {
		// prva slova od enuma i plus neki broj
		String serialNum = "";
		serialNum+=trafficType.name().substring(0, 1) + timeType.name().substring(0, 1) + trafficZone.name().substring(0,1) + ut.name().substring(0,1);
		long uniqueNum = ( new java.util.Date()).getTime();
		serialNum+=uniqueNum;
		return serialNum;
	}

	@Override
	public  Date calculateExpirationDate(TimeTicketType timeType, Date issueDate) {
		//vraca nam izracunati period vazenja karte u odnosu na datum kupovine karte i izabrani period
		LocalDate date = issueDate.toLocalDate();
		if(timeType == TimeTicketType.ANNUAL){
			//povecamo samo godinu
			date = date.plusYears(1);
		}else if (timeType == TimeTicketType.DAILY){
			//vazi tog istog dana do 24h
			date = date.plusDays(1);			
		}else if(timeType == TimeTicketType.MONTH){
			//menjamo mesec 
			date = date.plusMonths(1);
		}else if(timeType == TimeTicketType.SINGLE){
			//vazi 15 dana, a moze samo jednom da se upotrebi 
			date  = date.plusDays(15);
		}
		
		return java.sql.Date.valueOf(date);
	}

	@Override
	public Collection<TicketReaderDto> allTicket(Pageable pageable, String username) {
		// return all tickets for one passenger
		Passenger passenger = (Passenger) userService.getUser(username);
		Page<Ticket> allTicket = ticketRepository.findByPassenger(passenger, pageable);
		
		List<TicketReaderDto> tickets = new ArrayList<TicketReaderDto>();
		for(Ticket t : allTicket){
			tickets.add(convertToTicketDto(t));
		}
		
		return tickets;
	}

	@Override
	public double getTicketPrice(TicketDto t,String username) throws PriceItemNotFoundException, UserNotFoundException, NotFoundActivePricelistException, WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException {
		// TODO Auto-generated method stub
		PriceList pl = pricelistService.getPricelist();
		double price = 0;
		// nadjemo korisnika da bi videli koja mu je uloga
		Passenger p  = getPassengerByUsername(username);
		UserTicketType ut = UserTicketType.REGULAR;
		if (p.getActivate() == true) {
			ut = p.getUserTicketType();
		}

		// pronadjemo priceitem na osnovu svih zadatih parametara
		TrafficType type = ConverterService.convertStringToTrafficType(t.getTrafficType());
		TimeTicketType time = ConverterService.convertStringToTimeTicketType(t.getTimeType());
		TrafficZone zone = ConverterService.convertStringToZone(t.getTrafficZone());
		PriceItem priceItem = this.priceItemService.getPriceItem(type, time, zone, pl);
		//sad posto smo nasli treba da izracunamo cenu 

		if (priceItem != null) {
			price = calculatePrice(priceItem, ut);
		}
		return price;
	}

	private double calculatePrice(PriceItem priceItem, UserTicketType ut) {
		// na osnovu regularne cene i tipa korisnika racunamo cenu karte
		double discount = 0 ;
		if(ut == UserTicketType.HANDYCAP){
			discount = priceItem.getHandycapDiscount();
		}else if(ut == UserTicketType.SENIOR){
			discount = priceItem.getSeniorDiscount();
		}else if(ut == UserTicketType.STUDENT){
			discount = priceItem.getStudentDiscount();
		}
		double regular_price = priceItem.getPrice();
		
		double price = regular_price - ((discount/100)*regular_price);
		
		return price;
	}

	@Override
	public Set<TicketReaderDto> getReports(ReportDto report) {
		return null;
		
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
	public boolean useTicket(String serialNo, String username) throws TicketNotFound, TicketAlreadyUsedException {
		// 1. na osnovu serijskog broja pronadjemo kartu u bazi 
		Ticket foundTicket = this.ticketRepository.findBySerialNo(serialNo).orElseThrow(()->new TicketNotFound());
		Date today = new Date(new java.util.Date().getTime());
		if(foundTicket.getIssueDate().before(today) && today.before(foundTicket.getExpirationDate())){
			//proverimo da li je karta single, ako jeste i ako je vec koriscena bacamo gresku
			if(foundTicket.getTimeType() == TimeTicketType.SINGLE){
				if (foundTicket.isUsed() == true){
					throw new TicketAlreadyUsedException();
				}else{
					foundTicket.setUsed(true); //ako nije koriscena setujemo na true
				}
			}
			Ticket saveTicket = this.ticketRepository.save(foundTicket);//update karte
			return true;//iskoriscena karta
		}
		
		return false;
	}

}
