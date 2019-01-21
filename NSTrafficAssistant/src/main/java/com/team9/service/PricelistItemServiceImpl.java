package com.team9.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team9.dto.PriceItemDto;
import com.team9.exceptions.PriceItemAlreadyExistsException;
import com.team9.exceptions.PriceItemNotFoundException;
import com.team9.exceptions.PriceLessThanZeroException;
import com.team9.exceptions.WrongDiscountException;
import com.team9.exceptions.WrongNumberOfPriceItemException;
import com.team9.exceptions.WrongTicketTimeException;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.model.PriceItem;
import com.team9.model.PriceList;
import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;
import com.team9.repository.PriceItemRepository;

@Service
public class PricelistItemServiceImpl implements PricelistItemService {

	@Autowired
	private PriceItemRepository repository;

	@Override
	public PriceList addPricelistItems(Set<PriceItemDto> items, PriceList list)
			throws PriceItemAlreadyExistsException, PriceLessThanZeroException,
			WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException, WrongDiscountException, WrongNumberOfPriceItemException {
		// TODO Auto-generated method stub
		// 1. konvertujemo sve item-e u priceitem
		Set<PriceItem> priceItems = convertDtoToPriceItem(items, list);
		// 2.radimo proveru svake stavke,a zatim vrsimo i upis u bazu
		Set<PriceItem> saveItems = new HashSet<>();
		if (priceItems.size() == 24) {
			for (PriceItem p : priceItems) {
				// 1. proverimo da li je cena veca od 0
				if (p.getPrice() <= 0) {
					throw new PriceLessThanZeroException("The price of the item is less than 0!");
				}
				// proverimo da li su nam popusti izmendju 0 i 100
				checkDiscount(p.getHandycapDiscount(), p.getSeniorDiscount(), p.getStudentDiscount());
				// 2. proverimo da vec ne postoji uneta stavka sa istim
				// parametrima
				Optional<PriceItem> pi = this.repository.findByTrafficTypeAndTimeTypeAndZoneAndPricelist(
						p.getTrafficType(), p.getTimeType(), p.getZone(), list);
				if (pi.isPresent() == true) {
					// znaci da vec postoji u bazi pa bacamo exception
					throw new PriceItemAlreadyExistsException("The price list must not contain the same items ");
				}
				// sad unesemo stavku u cenovnik
				PriceItem saveItem = this.repository.save(p);
				saveItems.add(saveItem);
			}

			list.setItems(saveItems);
			return list;
		}else{
			throw new WrongNumberOfPriceItemException("The price list has more than or equal to 24 items!");
		}

	}

	private void checkDiscount(double handycapDiscont, double seniorDiscount, double studentDiscount)
			throws WrongDiscountException {
		// proverimo da li su nam poslati popusti u opsegu izmedju 0 i 100
		if ((handycapDiscont < 0 || handycapDiscont > 100) || (seniorDiscount < 0 || handycapDiscont > 100)
				|| (studentDiscount < 0 || studentDiscount > 100)) {
			// bacimo izuzetak
			throw new WrongDiscountException("The discount must be between 0 and 100");
		}

	}

	private Set<PriceItem> convertDtoToPriceItem(Set<PriceItemDto> items, PriceList list)
			throws WrongTrafficTypeException, WrongTicketTimeException, WrongTrafficZoneException {
		// TODO Auto-generated method stub
		Set<PriceItem> priceItems = new HashSet<PriceItem>();
		for (PriceItemDto pi : items) {
			// konvertujemo tipove
			PriceItem p_item = new PriceItem(pi.getPrice(),
					ConverterService.convertStringToTrafficType(pi.getTrafficType()),
					ConverterService.convertStringToTimeTicketType(pi.getTimeType()),
					ConverterService.convertStringToZone(pi.getZone()), pi.getStudentDiscount(), pi.getSeniorDiscount(),
					pi.getHandycapDiscount(), list);
			priceItems.add(p_item);
		}
		return priceItems;
	}

	@Override
	public List<PriceItemDto> convertToDto(Set<PriceItem> items) {
		//stavicemo da kada nam vraca stavke, ne vraca procente , nego stvarne cene
		List<PriceItemDto> itemsDto = new ArrayList<PriceItemDto>();
		for (PriceItem pi : items) {
			PriceItemDto pdto = new PriceItemDto(pi.getPrice(), pi.getTrafficType().name(), pi.getTimeType().name(),
					pi.getZone().name(),calculateTicketPrice(pi.getPrice(), pi.getStudentDiscount()),calculateTicketPrice(pi.getPrice(), pi.getHandycapDiscount()),calculateTicketPrice(pi.getPrice(), pi.getSeniorDiscount()));
			itemsDto.add(pdto);
		}
		return itemsDto;

	}
	
	@Override
	public double calculateTicketPrice(double price, double discount){
		return (price - ((discount/100)*price));
	}



	@Override
	public PriceItem getPriceItem(TrafficType type, TimeTicketType time, TrafficZone zone, PriceList p_id)
			throws PriceItemNotFoundException {
		// TODO Auto-generated method stub
		PriceItem foundItem = this.repository.findByTrafficTypeAndTimeTypeAndZoneAndPricelist(type, time, zone, p_id)
				.orElseThrow(() -> new PriceItemNotFoundException("Not item found for traffic type: " + type + ", ticket time: " + time + " and zone: " + zone));

		return foundItem;
	}

	@Override
	public Set<PriceItem> getPriceItemsByPricelist(PriceList savepl) {
		// TODO Auto-generated method stub
		return this.repository.findByPricelist(savepl);
	}


}
