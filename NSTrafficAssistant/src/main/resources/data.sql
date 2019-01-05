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
values (8, 'BMFS12121212000', '2018-12-27', '2019-01-27', 0, 1, 0, true, 900, 0, false, 3);

insert into ticket (id, serial_no, issue_date, expiration_date, user_type, time_type, 
traffic_zone, active, price,traffic_type, used, passenger_id ) 
values (9, 'BMFS12121212001', '2018-12-27', '2019-01-27', 1, 1, 0, true, 950, 0, false, 4);

-- karta koja nije koriscena, single karta
insert into ticket (id, serial_no, issue_date, expiration_date, user_type, time_type, 
traffic_zone, active, price,traffic_type, used, passenger_id ) 
values (55, 'BMFS12121212023', '2019-01-04', '2019-01-19', 2, 3, 0, true, 95, 0,false, 6);
-- karta kojoj je isteklo vreme trajanja, dnevna karta
insert into ticket (id, serial_no, issue_date, expiration_date, user_type, time_type, 
traffic_zone, active, price,traffic_type, used, passenger_id ) 
values (56, 'BMFS12121212025', '2019-01-01', '2019-01-02', 2, 2, 0, true, 95, 0,false, 6);
-- karta koje je koriscena, single karta
insert into ticket (id, serial_no, issue_date, expiration_date, user_type, time_type, 
traffic_zone, active, price,traffic_type, used, passenger_id ) 
values (57, 'BMFS12121212024', '2019-01-03', '2019-01-18', 2, 3, 0, true, 95, 0,true, 6);
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
