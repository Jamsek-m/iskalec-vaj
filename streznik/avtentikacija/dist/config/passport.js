"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const passport = require("passport");
const passportLocal = require("passport-local");
const request = require("request");
const LocalStrategy = passportLocal.Strategy;
function default_1() {
    passport.use(new LocalStrategy({ usernameField: "email", passwordField: "geslo" }, (email, password, done) => {
        console.log(`podatki: ${email}, ${password}!`);
        const URL = `http://localhost:8080/v1/uporabniki/email/${email}`;
        console.log("iscem podatke!");
        request.get({ url: URL }, (error, res, body) => {
            if (error) {
                return done(error);
            }
            else {
                const uporabnik = JSON.parse(body);
                if (uporabnik.geslo === password) {
                    return done(null, uporabnik);
                }
                else {
                    return done(null, false, { message: "Napacen email in/ali geslo!" });
                }
            }
        });
    }));
}
exports.default = default_1;
