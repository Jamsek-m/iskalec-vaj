"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
class Uporabnik {
    constructor(uporabniskoIme, ime, priimek, email, geslo1, geslo2, letnik) {
        this.uporabniskoIme = uporabniskoIme;
        this.ime = ime;
        this.priimek = priimek;
        this.email = email;
        this.geslo1 = geslo1;
        this.geslo2 = geslo2;
        this.letnik = letnik;
        this.hashedGeslo = "";
    }
}
exports.Uporabnik = Uporabnik;
