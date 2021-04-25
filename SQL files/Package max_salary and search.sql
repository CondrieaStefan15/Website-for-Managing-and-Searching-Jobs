create or replace package max_salary_from_county
is
	type p_list is ref cursor return meserii%rowtype;
	
	procedure intoarcemMeseriaCeaMaiPlatita(variabilaCursor Out p_list, salariulMaxim in NUMBER,  p_judet in VARCHAR);
end max_salary_from_county;
/


Create or replace PACKAGE BODY max_salary_from_county
is
	procedure intoarcemMeseriaCeaMaiPlatita(variabilaCursor Out p_list, salariulMaxim in NUMBER, p_judet in VARCHAR)
	is
	begin
	open variabilaCursor for select * from meserii where salariu = salariulMaxim AND meserii_locatie=concat(concat('',p_judet),'');
	end intoarcemMeseriaCeaMaiPlatita;
end max_salary_from_county;
/




create or replace package searchInParticularitati
is
	type p_list is ref cursor return meserii%rowtype;
	
	procedure intoarcemMeseriileDupaCheie(variabilaCursor Out p_list, cheia in VARCHAR);
end searchInParticularitati;
/


Create or replace PACKAGE BODY searchInParticularitati
is
	procedure intoarcemMeseriileDupaCheie(variabilaCursor Out p_list, cheia in VARCHAR)
	is
	begin
	open variabilaCursor for select * from meserii where particularitati like concat(concat('%',cheia),'%');
	end intoarcemMeseriileDupaCheie;
end searchInParticularitati;
/
	
	
	

	
	