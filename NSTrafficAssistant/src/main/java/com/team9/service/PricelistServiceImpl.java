package com.team9.service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team9.dto.PriceItemDto;
import com.team9.dto.PricelistDto;
import com.team9.dto.PricelistReaderDto;
import com.team9.exceptions.NotFoundActivePricelistException;
import com.team9.exceptions.PriceItemAlreadyExistsException;
import com.team9.exceptions.PriceLessThanZeroException;
import com.team9.exceptions.WrongDiscountException;
import com.team9.exceptions.WrongNumberOfPriceItemException;
import com.team9.exceptions.WrongTicketTimeException;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.exceptions.WrongUserTicketTypeException;
import com.team9.model.PriceList;
import com.team9.repository.PriceListRepository;

@Service
public class PricelistServiceImpl implements PriceListService {

	@Autowired 
	private PriceListRepository pricelistRepo;
	
	@Autowired
	private PricelistItemService pricelistItemService;
	
	@Override
	public PriceList getPricelist() throws NotFoundActivePricelistException{
		return pricelistRepo.findByActivateTrue().orElseThrow(() -> new NotFoundActivePricelistException("There is no active price list!"));
	}
	
	@Override
	public PricelistReaderDto getValidPricelist() throws NotFoundActivePricelistException {
		// vratimo raspored koji je aktivan, jer u jednom momentu moze da bude aktivan samo jedan raspored
		return convertToReaderDto(getPricelist());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PricelistReaderDto addPricelist(PricelistDto pricelist) throws ParseException, PriceItemAlreadyExistsException, PriceLessThanZeroException, WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException, WrongDiscountException, WrongNumberOfPriceItemException {
		//1.konvertujemo prosledjeni raspored
		PriceList pl = convertDtoToPricelist(pricelist);
		
		//2. cenovnik koji nam je bio aktivan do sada setujemo na false
		PriceList activePricelist;
		try {
			activePricelist = getPricelist();
			activePricelist.setActivate(false);
			activePricelist.setExpirationDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
			activePricelist = this.pricelistRepo.save(activePricelist);
			
		} catch (NotFoundActivePricelistException e) {
			// TODO Auto-generated catch block
			//nista se ne desava samo nastavimo dalje
			
		}
		
		//3. resili smo sve u vezi do sada vazeceg cenovnika, sad sacuvamo novi raspored
		PriceList newPricelist = this.pricelistRepo.save(pl);
		//4. sad unesemo stavke cenovnika
		if(newPricelist != null){
			newPricelist = this.pricelistItemService.addPricelistItems(pricelist.getItems(), pl);
		}
		
		return convertToReaderDto(newPricelist);
		
	}

	private PricelistReaderDto convertToReaderDto(PriceList savepl) {
		// TODO Auto-generated method stub
		if(savepl == null){
			return null;
		}
		Set<PriceItemDto> itemsDto = this.pricelistItemService.convertToDto(savepl.getItems());
		PricelistReaderDto prdto = new PricelistReaderDto(savepl.getId(), savepl.getIssueDate(), savepl.getExpirationDate(), savepl.isActivate(), itemsDto);
		return prdto;
	}

	private PriceList convertDtoToPricelist(PricelistDto pricelist) throws ParseException {
		// imam sledece atribute:
		//pricelist : date issuedate, date expirationdate, activate 
		//ISSUE DATE JE DANASNJI DAN + 1
		java.sql.Date issueDate =  new java.sql.Date(new java.util.Date().getTime());
		//i jos dodamo jedan dan na ovo
		LocalDate date = issueDate.toLocalDate();
		
		date = date.plusDays(1);
		
		
		PriceList pl = new PriceList(java.sql.Date.valueOf(date),null, true);
		
		return pl;
	}


	

}
