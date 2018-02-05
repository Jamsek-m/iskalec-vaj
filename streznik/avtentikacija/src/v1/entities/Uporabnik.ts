export class Uporabnik {

    public uporabniskoIme: string;
    public ime: string;
    public priimek: string;
    public email: string;
    public geslo1: string;
    public geslo2: string;
    public hashedGeslo: string;
    public letnik: number;

    constructor(uporabniskoIme: string, ime: string, priimek: string,
                email: string, geslo1: string, geslo2: string, letnik: number) {
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
