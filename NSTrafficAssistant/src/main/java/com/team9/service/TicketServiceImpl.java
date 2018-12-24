package com.team9.service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team9.dto.ReportDto;
import com.team9.dto.TicketDto;
import com.team9.dto.TicketReaderDto;
import com.team9.exceptions.NotFoundActivePricelistException;
import com.team9.exceptions.PriceItemNotFoundException;
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
		// izvucemo trazenog korisnika na osnovu usrname-a
		Ticket buyTicket = new Ticket();
		Passenger passenger = getPassengerByUsername(username);
		buyTicket.setPassenger(passenger);
		
		//proverimo da li su nam prosledjeni parametri ispravni 
		//1. tip prevoza
		if(t.getTrafficType() != TrafficType.BUS && t.getTrafficType() != TrafficType.METRO && t.getTrafficType() != TrafficType.TRAM){
			throw new WrongTrafficTypeException();
		}
		buyTicket.setTrafficType(t.getTrafficType());
		//2. zone 
		if(t.getTrafficZone() != TrafficZone.FIRST && t.getTrafficZone() != TrafficZone.SECOND){
			throw new WrongTrafficZoneException();
		}
		buyTicket.setTrafficZone(t.getTrafficZone());
		//3. time type
		if(t.getTimeType() != TimeTicketType.ANNUAL && t.getTimeType() != TimeTicketType.DAILY && t.getTimeType() != TimeTicketType.MONTH && t.getTimeType() != TimeTicketType.SINGLE){
			throw new WrongTicketTimeException();
		}
		buyTicket.setTimeType(t.getTimeType());
		//na osnovu korisnika znamo kog je tipa karta
		UserTicketType ut = UserTicketType.REGULAR;
		if(passenger.getActivate() == true){
			ut = passenger.getUserTicketType();
		}
		buyTicket.setUserType(ut);
		//treba da izracunamo trajanje karte, za pocetni datum stavimo danasnji datum
		buyTicket.setIssueDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		//pozovemo funkciju koja ce da nam vrati izracunati period u odnosu na datum  kupovine karte i na izabrani period
		buyTicket.setExpirationDate(calculateExpirationDate(t.getTimeType(), buyTicket.getIssueDate()));
		buyTicket.setActive(true);
		buyTicket.setNumOfUsed(0);
		//jos izgenerisemo serijski broj za kartu
		buyTicket.setSerialNo(generateSerialNumber(t.getTrafficType(), t.getTimeType(), t.getTrafficZone(), ut));
		
		//cena karte, vec imamo povucenu cenu sa fronta, ali cemo je uporediti  sa cenom u bazi za svaki slucaj 
		double price = getTicketPrice(t.getTimeType(), t.getTrafficZone(), t.getTrafficType(), username);
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

	private String generateSerialNumber(TrafficType trafficType, TimeTicketType timeType, TrafficZone trafficZone,
			UserTicketType ut) {
		// prva slova od enuma i plus neki broj
		String serialNum = "";
		serialNum+=trafficType.name().substring(0, 1) + timeType.name().substring(0, 1) + trafficZone.name().substring(0,1) + ut.name().substring(0,1);
		long uniqueNum = ( new java.util.Date()).getTime();
		serialNum+=uniqueNum;
		return serialNum;
	}

	private Date calculateExpirationDate(TimeTicketType timeType, Date issueDate) {
		//vraca nam izracunati period vazenja karte u odnosu na datum kupovine karte i izabrani period
		LocalDate date = issueDate.toLocalDate();
		if(timeType == TimeTicketType.ANNUAL){
			//povecamo samo godinu
			date.plusYears(1);
		}else if (timeType == TimeTicketType.DAILY){
			//vazi tog istog dana do 24h
			
			
		}else if(timeType == TimeTicketType.MONTH){
			//menjamo mesec 
			date.plusMonths(1);
		}else if(timeType == TimeTicketType.SINGLE){
			//vazi 15 dana, a moze samo jednom da se upotrebi 
			date.plusDays(15);
		}
		
		return java.sql.Date.valueOf(date);
	}

	@Override
	public Collection<Ticket> allTicket(String username) {
		// return all tickets for one passenger
		Passenger passenger = (Passenger) userService.getUser(username);
		return ticketRepository.findByPassenger(passenger);
	}

	@Override
	public double getTicketPrice(TimeTicketType timeType, TrafficZone trafficZone, TrafficType trafficType,
			String username) throws PriceItemNotFoundException, UserNotFoundException, NotFoundActivePricelistException {
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
		PriceItem priceItem = this.priceItemService.getPriceItem(ut, trafficType, timeType, trafficZone, pl);

		if (priceItem != null) {
			price = priceItem.getPrice();
		}
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

}
