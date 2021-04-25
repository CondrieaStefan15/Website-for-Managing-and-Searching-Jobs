set serveroutput on


create or replace procedure insert_n_records(numberElements in NUMBER)
is
  v_records NUMBER :=0; --get number of records from domenii table
  flag NUMBER :=0;
begin
  SELECT COUNT(*) INTO v_records FROM domenii;
  
  FOR i IN 1..numberElements LOOP
    v_records := v_records + 1;
    SELECT COUNT(*) INTO flag FROM domenii WHERE nume_domeniu = ('Domeniul_'||i);
    IF(flag = 0) THEN --daca domeniul nu este in tabela, il adaugam
      INSERT INTO domenii VALUES (v_records, 'Domeniul_'||i);
    END IF;
  END LOOP;

end insert_n_records;
/


create or replace function isDomainAlready(v_domain_name in domenii.nume_domeniu% TYPE)
RETURN NUMBER
IS
  nrOfDomains NUMBER :=0;
BEGIN
  SELECT COUNT(*) INTO  nrofdomains FROM domenii where nume_domeniu = v_domain_name;
  RETURN nrofdomains;
END isDomainAlready;
/



Create or replace function get_max_sal_from_judet (v_nume_judet IN Locatii.judet%TYPE)
return number
is
v_salary Meserii.salariu%TYPE:=0;
begin
select MAX(salariu) into v_salary from meserii where meserii_locatie=v_nume_judet;
return (v_salary);
end get_max_sal_from_judet;
/


Create or replace function get_min_sal_from_judet (v_nume_judet IN Locatii.judet%TYPE)
return number
is
v_salary Meserii.salariu%TYPE:=0;
begin
select MIN(salariu) into v_salary from meserii where meserii_locatie=v_nume_judet;
return (v_salary);
end get_max_sal_from_judet;
/


create or replace function returnDomainName(v_id_domeniu IN Domenii.id_domeniu%type)
return varchar
is
v_nume_domeniu Domenii.nume_domeniu%Type:=0;
begin
select nume_domeniu into v_nume_domeniu from domenii where id_domeniu=v_id_domeniu;
return (v_nume_domeniu);
end returnDomainName;
/






















