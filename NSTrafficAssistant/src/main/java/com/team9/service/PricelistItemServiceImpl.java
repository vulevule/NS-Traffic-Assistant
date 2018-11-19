package com.team9.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team9.dto.PriceItemDto;
import com.team9.model.PriceItem;
import com.team9.model.PriceList;
import com.team9.repository.PriceItemRepository;

@Service
public class PricelistItemServiceImpl implements PricelistItemService {

	@Autowired
	private PriceItemRepository repository;
	
	@Override
	public PriceList addPricelistItems(Set<PriceItemDto> items, PriceList list) {
		// TODO Auto-generated method stub
		//1. konvertujemo sve item-e u priceitem
		Set<PriceItem> priceItems = convertDtoToPriceItem(items, list);
		//2.sacuvamo ih sve u bazi
		for(PriceItem p : priceItems){
			this.repository.save(p);
		}
		list.setItems(priceItems);
		return list;
		
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

}
