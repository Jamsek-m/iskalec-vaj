"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const bcrypt = require("bcrypt");
function encryptPassword(password, done) {
    bcrypt.genSalt(10, (napakaSalt, salt) => {
        if (napakaSalt) {
            return done(napakaSalt);
        }
        else {
            bcrypt.hash(password, salt, (napakaHash, hash) => {
                return done(napakaHash, hash);
            });
        }
    });
}
exports.encryptPassword = encryptPassword;
function comparePassword(unhashedPass, hash, done) {
    bcrypt.compare(unhashedPass, hash, (napaka, passMatch) => {
        if (napaka) {
            return done(napaka);
        }
        else {
            return done(null, passMatch);
        }
    });
}
exports.comparePassword = comparePassword;
