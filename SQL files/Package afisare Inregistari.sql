SET SERVEROUT ON
CREATE OR REPLACE PACKAGE intoarceDate
as

type p_lista is ref cursor;


PROCEDURE intoarceToateDomeniile(cursorDomenii OUT p_lista);
PROCEDURE intoarceToateFirmele(cursorFirme OUT p_lista);
PROCEDURE intoarceToateMeseriile(cursorMeserii OUT p_lista);
PROCEDURE intoarceToateLocatiile(cursorLocatii OUT p_lista);
PROCEDURE intoarceToateMeseriileDinCos(cursorMeseriiCos OUT p_lista);


PROCEDURE intoarceMeseriiDupaCriteriu(cursorMeseriiDupaCriteriu OUT p_lista, p_nume_criteriu IN VARCHAR);
PROCEDURE intoarceMeseriiDupaSalariu(cursorMeseriiDupaSalariu OUT p_lista, ordinea IN varchar);
PROCEDURE intoarceMeseriiDupaNumeMeserie(cursorMeseriiDupaNumeMeserie OUT p_lista, ordinea IN varchar);
PROCEDURE intoarceMeseriiDupaNumeLocatie(cursorMeseriiDupaNumeLocatie OUT p_lista, ordinea IN varchar);
PROCEDURE intoarceMeseriiDupaNumeFirma(cursorMeseriiDupaNumeFirma OUT p_lista, ordinea IN varchar);

PROCEDURE intoarceMeseriiDupaUnDomeniu(cursorMeseriiDupaDomeniu OUT p_lista, p_domeniul IN varchar);
PROCEDURE intoarceContacteleDeLaOFirma(cursorContacteFirma OUT p_lista, p_id_meserie IN Number);
PROCEDURE intoarceLocMeserieCos(cursorLocatieMeserieCos OUT p_lista, p_id_locatie IN Number);

END intoarceDate;
/

CREATE OR REPLACE PACKAGE BODY intoarceDate
as

PROCEDURE intoarceToateDomeniile(cursorDomenii OUT p_lista)
 IS 
 BEGIN
  OPEN cursorDomenii for select * from domenii ORDER BY id_domeniu;
 END intoarceToateDomeniile;
 
 PROCEDURE intoarceToateFirmele(cursorFirme OUT p_lista)
 IS 
 BEGIN
  OPEN cursorFirme for select * from firme ORDER BY id_firma;
 END intoarceToateFirmele;
 
 PROCEDURE intoarceToateMeseriile(cursorMeserii OUT p_lista)
 IS 
 BEGIN
  OPEN cursorMeserii for select * from meserii ORDER BY id_meserie;
 END intoarceToateMeseriile;
 
 PROCEDURE intoarceToateLocatiile(cursorLocatii OUT p_lista)
 IS 
 BEGIN
  OPEN cursorLocatii for select * from locatii ORDER BY id_locatie;
 END intoarceToateLocatiile;
 
  PROCEDURE intoarceToateMeseriileDinCos(cursorMeseriiCos OUT p_lista)
 IS 
 BEGIN
  OPEN cursorMeseriiCos for select * from cos_meserii ORDER BY id_locatie;
 END intoarceToateMeseriileDinCos;
 
 
 
 
 
 PROCEDURE intoarceMeseriiDupaCriteriu(cursorMeseriiDupaCriteriu OUT p_lista, p_nume_criteriu IN VARCHAR)
 IS 
 BEGIN
  OPEN cursorMeseriiDupaCriteriu for select * from meserii ORDER BY p_nume_criteriu;
 END intoarceMeseriiDupaCriteriu;
 
 
 
 
   PROCEDURE intoarceMeseriiDupaSalariu(cursorMeseriiDupaSalariu OUT p_lista, ordinea IN varchar)
 IS 
 BEGIN
  OPEN cursorMeseriiDupaSalariu for select * from meserii ORDER BY CASE WHEN ordinea = 'crescator' THEN salariu END ASC, 
                                                                    CASE WHEN ordinea = 'descrescator' THEN salariu END DESC;
 END intoarceMeseriiDupaSalariu;
 
  PROCEDURE intoarceMeseriiDupaNumeMeserie(cursorMeseriiDupaNumeMeserie OUT p_lista, ordinea IN varchar)
 IS 
 BEGIN
  OPEN cursorMeseriiDupaNumeMeserie for select * from meserii ORDER BY CASE WHEN ordinea = 'crescator' THEN nume_meserie END ASC, 
                                                                    CASE WHEN ordinea = 'descrescator' THEN nume_meserie END DESC;
 END intoarceMeseriiDupaNumeMeserie;
 
   PROCEDURE intoarceMeseriiDupaNumeLocatie(cursorMeseriiDupaNumeLocatie OUT p_lista, ordinea IN varchar)
 IS 
 BEGIN
  OPEN cursorMeseriiDupaNumeLocatie for select * from meserii ORDER BY CASE WHEN ordinea = 'crescator' THEN meserii_locatie END ASC, 
                                                                    CASE WHEN ordinea = 'descrescator' THEN meserii_locatie END DESC;
 END intoarceMeseriiDupaNumeLocatie;
 
    PROCEDURE intoarceMeseriiDupaNumeFirma(cursorMeseriiDupaNumeFirma OUT p_lista, ordinea IN varchar)
 IS 
 BEGIN
  OPEN cursorMeseriiDupaNumeFirma for select * from meserii ORDER BY CASE WHEN ordinea = 'crescator' THEN meserii_nume_firma END ASC, 
                                                                    CASE WHEN ordinea = 'descrescator' THEN meserii_nume_firma END DESC;
 END intoarceMeseriiDupaNumeFirma;
 
 PROCEDURE intoarceMeseriiDupaUnDomeniu(cursorMeseriiDupaDomeniu OUT p_lista, p_domeniul IN varchar)
 IS 
 BEGIN
  OPEN cursorMeseriiDupaDomeniu for select * from meserii m, firme f where f.firme_nume_domeniu=concat(concat('',p_domeniul),'') and m.id_firma=f.id_firma;
 END intoarceMeseriiDupaUnDomeniu;
 
 
  PROCEDURE intoarceContacteleDeLaOFirma(cursorContacteFirma OUT p_lista, p_id_meserie IN Number)
 IS 
 BEGIN
  OPEN cursorContacteFirma for select f.id_firma, nume_firma, numar_telefon, email, firme_nume_domeniu, id_domeniu
								from firme f, meserii m
								where m.meserii_nume_firma=f.nume_firma and m.id_firma=f.id_firma 
								AND m.id_firma=(select id_firma from meserii where id_meserie = p_id_meserie);
 END intoarceContacteleDeLaOFirma;
 
 
   PROCEDURE intoarceLocMeserieCos(cursorLocatieMeserieCos OUT p_lista, p_id_locatie IN Number)
 IS 
 BEGIN
  OPEN cursorLocatieMeserieCos for select l.id_locatie, judet, nume_locatie, adresa
								from locatii l, meserii m
								where m.id_locatie=l.id_locatie And m.id_locatie=p_id_locatie;
 END intoarceLocMeserieCos;

 
 


END intoarceDate;
/