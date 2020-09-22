insert into ROLE (id, name, active) values (1, 'ADMIN', true), (2, 'USER', true);

insert into PROFILE (id, name, active) values (1, 'GERENTE', true), (2, 'SUPERVISOR', true);

insert into USER (id, name, cpf, password, gender, birth_date, profile_id, role_id, active) values
	 (1, 'Thiago Tavares Gregorio', '35296261801', '123123123', 'M', '1987-08-18', 1, 1, true);