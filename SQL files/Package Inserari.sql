CREATE OR REPLACE PACKAGE packageInserari
AS
	
	PROCEDURE insereazaDomenii(p_id IN Domenii.id_domeniu%TYPE, p_nume IN Domenii.nume_domeniu%TYPE); 
	PROCEDURE insereazaFirme(p_id IN Firme.id_firma%TYPE, p_nume IN Firme.nume_firma%TYPE, p_numar_telefon IN Firme.numar_telefon%TYPE, p_email IN Firme.email%TYPE, p_id_domeniu IN Firme.id_domeniu%TYPE);
	PROCEDURE insereazaMeserii(p_id IN Meserii.id_meserie%TYPE, p_nume IN Meserii.nume_meserie%TYPE, p_tip_meserie IN Meserii.tip_meserie%TYPE, p_salariu IN Meserii.salariu%TYPE, p_nivel_studii IN Meserii.nivel_studii%TYPE, p_particularitati IN Meserii.particularitati%TYPE, p_id_firma IN Meserii.id_firma%TYPE, p_id_locatie IN Meserii.id_locatie%TYPE);
	PROCEDURE insereazaLocatii(p_id IN Locatii.id_locatie%TYPE, p_judet IN Locatii.judet%TYPE, p_nume_locatie IN Locatii.nume_locatie%TYPE, p_adresa IN Locatii.adresa%TYPE);
	PROCEDURE insereazaInCosMeserii(p_id IN Meserii.id_meserie%TYPE);

END packageInserari;
/


CREATE OR REPLACE PACKAGE BODY packageInserari
AS
	
	
	PROCEDURE insereazaDomenii(p_id IN Domenii.id_domeniu%TYPE, p_nume IN Domenii.nume_domeniu%TYPE)
	IS
	BEGIN
	INSERT 
	INTO Domenii(id_domeniu, nume_domeniu)
	VALUES (p_id, p_nume);
	END insereazaDomenii;
	
	
	PROCEDURE insereazaFirme(p_id IN Firme.id_firma%TYPE, p_nume IN Firme.nume_firma%TYPE, p_numar_telefon IN Firme.numar_telefon%TYPE, p_email IN Firme.email%TYPE, p_id_domeniu IN Firme.id_domeniu%TYPE)
	IS
	BEGIN
	INSERT 
	INTO Firme(id_firma, nume_firma, numar_telefon, email, firme_nume_domeniu, id_domeniu)
	VALUES (p_id, p_nume, p_numar_telefon, p_email, (select nume_domeniu from domenii where id_domeniu=p_id_domeniu), p_id_domeniu);
	END insereazaFirme;
	
	
	PROCEDURE insereazaMeserii(p_id IN Meserii.id_meserie%TYPE, p_nume IN Meserii.nume_meserie%TYPE, p_tip_meserie IN Meserii.tip_meserie%TYPE, p_salariu IN Meserii.salariu%TYPE, p_nivel_studii IN Meserii.nivel_studii%TYPE, p_particularitati IN Meserii.particularitati%TYPE, p_id_firma IN Meserii.id_firma%TYPE, p_id_locatie IN Meserii.id_locatie%TYPE)
	IS
	BEGIN
	INSERT 
	INTO Meserii(id_meserie, nume_meserie, tip_meserie, salariu, nivel_studii, particularitati, meserii_nume_firma, meserii_locatie, id_firma, id_locatie)
	VALUES (p_id, p_nume, p_tip_meserie, p_salariu, p_nivel_studii, p_particularitati,(select nume_firma from firme where id_firma=p_id_firma), (select judet from locatii where id_locatie=p_id_locatie), p_id_firma, p_id_locatie);
	END insereazaMeserii;



	PROCEDURE insereazaLocatii(p_id IN Locatii.id_locatie%TYPE, p_judet IN Locatii.judet%TYPE, p_nume_locatie IN Locatii.nume_locatie%TYPE, p_adresa IN Locatii.adresa%TYPE)
	IS
	BEGIN
	INSERT 
	INTO Locatii(id_locatie, judet, nume_locatie, adresa)
	VALUES (p_id, p_judet, p_nume_locatie, p_adresa);
	END insereazaLocatii;
	
	
	PROCEDURE insereazaInCosMeserii(p_id IN Meserii.id_meserie%TYPE)
	IS
	BEGIN
	INSERT 
	into cos_meserii (id_meserie, nume_meserie, tip_meserie, salariu, nivel_studii, particularitati, meserii_nume_firma, meserii_locatie, id_firma, id_locatie)
	select * from meserii where id_meserie=p_id;
	END insereazaInCosMeserii;
	
END packageInserari;
/



















