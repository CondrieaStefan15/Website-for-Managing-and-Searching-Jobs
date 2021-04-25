SET SERVEROUT ON
CREATE OR REPLACE PACKAGE packageDelete
AS

     PROCEDURE deleteDomenii(d_id IN domenii.id_domeniu%TYPE);
	 PROCEDURE deleteFirme(d_id IN firme.id_firma%TYPE);
	 PROCEDURE deleteMeserii(d_id IN meserii.id_meserie%TYPE);
	 PROCEDURE deleteLocatii(d_id IN locatii.id_locatie%TYPE);
	 
	 PROCEDURE deleteMeseriiDinCos(d_id IN meserii.id_meserie%TYPE);

END packageDelete;
/

CREATE OR REPLACE PACKAGE BODY packageDelete
as

	PROCEDURE deleteDomenii(d_id IN domenii.id_domeniu%TYPE)
	IS 
	BEGIN
		DELETE FROM domenii WHERE id_domeniu = d_id;

		
	END deleteDomenii;
	
	PROCEDURE deleteFirme(d_id IN firme.id_firma%TYPE)
	IS 
	BEGIN
		DELETE FROM firme WHERE id_firma = d_id;

		
	END deleteFirme;
	
	PROCEDURE deleteMeserii(d_id IN meserii.id_meserie%TYPE)
	IS 
	BEGIN
		DELETE FROM meserii WHERE id_meserie = d_id;

		
	END deleteMeserii;
	
	
	PROCEDURE deleteLocatii(d_id IN locatii.id_locatie%TYPE)
	IS 
	BEGIN
		DELETE FROM locatii WHERE id_locatie = d_id;

		
	END deleteLocatii;
	
	
	PROCEDURE deleteMeseriiDinCos(d_id IN meserii.id_meserie%TYPE)
	IS 
	BEGIN
		DELETE FROM cos_meserii WHERE id_meserie = d_id;

		
	END deleteMeseriiDinCos;

	

END packageDelete;
/
	