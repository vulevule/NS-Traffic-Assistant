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
import com.team9.exceptions.WrongDiscountException;
import com.team9.exceptions.WrongNumberOfPriceItemException;
import com.team9.exceptions.WrongTicketTimeException;
import com.team9.exceptions.WrongTrafficTypeException;
import com.team9.exceptions.WrongTrafficZoneException;
import com.team9.exceptions.WrongUserTicketTypeException;
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
	public PriceList addPricelistItems(Set<PriceItemDto> items, PriceList list)
			throws PriceItemAlreadyExistsException, PriceLessThanZeroException, WrongUserTicketTypeException,
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
					throw new PriceLessThanZeroException();
				}
				// proverimo da li su nam popusti izmendju 0 i 100
				checkDiscount(p.getHandycapDiscont(), p.getSeniorDiscount(), p.getStudentDiscount());
				// 2. proverimo da vec ne postoji uneta stavka sa istim
				// parametrima
				Optional<PriceItem> pi = this.repository.findByTrafficTypeAndTimeTypeAndZoneAndPricelist(
						p.getTrafficType(), p.getTimeType(), p.getZone(), list);
				if (pi.isPresent() == true) {
					// znaci da vec postoji u bazi pa bacamo exception
					throw new PriceItemAlreadyExistsException();
				}
				// sad unesemo stavku u cenovnik
				PriceItem saveItem = this.repository.save(p);
				saveItems.add(saveItem);
			}

			list.setItems(saveItems);
			return list;
		}else{
			throw new WrongNumberOfPriceItemException();
		}

	}

	private void checkDiscount(double handycapDiscont, double seniorDiscount, double studentDiscount)
			throws WrongDiscountException {
		// proverimo da li su nam poslati popusti u opsegu izmedju 0 i 100
		if ((handycapDiscont < 0 || handycapDiscont > 100) || (seniorDiscount < 0 || handycapDiscont > 100)
				|| (studentDiscount < 0 || studentDiscount > 100)) {
			// bacimo izuzetak
			throw new WrongDiscountException();
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
	public Set<PriceItemDto> convertToDto(Set<PriceItem> items) {
		Set<PriceItemDto> itemsDto = new HashSet<PriceItemDto>();
		for (PriceItem pi : items) {
			PriceItemDto pdto = new PriceItemDto(pi.getPrice(), pi.getTrafficType().name(), pi.getTimeType().name(),
					pi.getZone().name(), pi.getStudentDiscount(), pi.getHandycapDiscont(), pi.getSeniorDiscount());
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
	public PriceItem getPriceItem(TrafficType type, TimeTicketType time, TrafficZone zone, PriceList p_id)
			throws PriceItemNotFoundException {
		// TODO Auto-generated method stub
		PriceItem foundItem = this.repository.findByTrafficTypeAndTimeTypeAndZoneAndPricelist(type, time, zone, p_id)
				.orElseThrow(() -> new PriceItemNotFoundException());

		return foundItem;
	}

	@Override
	public void deleteItems(Set<PriceItem> items) {
		// TODO Auto-generated method stub

	}

}
