"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const jwt = require("jsonwebtoken");
const path = require("path");
const request = require("request");
const encrypt = require("./encrypt");
/* tslint:disable no-var-requires */
const config = require(path.join(__dirname, "..", "..", "..", "config", "storitev.json"));
/* tslint:enable no-var-requires */
class Avtentikacija {
    constructor() {
        this.baseUrl = config.base_url;
    }
    prijaviUporabnika(email, geslo, done) {
        const URL = `${this.baseUrl}email/${email}`;
        console.log(URL);
        request.get({ url: URL }, (error, res, body) => {
            if (error) {
                return done(error);
            }
            else if (res.statusCode === 200) {
                const uporabnik = JSON.parse(body);
                encrypt.comparePassword(geslo, uporabnik.geslo, (napaka, isMatch) => {
                    if (isMatch) {
                        return done(undefined, this.generateJWTtoken(uporabnik));
                    }
                    else {
                        return done(undefined, false);
                    }
                });
            }
            else {
                console.log("KODA: ", res.statusCode);
                const napaka = new Error("Not Found");
                console.log("NAPAKA", napaka);
                return done(napaka);
            }
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
