INSERT INTO sif_vloga(sifra, naziv) VALUES ('USER', 'Uporabnik'), ('MOD', 'Moderator'), ('ADMIN', 'Administrator');

INSERT INTO sif_status_uporabnik(sifra, naziv) VALUES ('ACTIVE', 'Aktiven'), ('INACTIVE', 'Neaktiven');

INSERT INTO uporabnik(uporabnisko_ime, ime, priimek, email, geslo, status)
  VALUES('miha', 'Miha', 'Jamšek', 'miha_jamsek@windowslive.com', 'geslo', 1);

INSERT INTO uporabniske_vloge(uporabnik_id, vloga_id) VALUES(1, 1), (1, 2), (1, 3);

