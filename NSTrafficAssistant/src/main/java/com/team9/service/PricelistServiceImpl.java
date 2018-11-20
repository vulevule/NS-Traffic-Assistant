package com.team9.service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team9.dto.PriceItemDto;
import com.team9.dto.PricelistDto;
import com.team9.dto.PricelistReaderDto;
import com.team9.model.PriceItem;
import com.team9.model.PriceList;
import com.team9.repository.PriceListRepository;

@Service
public class PricelistServiceImpl implements PriceListService {

	@Autowired 
	private PriceListRepository pricelistRepo;
	
	@Autowired
	private PricelistItemService pricelistItemService;
	
	@Override
	public PricelistReaderDto getValidPricelist() {
		// na osnovu trenutnog datuma nadjemo vazeci raspored 
		return convertToReaderDto(pricelistRepo.findByActivateTrue());
	}

	@Override
	public PricelistReaderDto addPricelist(PricelistDto pricelist) throws ParseException {
		//dodajemo raspored zajedno sa njegovim stavkama 
		//1. prvo prebacimo sve u pricelist
		PriceList pl =  convertDtoToPricelist(pricelist);		
		//3. unesemo cenovnik u bazu, ne moze da postoji vec vazeci raspored, zato sto ce mo admina na frontu da ogranicimo da mora prvo da stavi prethodni da je ne vazeci, pa onda novi 
		// da napravi
		PriceList savepl = this.pricelistRepo.save(pl);
		//4. sad mozemo da unesemo stavke cenovnika
		if(savepl != null){
			savepl = this.pricelistItemService.addPricelistItems(pricelist.getItems(), savepl);
		}
		return convertToReaderDto(savepl);
	}

	private PricelistReaderDto convertToReaderDto(PriceList savepl) {
		// TODO Auto-generated method stub
		if(savepl == null){
			return null;
		}
		Set<PriceItemDto> itemsDto = this.pricelistItemService.convertToDto(savepl.getItems());
		PricelistReaderDto prdto = new PricelistReaderDto(savepl.getId(), convertToString(savepl.getIssueDate()), convertToString(savepl.getExpirationDate()), savepl.isActivate(), itemsDto);
		return prdto;
	}

	private String convertToString(Date date) {
		// convert date to  string
		if(date == null){
			return "";
		}
		SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("dd-MM-yyyy");
		String date_to_string = dateformatyyyyMMdd.format(date);


		return date_to_string;
	}

	private PriceList convertDtoToPricelist(PricelistDto pricelist) throws ParseException {
		// imam sledece atribute:
		//pricelist : date issuedate, date expirationdate, activate 
		PriceList pl = new PriceList(convertToDate(pricelist.getIssueDate()),null, true);
		
		return pl;
	}

	private Date convertToDate(String stringDate) throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-mm-yyyy");
		java.util.Date date = sdf1.parse(stringDate);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}

	@Override
	public PricelistReaderDto changePricelist(PricelistDto pricelist) {
		// 1. potrazimo da li postoji cenovnik sa poslatim id-em i da li je aktivan 
		PriceList pl =  this.pricelistRepo.findByIdAndActivate(pricelist.getId(), true);
		if(pl == null){
			return null;
		}
		//2. izbrisemo ceo cenovnik 
		//izbrisemo sve stavke ovog cenovnika, a onda i cenovnik
		Set<PriceItem> items = this.pricelistItemService.getItemsByPricelist(pl);
		this.pricelistItemService.deleteItems(items);
		//3. ponovo upisemo sve podatke samo sa novim stavkama 
		//unesli smo cenovnik i sad jos unesemo sve njegove stavke 
		if(pl != null){
			pl = this.pricelistItemService.addPricelistItems(pricelist.getItems(), pl);
		}
		return convertToReaderDto(pl);
	}

	@Override
	public PricelistReaderDto changeActivatePricelist(PricelistDto pricelist) {
		// TODO Auto-generated method stub
		PriceList pl= this.pricelistRepo.findByIdAndActivate(pricelist.getId(), true);
		if(pl == null){
			return null;
		}
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		pl.setExpirationDate(date);
		pl.setActivate(false);
		pl = this.pricelistRepo.save(pl);
		return convertToReaderDto(pl);
	}
	

}
