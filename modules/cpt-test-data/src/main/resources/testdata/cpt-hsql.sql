/*
Execute HSQL specific DB scripts here. We don't need below sequence but I added it only to test the code
*/
create table dual (dummy varchar(1) );
insert into dual (dummy) values ('X');

DROP sequence XXXXX_KEY_SEQ IF EXISTS;
create sequence XXXXX_KEY_SEQ start with 1 increment by 1;
