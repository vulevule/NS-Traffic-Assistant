package com.team9.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team9.dto.PriceItemDto;
import com.team9.exceptions.PriceItemAlreadyExistsException;
import com.team9.exceptions.PriceItemNotFoundException;
import com.team9.exceptions.PriceLessThanZeroException;
import com.team9.model.PriceItem;
import com.team9.model.PriceList;
import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.model.UserTicketType;
import com.team9.repository.PriceItemRepository;

@Service
public class PricelistItemServiceImpl implements PricelistItemService {

	@Autowired
	private PriceItemRepository repository;
	
	@Override
	public PriceList addPricelistItems(Set<PriceItemDto> items, PriceList list) throws  PriceItemAlreadyExistsException, PriceLessThanZeroException {
		// TODO Auto-generated method stub
		//1. konvertujemo sve item-e u priceitem
		Set<PriceItem> priceItems = convertDtoToPriceItem(items, list);
		//2.radimo proveru svake stavke,a zatim  vrsimo i upis u bazu
		Set<PriceItem> saveItems = new HashSet<>();
		for(PriceItem p : priceItems){
			//1. proverimo da li je cena veca od 0 
			if(p.getPrice() <= 0){
				throw new PriceLessThanZeroException();
			}
			//proverimo da li su nam dobri svi ostali parametri kao sto su zona, vremenske odrednice, vrsta prevoza i vrsta karte
			checkArguments(p);
			//2. proverimo da vec ne postoji uneta stavka sa istim parametrima 
			Optional<PriceItem> pi =  this.repository.findByUserTypeAndTrafficTypeAndTimeTypeAndZoneAndPricelist(p.getUserType(),p.getTrafficType(),p.getTimeType(),p.getZone(), list);
			if(pi.isPresent() == true){
				//znaci da vec postoji u bazi pa bacamo exception
				throw new PriceItemAlreadyExistsException();
			}
			//sad unesemo stavku u cenovnik
			PriceItem saveItem = this.repository.save(p);
			saveItems.add(saveItem);
		}
		
		list.setItems(saveItems);
		return list;
		
	}

	private void checkArguments(PriceItem p) {
		// provera parametara
		
	}

	private Set<PriceItem> convertDtoToPriceItem(Set<PriceItemDto> items, PriceList list) {
		// TODO Auto-generated method stub
		Set<PriceItem> priceItems = new HashSet<PriceItem>();
		for(PriceItemDto pi : items){
			PriceItem p_item =  new PriceItem(pi.getPrice(), pi.getUserType(), pi.getTrafficType(), pi.getTimeType(), list, pi.getZone());
			priceItems.add(p_item);
		}
		return priceItems;
	}
	
	@Override
	public Set<PriceItemDto> convertToDto(Set<PriceItem> items){
		Set<PriceItemDto> itemsDto = new HashSet<PriceItemDto>();
		for(PriceItem pi : items){
			PriceItemDto pdto = new PriceItemDto(pi.getPrice(), pi.getUserType(), pi.getTrafficType(), pi.getTimeType(), pi.getZone());
			itemsDto.add(pdto);
		}
		return itemsDto;
		
	}


	@Override
	public Set<PriceItem> getItemsByPricelist(PriceList pl) {
		// TODO Auto-generated method stub
		return this.repository.findByPricelist(pl);
	}

	@Override
	public void deleteItems(Set<PriceItem> items) {
		// TODO Auto-generated method stub
		for(PriceItem p : items){
			this.repository.delete(p);
		}
		
	}

	@Override
	public PriceItem getPriceItem(UserTicketType ut, TrafficType type, TimeTicketType time, TrafficZone zone,
			PriceList p_id) throws PriceItemNotFoundException {
		// TODO Auto-generated method stub
		PriceItem foundItem = this.repository.findByUserTypeAndTrafficTypeAndTimeTypeAndZoneAndPricelist(ut, type, time, zone, p_id).orElseThrow(() -> new PriceItemNotFoundException());
		
		
		
		return foundItem;
	}

}
