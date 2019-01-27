--adrese
insert into address (id, street, city, zip)values(1, 'Bulevar Vojvode Stepe', 'Beograd', 11000);
insert into address (id, street, city, zip)values(2, 'Vuka Karadzica 5', 'Novi Sad', 21000);
--korisnici
insert into user (id,type,  name, personal_no, username, password, email, role, activate, user_ticket_type, address_id)
values(3, 'Passenger','Pera Peric', '11129956325632', 'peraperic', '$2a$10$nWigsJopzu.0AL5bO.79meRVJGcMhSOv5Yxh476nDL5ZZcIBotv7G', 'pera@gmail.com', 1, true, 0, 1);--pass = 1111

insert into user (id,type,name, personal_no, username, password, email, role, activate, user_ticket_type, address_id)
values(4, 'Passenger', 'Petar Petrovic', '11129956325632', 'petarpetrovic', '$2a$10$jcTXnU3oS2SzxxgXCcTpd.QW0ZzRiDPNE74/kT6dZMDhYkKahQJ5S', 'petar@gmail.com', 1, true, 1, 1); --pass = 2222
insert into user (id,type, name, personal_no, username, password, email, role, activate, user_ticket_type, address_id) --pass = 3333
values(5,'Passenger',  'Petra Peric', '11129956325632', 'petraperic', '$2a$10$.t83Y.qOskliQZZdWhcJvOn9ogqC.H1lvCCFtslWKqmWTQBfKW2eq', 'petra@gmail.com', 1, true, 2, 1);
insert into user (id, type, name, personal_no, username, password, email, role, activate, user_ticket_type, address_id)--pass = 4444
values(6,'Passenger', 'Pavle Peric', '11129956325632', 'pavleperic', '$2a$10$Xdj3Oezo6maXaUyf.YzI8eiGh93TqqfwgUolPo0WqOSwsgRAUvQme', 'pavle@gmail.com', 1, true, 3, 2);
insert into user (id, type, name, personal_no, username, password, email, role, activate, user_ticket_type, address_id)--pass = 5555
values(7,'Passenger',  'Mika Peric', '11129956325632', 'mikaperic', '$2a$10$ifDULT1TOVUfuGxWOnSiAO8p2Rgw6yBuyytYVn39u3hqRkILAMF9K', 'mika@gmail.com', 1, false, 0, 2);

insert into user (id, type, name, personal_no, username, password, email, role, address_id ) --pass = 6666
values(50, 'Inspector', 'Lena Lukic', '451278965533', 'lenalukic', '$2a$10$z0EIf/A8qb/WzxwNKR94Ne31T0rP6IVwDcJjv4Fp4ir6MiT9vQF0m', 'lena@gmail.com', 2 , 1);
insert into user (id, type, name, personal_no, username, password, email, role, address_id ) -- pass = 7777
values(51, 'Admin', 'Lara Lukic', '4512789655335', 'laralukic', '$2a$10$tFHPAi/oFW99K/5lTfw29.YKf6r6UYmFZTSzCIrMOHhtLDZKghGM6', 'lara@gmail.com', 0 , 1);
--authority
insert into authority (id, name, user_id) values (1, 'ADMIN', 51);
insert into authority (id, name,user_id) values (2, 'INSPECTOR', 50);
insert into authority (id, name,user_id) values (3, 'PASSENGER', 3);
insert into authority (id, name,user_id) values (4, 'PASSENGER', 4);
insert into authority (id, name,user_id) values (5, 'PASSENGER', 5);
insert into authority (id, name,user_id) values (6, 'PASSENGER', 6);
insert into authority (id, name,user_id) values (7, 'PASSENGER', 7);

--karte
insert into ticket (id, serial_no, issue_date, expiration_date, user_type, time_type, 
traffic_zone, active, price,traffic_type, used, passenger_id ) 
values (1, 'BMFS12121212000', '2019-01-05', '2019-02-05', 0, 1, 0, true, 900, 0, false, 3);

insert into ticket (id, serial_no, issue_date, expiration_date, user_type, time_type, 
traffic_zone, active, price,traffic_type, used, passenger_id ) 
values (2, 'BMFS12121212001', '2019-01-15', '2019-02-15', 1, 1, 0, true, 950, 0, false, 4);

-- karta koja nije koriscena, single karta
insert into ticket (id, serial_no, issue_date, expiration_date, user_type, time_type, 
traffic_zone, active, price,traffic_type, used, passenger_id ) 
values (3, 'BMFS12121212023', '2019-01-18', '2019-02-02', 2, 3, 0, true, 95, 0,false, 6);
-- karta kojoj je isteklo vreme trajanja, dnevna karta
insert into ticket (id, serial_no, issue_date, expiration_date, user_type, time_type, 
traffic_zone, active, price,traffic_type, used, passenger_id ) 
values (4, 'BMFS12121212025', '2019-01-01', '2019-01-02', 2, 2, 0, true, 95, 0,false, 6);
-- karta koje je koriscena, single karta
insert into ticket (id, serial_no, issue_date, expiration_date, user_type, time_type, 
traffic_zone, active, price,traffic_type, used, passenger_id ) 
values (5, 'BMFS12121212024', '2019-01-18', '2019-02-02', 2, 3, 0, true, 95, 0,true, 6);
--karta iz druge zone

insert into ticket (id, serial_no, issue_date, expiration_date, user_type, time_type, 
traffic_zone, active, price,traffic_type, used, passenger_id ) 
values (6, 'BMFS12121212003', '2019-01-05', '2019-02-05', 1, 1, 1, true, 950, 0, false, 4);
--cenovnik
insert into price_list(id,issue_date, activate) values (10, '2018-12-25', true);

--stavke cenovnika
--bus
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
				values (11, 10000, 0,0,0, 10, 5, 5,10 );
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount,pricelist_id)
				values (12, 10000, 0,0,1, 10, 5, 5, 10 );
				
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
				values (13, 1000, 0,1,0, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (14, 1000, 0,1,1, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (15, 100, 0,2,0, 10, 5, 5,10 );
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (16, 100, 0,2,1, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (17, 100, 0,3,0, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (18, 100, 0,3,1, 10, 5, 5,10 );
--metro
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (19, 10000, 1,0,0, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (20, 10000, 1,0,1, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (21, 1000, 1,1,0, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (22, 1000, 1,1,1, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (23, 100, 1,2,0, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (24, 100, 1,2,1, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (25, 100, 1,3,0, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (26, 100, 1,3,1, 10, 5, 5 ,10);
--tram
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (27, 10000, 2,0,0, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (28, 10000, 2,0,1, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (29, 1000, 2,1,0, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (30, 1000, 2,1,1, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (31, 100, 2,2,0, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (32, 100, 2,2,1, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (33, 100, 2,3,0, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (34, 100, 2,3,1, 10, 5, 5 ,10);

--stanice

insert into station(id, name, type, x_coordinate, y_coordinate)
values(1, 'Bazar', 0, 19.830287933873482, 45.26408747364272);
insert into station(id, name, type, x_coordinate, y_coordinate)
values(2, 'Bazar', 2, 19.83210754551692, 45.26066810367371); --tram
insert into station(id, name, type, x_coordinate, y_coordinate)
values(3, 'Bazar-Podhodnik', 1, 19.835214616439767, 45.255242602344424);
insert into station(id, name, type, x_coordinate, y_coordinate)
values(4, 'Narodnog Fronta', 0, 19.837051391077697, 45.25194354772586);
insert into station(id, name, type, x_coordinate, y_coordinate)
values(5, 'Zeleznicka', 0, 19.839197158813473, 45.24794333819497);
insert into station(id, name, type, x_coordinate, y_coordinate)
values(6, 'Balzakova', 1, 19.841394424962346, 45.243882414390214);
insert into station(id, name, type, x_coordinate, y_coordinate)
values(7, 'Bulevar Jase Tomica', 2, 19.823949634301243, 45.26394299011491); --tram

--linije
insert into line (id, mark, name, type, zone)
values(1, '1A', 'ZELEZNICKA - FUTOSKA PIJACA - ZELZENICKA', 0, 0);

insert into line (id, mark, name, type, zone)
values(2, '1T', 'BULEVAR J.T. - BAZAR - BULEVAR J.T.', 2, 0);

--station-line
insert into station_line(id, arrival, station_num, line_id, station_id)--line 1
values (1, 0, 1, 1, 1);
insert into station_line(id, arrival, station_num, line_id, station_id)
values (2, 5, 2, 1, 4);
insert into station_line(id, arrival, station_num, line_id, station_id)
values (3, 10, 3, 1, 6);
--line 2
insert into station_line(id, arrival, station_num, line_id, station_id)
values (4, 0, 1, 2, 7);
insert into station_line(id, arrival, station_num, line_id, station_id)
values (5, 10, 2, 2, 2);

--location
--1. linija
insert into location(id, lon, lat)
values (1, 19.843186438956764, 45.24886502908012 );

insert into location(id, lon, lat)
values (2,  19.839238225977166, 45.2479921840457 );
insert into location(id, lon, lat)
values (3, 19.837025702145183,45.25202058846409 );
insert into location(id, lon, lat)
values (4,19.830292760743752,  45.26413093570861 );
insert into location(id, lon, lat)
values (5,19.829472600686127, 45.264761892239505  );

--2. linija
 insert into location(id, lon, lat)
values (6, 19.8238649959967,  45.26392956524123 );
insert into location(id, lon, lat)
values (7, 19.82764154759934,  45.263701344924385 );
insert into location(id, lon, lat)
values (8, 19.830273687839508,  45.2641040866103 );
insert into location(id, lon, lat)
values (9, 19.832181037054397,  45.26064041372618 );
insert into location(id, lon, lat)
values (10,19.828614293655846,  45.25963349203437 ) ;
insert into location(id, lon, lat)
values (11,  19.823102056834614,  45.259015903418685 );
insert into location(id, lon, lat)
values (12, 19.818581640138287,  45.26282872744534 );
insert into location(id, lon, lat)
values (13, 19.820794165279946,  45.26388929121569 );
insert into location(id, lon, lat)
values (14, 19.822053014504487,  45.264010113667524 );
insert into location(id, lon, lat)
values (15,19.823903142823834,  45.2639564150366 ) ;

--rute
-- prva
insert into line_route (line_id, route_id)
values (1, 1);
insert into line_route ( line_id, route_id)
values ( 1, 2);
insert into line_route ( line_id, route_id)
values ( 1, 3);
insert into line_route (line_id, route_id)
values (1, 4);
insert into line_route ( line_id, route_id)
values (1, 5);
--druga
insert into line_route ( line_id, route_id)
values ( 2, 6);
insert into line_route (line_id, route_id)
values ( 2, 7);
insert into line_route (line_id, route_id)
values ( 2, 8);
insert into line_route (line_id, route_id)
values ( 2, 9);
insert into line_route ( line_id, route_id)
values ( 2, 10);
insert into line_route ( line_id, route_id)
values (2, 12);
insert into line_route ( line_id, route_id)
values (2, 13);
insert into line_route ( line_id, route_id)
values ( 2, 14);
insert into line_route ( line_id, route_id)
values ( 2, 15);


--red voznje
insert into timetable (id, issue_date, activate)
values (1, '2019-01-03', true);

--stavke reda voznje

insert into timetable_item (id, start_time, type, line_id, timetable_id)
values(1, "10:30", 0, 1, 1);
insert into timetable_item (id, start_time, type, line_id, timetable_id)
values(2, "11:30", 0, 1, 1);
insert into timetable_item (id, start_time, type, line_id, timetable_id)
values(3, "12:30", 0, 1, 1);
insert into timetable_item (id, start_time, type, line_id, timetable_id)
values(4, "13:30", 0, 1, 1);
insert into timetable_item (id, start_time, type, line_id, timetable_id)
values(5, "14:30", 0, 1, 1);

insert into timetable_item (id, start_time, type, line_id, timetable_id)
values(6, "10:30", 1, 1, 1);
insert into timetable_item (id, start_time, type, line_id, timetable_id)
values(7, "12:30", 1, 1, 1);
insert into timetable_item (id, start_time, type, line_id, timetable_id)
values(8, "13:30", 1, 1, 1);

insert into timetable_item (id, start_time, type, line_id, timetable_id)
values(9, "15:30", 1, 1, 1);

insert into timetable_item (id, start_time, type, line_id, timetable_id)
values(10, "10:30", 2, 1, 1);
insert into timetable_item (id, start_time, type, line_id, timetable_id)
values(11, "11:30", 2, 1, 1);
insert into timetable_item (id, start_time, type, line_id, timetable_id)
values(12, "12:30", 2, 1, 1);
insert into timetable_item (id, start_time, type, line_id, timetable_id)
values(13, "13:30", 2, 1, 1);
insert into timetable_item (id, start_time, type, line_id, timetable_id)
values(14, "14:30", 2, 1, 1);

