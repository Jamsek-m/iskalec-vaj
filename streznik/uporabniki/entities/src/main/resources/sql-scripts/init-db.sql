INSERT INTO sif_vloga(sifra, naziv) VALUES ('USER', 'Uporabnik'), ('MOD', 'Moderator'), ('ADMIN', 'Administrator');

INSERT INTO sif_status_uporabnik(sifra, naziv) VALUES ('ACTIVE', 'Aktiven'), ('INACTIVE', 'Neaktiven'), ('NOT_CONFIRMED', 'Nepotrjen');

INSERT INTO enota(naziv, sifra) VALUES ('Univerzitetni', 'UNI'), ('Visokošolski', 'VSŠ');

INSERT INTO letnik(letnik, enota_id) VALUES(1, 1), (2, 1), (3, 1), (1, 2), (2, 2), (3, 2);

INSERT INTO uporabnik(uporabnisko_ime, ime, priimek, email, geslo, status_id, letnik_id) VALUES('miha', 'Miha', 'Jamšek', 'miha_jamsek@windowslive.com', 'geslo', 1, 3);

INSERT INTO uporabniske_vloge(uporabnik_id, vloga_id) VALUES(1, 1), (1, 2), (1, 3);

