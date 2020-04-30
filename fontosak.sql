use camp_organizer_db;

select * from Camp;
select * from Person;
select * from Parent;
select * from Camp_campers;

UPDATE INTO Parent (id, email, job, name, phone)
   VALUES
   ( 1, 'szuloteszt@gmail.com', 'fogorvos', 'Nagy Éva',  '0670/1234567');
   
   UPDATE Parent SET email = 'szuloteszt@gmail.com' where id=4;

select email from parent where id in
	(select parent_id from person where id in
		(select campers_id from camp_campers where camps_from_date='2020-08-11'));
           

select campers_id from camp_campers where camps_from_date='2020-08-11';
            
select email from parent where id in
	(select parent_id from person where rating='PLUTO' and id in
		(select campers_id from camp_campers where camps_from_date in
			(select from_date from camp where is_active=1)));


drop table address;
drop table camp_campers;
drop table camp;
drop table person;
drop table parent;