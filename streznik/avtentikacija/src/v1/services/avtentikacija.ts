import * as jwt from "jsonwebtoken";
import * as path from "path";
import * as request from "request";
import {Uporabnik} from "../entities/Uporabnik";
import * as encrypt from "./encrypt";
/* tslint:disable no-var-requires */
const config = require(path.join(__dirname, "..", "..", "..", "config", "storitev.json"));
/* tslint:enable no-var-requires */

export class Avtentikacija {

    private uporabnikiBaseUrl: string;
    private emailBaseUrl: string;

    constructor() {
        this.uporabnikiBaseUrl = config.uporabnikiService.base_url;
        this.emailBaseUrl = config.emailService.base_url;
    }

    public prijaviUporabnika(email: string, geslo: string, done: (err: Error, token?: string|boolean) => void) {
        const URL = `${this.uporabnikiBaseUrl}email/${email}`;

        request.get({url: URL}, (error, res, body) => {
            if (error) {
                return done(error);
            } else if (res.statusCode === 200) {
                const uporabnik = JSON.parse(body);
                encrypt.comparePassword(geslo, uporabnik.geslo, (napaka, isMatch) => {
                    if (isMatch) {
                        return done(undefined, this.generateJWTtoken(uporabnik));
                    } else {
                        return done(undefined, false);
                    }
                });
            } else {
                return done({
                    message: `Napaka pri klicu mikrostoritve uporabnikov. Koda odgovora: ${res.statusCode}`,
                    name: "Napaka pri dosegu mikrostoritve!",
                    stack: null,
                });
            }
        });
    }

    public registrirajUporabnika(uporabnik: Uporabnik, done: (err: Error, uspeh?: boolean) => void) {
        if (uporabnik.geslo1 !== uporabnik.geslo2) {
            return done({message: "Gesli se ne ujemata!", name: "Password mismatch"});
        }
        encrypt.encryptPassword(uporabnik.geslo1, (err, hash) => {
            const URL = `${this.uporabnikiBaseUrl}`;
            uporabnik.hashedGeslo = hash;
            console.log("klicem");
            request.post({url: URL, body: uporabnik, json: true}, (error, res, body) => {
                if (error) {
                    return done(error);
                } else if (res.statusCode === 201) {
                    return done(null, true);
                } else {
                    console.log(`KODA: ${res.statusCode}, body: ${body}`);
                    return done(null, false);
                }
            });
        });

    }

    private generateJWTtoken(uporabnik) {

        const vloge = uporabnik.vloge.map( (item) => {
            return item.id;
        });

        const expirationDate = new Date();
        expirationDate.setDate(expirationDate.getDate() + 7);
        return jwt.sign({
            id: uporabnik.id,
            roles: vloge,
        }, config.token_secret);
    }

}

/*function generateJWTtoken(uporabnik) {

    const vloge = uporabnik.vloge.map( (item) => {
        return item.id;
    });

    const expirationDate = new Date();
    expirationDate.setDate(expirationDate.getDate() + 7);
    return jwt.sign({
        id: uporabnik.id,
        roles: vloge,
    }, config.token_secret);
}

export function prijaviUporabnika(email: string, geslo: string , done: (err: Error, token?: string|boolean) => void) {
    const URL = `http://localhost:8080/v1/uporabniki/email/${email}`;

    request.get({url: URL}, (error, res, body) => {
        console.log(res.statusCode);
        if (error) {
            return done(error);
        } else if (res.statusCode === 200) {
            const uporabnik = JSON.parse(body);
            encrypt.comparePassword(geslo, uporabnik.geslo, (napaka, isMatch) => {
                if (isMatch) {
                    return done(undefined, generateJWTtoken(uporabnik));
                } else {
                    return done(undefined, false);
                }
            });
        } else {
            const napaka = new Error("Not Found");
            console.log("napaka: ", napaka);
            return done(napaka);
        }
    });
}*/
