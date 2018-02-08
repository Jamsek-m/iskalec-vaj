"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const PrijavaException_1 = require("../entities/exceptions/PrijavaException");
class Uporabnik {
    static preveriPrazneVrednosti(body) {
        if (!body.uporabniskoIme || !body.ime || !body.priimek || !body.email ||
            !body.geslo1 || !body.geslo2 || !body.letnik) {
            const napaka = new PrijavaException_1.PrijavaException({
                message: "Nekateri podatki za registracijo manjkajo!",
                name: "Manjkajo podatki",
                stack: null,
            }, "Manjkajo podatki", 400);
            return [true, napaka];
        }
        else {
            return [false, null];
        }
    }
    constructor(uporabniskoIme, ime, priimek, email, geslo, letnik) {
        this.uporabniskoIme = uporabniskoIme;
        this.ime = ime;
        this.priimek = priimek;
        this.email = email;
        this.letnik = letnik;
        this.geslo = geslo;
    }
}
exports.Uporabnik = Uporabnik;
