CREATE OR REPLACE TRIGGER domenii_id_domeniu_trg BEFORE
    INSERT ON domenii
    FOR EACH ROW
    WHEN ( new.id_domeniu IS NULL )
BEGIN
    :new.id_domeniu := domenii_id_domeniu_seq.nextval;
END;
/


CREATE OR REPLACE TRIGGER firme_id_firma_trg BEFORE
    INSERT ON firme
    FOR EACH ROW
    WHEN ( new.id_firma IS NULL )
BEGIN
    :new.id_firma := firme_id_firma_seq.nextval;
END;
/



CREATE OR REPLACE TRIGGER locatii_id_locatie_trg BEFORE
    INSERT ON locatii
    FOR EACH ROW
    WHEN ( new.id_locatie IS NULL )
BEGIN
    :new.id_locatie := locatii_id_locatie_seq.nextval;
END;
/



CREATE OR REPLACE TRIGGER meserii_id_meserie_trg BEFORE
    INSERT ON meserii
    FOR EACH ROW
    WHEN ( new.id_meserie IS NULL )
BEGIN
    :new.id_meserie := meserii_id_meserie_seq.nextval;
END;
/