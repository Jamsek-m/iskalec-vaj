import {PrijavaException} from "../entities/exceptions/PrijavaException";

export class Uporabnik {

    public static preveriPrazneVrednosti(body: any): [boolean, PrijavaException] {
        if (!body.uporabniskoIme || !body.ime || !body.priimek || !body.email ||
            !body.geslo1 || !body.geslo2 || !body.letnik) {
                const napaka = new PrijavaException({
                    message: "Nekateri podatki za registracijo manjkajo!",
                    name: "Manjkajo podatki",
                    stack: null,
                }, "Manjkajo podatki", 400);
                return [true, napaka];
        } else {
            return [false, null];
        }
    }

    public uporabniskoIme: string;
    public ime: string;
    public priimek: string;
    public email: string;
    public geslo: string;
    public letnik: number;
    public kljuc: string;

    constructor(uporabniskoIme: string, ime: string, priimek: string,
                email: string, geslo: string, letnik: number) {
            this.uporabniskoIme = uporabniskoIme;
            this.ime = ime;
            this.priimek = priimek;
            this.email = email;
            this.letnik = letnik;
            this.geslo = geslo;
    }

}
