CREATE OR REPLACE PACKAGE packageUpdate
AS

	PROCEDURE updateDomenii
(v_id IN Domenii.id_domeniu%TYPE,
 v_nume_domeniu IN Domenii.nume_domeniu%TYPE);

	PROCEDURE updateFirme
(v_id IN Firme.id_firma%TYPE,
 v_nume_firma IN Firme.nume_firma%TYPE,
 v_numar_telefon IN Firme.numar_telefon%TYPE,
 v_email IN Firme.email%TYPE,
 v_id_domeniu IN Firme.id_domeniu%TYPE);

	PROCEDURE updateMeserii
(v_id IN Meserii.id_meserie%TYPE,
 v_nume_meserie IN Meserii.nume_meserie%TYPE,
 v_tip_meserie IN Meserii.tip_meserie%TYPE,
 v_salariu IN Meserii.salariu%TYPE,
 v_nivel_studii IN Meserii.nivel_studii%TYPE,
 v_particularitati IN Meserii.particularitati%TYPE,
 v_id_firma IN Meserii.id_firma%TYPE,
 v_id_locatie IN Meserii.id_locatie%TYPE);

	PROCEDURE updateLocatii
(v_id IN Locatii.id_locatie%TYPE,
 v_judet IN Locatii.judet%TYPE,
 v_nume_locatie IN Locatii.nume_locatie%TYPE,
 v_adresa IN Locatii.adresa%TYPE);

END packageUpdate;
/


CREATE OR REPLACE PACKAGE BODY packageUpdate
as

PROCEDURE updateDomenii
(v_id IN Domenii.id_domeniu%TYPE,
 v_nume_domeniu IN Domenii.nume_domeniu%TYPE)
IS
BEGIN
	UPDATE Domenii
	SET nume_domeniu = v_nume_domeniu
	WHERE id_domeniu = v_id;
END updateDomenii;


PROCEDURE updateFirme
(v_id IN Firme.id_firma%TYPE,
 v_nume_firma IN Firme.nume_firma%TYPE,
 v_numar_telefon IN Firme.numar_telefon%TYPE,
 v_email IN Firme.email%TYPE,
 v_id_domeniu IN Firme.id_domeniu%TYPE)

IS 
BEGIN
	UPDATE Firme
	SET  nume_firma = v_nume_firma,
	     numar_telefon = v_numar_telefon,
         email = v_email,
		 firme_nume_domeniu=(select nume_domeniu from domenii where id_domeniu=v_id_domeniu),
         id_domeniu = v_id_domeniu

     WHERE id_firma = v_id;
END updateFirme;

PROCEDURE updateMeserii
(v_id IN Meserii.id_meserie%TYPE,
 v_nume_meserie IN Meserii.nume_meserie%TYPE,
 v_tip_meserie IN Meserii.tip_meserie%TYPE,
 v_salariu IN Meserii.salariu%TYPE,
 v_nivel_studii IN Meserii.nivel_studii%TYPE,
 v_particularitati IN Meserii.particularitati%TYPE,
 v_id_firma IN Meserii.id_firma%TYPE,
 v_id_locatie IN Meserii.id_locatie%TYPE)
IS
BEGIN
	UPDATE Meserii
	SET nume_meserie = v_nume_meserie,
	    tip_meserie = v_tip_meserie,
	    salariu = v_salariu,
	    nivel_studii = v_nivel_studii,
		particularitati = v_particularitati,
		meserii_nume_firma=(select nume_firma from firme where id_firma=v_id_firma),
		meserii_locatie=(select judet from locatii where id_locatie=v_id_locatie),
		id_firma=v_id_firma,
		id_locatie=v_id_locatie
	WHERE id_meserie = v_id;
END updateMeserii;



PROCEDURE updateLocatii
(v_id IN Locatii.id_locatie%TYPE,
 v_judet IN Locatii.judet%TYPE,
 v_nume_locatie IN Locatii.nume_locatie%TYPE,
 v_adresa IN Locatii.adresa%TYPE)
IS
BEGIN
	UPDATE Locatii
	SET judet = v_judet,
	    nume_locatie = v_nume_locatie,
        adresa = v_adresa
		 
	WHERE id_locatie = v_id;
END updateLocatii;

END packageUpdate;
/