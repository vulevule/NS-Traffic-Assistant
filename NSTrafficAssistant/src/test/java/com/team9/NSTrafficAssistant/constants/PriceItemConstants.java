package com.team9.NSTrafficAssistant.constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.team9.dto.PriceItemDto;
import com.team9.dto.PricelistDto;
import com.team9.model.PriceItem;
import com.team9.model.PriceList;
import com.team9.model.TimeTicketType;
import com.team9.model.TrafficType;
import com.team9.model.TrafficZone;

public class PriceItemConstants {
	
	//ovo je za testiranje pricelistItemServisa
	public static final PriceItemDto pi1 = new PriceItemDto(1000, "bus", "month", "first", 10, 5, 5);
	public static final PriceItemDto pi2 = new PriceItemDto(1000, "bus", "month", "second", 10, 5, 5);
	public static final PriceItemDto pi3 = new PriceItemDto(10000, "bus", "annual", "first", 10, 5, 5);
	public static final PriceItemDto pi4 = new PriceItemDto(10000, "bus", "annual", "second", 10, 5, 5);
	public static final PriceItemDto pi5 = new PriceItemDto(120, "bus", "daily", "first", 10, 5, 5);
	public static final PriceItemDto pi6 = new PriceItemDto(100, "bus", "daily", "second", 10, 5, 5);
	public static final PriceItemDto pi7 = new PriceItemDto(100, "bus", "single", "first", 10, 5, 5);
	public static final PriceItemDto pi8 = new PriceItemDto(100, "bus", "single", "second", 10, 5, 5);
	public static final PriceItemDto pi9 = new PriceItemDto(1000, "tram", "month", "first", 10, 5, 5);
	public static final PriceItemDto pi10 = new PriceItemDto(1000, "tram", "month", "second", 10, 5, 5);
	public static final PriceItemDto pi11 = new PriceItemDto(10000, "tram", "annual", "first", 10, 5, 5);
	public static final PriceItemDto pi12 = new PriceItemDto(10000, "tram", "annual", "second", 10, 5, 5);
	public static final PriceItemDto pi13 = new PriceItemDto(120, "tram", "daily", "first", 10, 5, 5);
	public static final PriceItemDto pi14 = new PriceItemDto(100, "tram", "daily", "second", 10, 5, 5);
	public static final PriceItemDto pi15 = new PriceItemDto(100, "tram", "single", "first", 10, 5, 5);
	public static final PriceItemDto pi16 = new PriceItemDto(100, "tram", "single", "second", 10, 5, 5);
	public static final PriceItemDto pi17 = new PriceItemDto(1000, "metro", "month", "first", 10, 5, 5);
	public static final PriceItemDto pi18 = new PriceItemDto(1000, "metro", "month", "second", 10, 5, 5);
	public static final PriceItemDto pi19 = new PriceItemDto(10000, "metro", "annual", "first", 10, 5, 5);
	public static final PriceItemDto pi20 = new PriceItemDto(10000, "metro", "annual", "second", 10, 5, 5);
	public static final PriceItemDto pi21 = new PriceItemDto(120, "metro", "daily", "first", 10, 5, 5);
	public static final PriceItemDto pi22 = new PriceItemDto(100, "metro", "daily", "second", 10, 5, 5);
	public static final PriceItemDto pi23 = new PriceItemDto(100, "metro", "single", "first", 10, 5, 5);
	public static final PriceItemDto pi24 = new PriceItemDto(12, "metro", "single", "second", 10, 5, 5);

	
	public static final PriceItemDto[] it_normal = new PriceItemDto[] {pi1, pi2, pi3, pi4, pi5, pi6, pi7, pi8, pi9,
			pi10, pi11, pi12, pi13, pi14, pi15, pi16, pi17, pi18, pi19, pi20, pi21, pi22, pi23, pi24};
	
	
	public static final Set<PriceItemDto> items_normal= new HashSet<PriceItemDto>(Arrays.asList(it_normal));
	
	
	public static final PriceItemDto pi_twice = new PriceItemDto(10000, "metro", "annual", "second", 10, 5, 5);

	public static final PriceItemDto[] it_twice = new PriceItemDto[] {pi1, pi2, pi3, pi4, pi5, pi6, pi7, pi8, pi9,
			pi10, pi11, pi12, pi13, pi14, pi15, pi16, pi17, pi18, pi19, pi20, pi21, pi22, pi23, pi_twice};
	
	
	public static final Set<PriceItemDto> items_twice= new HashSet<PriceItemDto>(Arrays.asList(it_twice));
	
	
	public static final PriceItemDto pi24_price_less0 = new PriceItemDto(-12, "metro", "single", "second", 10, 5, 5);
	
	public static final PriceItemDto[] it_price_less0 = new PriceItemDto[] {pi1, pi2, pi3, pi4, pi5, pi6, pi7, pi8, pi9,
			pi10, pi11, pi12, pi13, pi14, pi15, pi16, pi17, pi18, pi19, pi20, pi21, pi22, pi23, pi24_price_less0};
	
	
	public static final Set<PriceItemDto> items_price_less0 = new HashSet<PriceItemDto>(Arrays.asList(it_price_less0));
	
	public static final PriceItemDto pi24_wrongTrafficType = new PriceItemDto(1202, "plain", "single", "second", 10, 5, 5);

	public static final PriceItemDto[] it_wrong_trafficType = new PriceItemDto[] {pi1, pi2, pi3, pi4, pi5, pi6, pi7, pi8, pi9,
			pi10, pi11, pi12, pi13, pi14, pi15, pi16, pi17, pi18, pi19, pi20, pi21, pi22, pi23, pi24_wrongTrafficType};
	public static final Set<PriceItemDto> items_wrong_trafficType = new HashSet<PriceItemDto>(Arrays.asList(it_wrong_trafficType));

	
	public static final PriceItemDto pi24_wrongTimeTicket = new PriceItemDto(12, "metro", "weekly", "second", 10, 5, 5);

	public static final PriceItemDto[] it_wrong_timeTicket = new PriceItemDto[] {pi1, pi2, pi3, pi4, pi5, pi6, pi7, pi8, pi9,
			pi10, pi11, pi12, pi13, pi14, pi15, pi16, pi17, pi18, pi19, pi20, pi21, pi22, pi23, pi24_wrongTimeTicket};
	public static final Set<PriceItemDto> items_wrong_timeTicket = new HashSet<PriceItemDto>(Arrays.asList(it_wrong_timeTicket));

	
	public static final PriceItemDto pi24_wrongzone = new PriceItemDto(120, "metro", "single", "fourth", 10, 5, 5);

	public static final PriceItemDto[] it_wrong_zone= new PriceItemDto[] {pi1, pi2, pi3, pi4, pi5, pi6, pi7, pi8, pi9,
			pi10, pi11, pi12, pi13, pi14, pi15, pi16, pi17, pi18, pi19, pi20, pi21, pi22, pi23, pi24_wrongzone};
	public static final Set<PriceItemDto> items_wrong_zone = new HashSet<PriceItemDto>(Arrays.asList(it_wrong_zone));

	
	public static final PriceItemDto pi24_wrongDiscount = new PriceItemDto(1202, "metro", "single", "second", -1, 5, 5);

	public static final PriceItemDto[] it_wrong_discount = new PriceItemDto[] {pi1, pi2, pi3, pi4, pi5, pi6, pi7, pi8, pi9,
			pi10, pi11, pi12, pi13, pi14, pi15, pi16, pi17, pi18, pi19, pi20, pi21, pi22, pi23, pi24_wrongDiscount};
	public static final Set<PriceItemDto> items_wrong_discount = new HashSet<PriceItemDto>(Arrays.asList(it_wrong_discount));

	
	public static final PriceItemDto pi24_wrongDiscount1 = new PriceItemDto(120, "metro", "single", "second", 105, 5, 5);

	public static final PriceItemDto[] it_wrong_discount1 = new PriceItemDto[] {pi1, pi2, pi3, pi4, pi5, pi6, pi7, pi8, pi9,
			pi10, pi11, pi12, pi13, pi14, pi15, pi16, pi17, pi18, pi19, pi20, pi21, pi22, pi23, pi24_wrongDiscount1};
	public static final Set<PriceItemDto> items_wrong_discount1 = new HashSet<PriceItemDto>(Arrays.asList(it_wrong_discount1));
	
	public static final PriceItemDto[] it_23 = new PriceItemDto[] {pi1, pi2, pi3, pi4, pi5, pi6, pi7, pi8, pi9,
			pi10, pi11, pi12, pi13, pi14, pi15, pi16, pi17, pi18, pi19, pi20, pi21, pi22, pi23};
	public static final Set<PriceItemDto> items_wrong_number = new HashSet<PriceItemDto>(Arrays.asList(it_23));
	

	
	public static final PriceList p  = new PriceList(100L, new java.sql.Date(new java.util.Date().getTime()), null, true);

	public static final PriceItem i = new PriceItem(1L, 100, TrafficType.BUS, TimeTicketType.MONTH, TrafficZone.FIRST, 10, 5, 5, p);

	
}
