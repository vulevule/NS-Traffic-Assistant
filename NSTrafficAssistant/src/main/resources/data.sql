--adrese
insert into address (id, street, city, zip)values(1, 'Bulevar Vojvode Stepe', 'Beograd', 11000);
insert into address (id, street, city, zip)values(2, 'Vuka Karadzica 5', 'Novi Sad', 21000);
--korisnici
insert into user (id,type,  name, personal_no, username, password, email, role, activate, user_ticket_type, address_id)
values(3, 'Passenger','Pera Peric', '11129956325632', 'peraperic', '1111', 'pera@gmail.com', 1, true, 0, 1);	
insert into user (id,type,name, personal_no, username, password, email, role, activate, user_ticket_type, address_id)
values(4, 'Passenger', 'Petar Petrovic', '11129956325632', 'petarpetrovic', '2222', 'petar@gmail.com', 1, true, 1, 1);
insert into user (id,type, name, personal_no, username, password, email, role, activate, user_ticket_type, address_id)
values(5,'Passenger',  'Petra Peric', '11129956325632', 'petraperic', '3333', 'petra@gmail.com', 1, true, 2, 1);
insert into user (id, type, name, personal_no, username, password, email, role, activate, user_ticket_type, address_id)
values(6,'Passenger', 'Pavle Peric', '11129956325632', 'pavleperic', '4444', 'pavle@gmail.com', 1, true, 3, 2);
insert into user (id, type, name, personal_no, username, password, email, role, activate, user_ticket_type, address_id)
values(7,'Passenger',  'Mika Peric', '11129956325632', 'mikaperic', '5555', 'mika@gmail.com', 1, false, 0, 2);


--karte
insert into ticket (id, serial_no, issue_date, expiration_date, user_type, time_type, 
traffic_zone, active, price,traffic_type, used, passenger_id ) 
values (8, 'BMFS12121212000', '2018-12-27', '2019-01-27', 0, 1, 0, true, 900, 0, false, 3);

insert into ticket (id, serial_no, issue_date, expiration_date, user_type, time_type, 
traffic_zone, active, price,traffic_type, used, passenger_id ) 
values (9, 'BMFS12121212000', '2018-12-27', '2019-01-27', 1, 1, 0, true, 950, 0, false, 4);

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
values (27, 10000, 3,0,0, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (28, 10000, 3,0,1, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (29, 1000, 3,1,0, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (30, 1000, 3,1,1, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (31, 100, 3,2,0, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (32, 100, 3,2,1, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (33, 100, 3,3,0, 10, 5, 5 ,10);
insert into price_item(id, price, traffic_type, time_type, zone, student_discount, senior_discount, handycap_discount, pricelist_id)
values (34, 100, 3,3,1, 10, 5, 5 ,10);
