"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const jwt = require("jsonwebtoken");
const path = require("path");
const request = require("request");
const PrijavaException_1 = require("../entities/exceptions/PrijavaException");
const encrypt = require("./encrypt");
/* tslint:disable no-var-requires */
const config = require(path.join(__dirname, "..", "..", "..", "config", "storitev.json"));
/* tslint:enable no-var-requires */
class Avtentikacija {
    constructor() {
        this.uporabnikiBaseUrl = config.uporabnikiService.base_url;
    }
    prijaviUporabnika(email, geslo, done) {
        const URL = `${this.uporabnikiBaseUrl}email/`;
        const KLJUC = config.uporabniki_kljuc;
        request.post({ url: URL, body: { email, kljuc: KLJUC }, json: true }, (error, res, body) => {
            // prislo je do napake pri dostopu storitve
            if (error) {
                const napaka = new PrijavaException_1.PrijavaException(error, "Storitev uporabniki ni dosegljiva - ali je bila zagnana?", 424);
                return done(napaka, false);
                // uporabnik obstaja, primerjaj geslo
            }
            else if (res.statusCode === 200) {
                const uporabnik = body;
                encrypt.comparePassword(geslo, uporabnik.geslo, (napaka, isMatch) => {
                    if (isMatch) {
                        return done(undefined, this.generateJWTtoken(uporabnik));
                    }
                    else {
                        return done(undefined, false);
                    }
                });
                // uporabnik ne obstaja
            }
            else if (res.statusCode === 404) {
                return done(undefined, false);
                // napaka
            }
            else {
                const napaka = new PrijavaException_1.PrijavaException({
                    message: `Napaka pri klicu mikrostoritve uporabnikov. Koda odgovora: ${res.statusCode}`,
                    name: "Napaka pri dosegu mikrostoritve!",
                    stack: null,
                }, "Napaka pri poizvedbi uporabnika!", 500);
                return done(napaka, false);
            }
        });
    }
    registrirajUporabnika(uporabnik, done) {
        encrypt.encryptPassword(uporabnik.geslo, (err, hash) => {
            const URL = `${this.uporabnikiBaseUrl}`;
            const KLJUC = config.uporabniki_kljuc;
            uporabnik.geslo = hash;
            uporabnik.kljuc = KLJUC;
            request.post({ url: URL, body: uporabnik, json: true }, (error, res, body) => {
                // Prislo je do napake pri dostopu do storitve
                if (error) {
                    const napaka = new PrijavaException_1.PrijavaException(error, "Storitev uporabniki ni dosegljiva - ali je bila zagnana?", 424);
                    return done(napaka, false);
                    // Uporabnik je bil kreiran
                }
                else if (res.statusCode === 201) {
                    return done(undefined, true);
                    // napaka
                }
                else {
                    const napaka = new PrijavaException_1.PrijavaException(body, body.sporocilo, res.statusCode);
                    return done(napaka, false);
                }
            });
        });
    }
    generateJWTtoken(uporabnik) {
        const vloge = uporabnik.vloge.map((item) => {
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
exports.Avtentikacija = Avtentikacija;
