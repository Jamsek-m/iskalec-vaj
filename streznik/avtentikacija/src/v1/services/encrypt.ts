import * as bcrypt from "bcrypt";

export function encryptPassword(password, done) {
    bcrypt.genSalt(10, (napakaSalt, salt) => {
        if (napakaSalt) {
            return done(napakaSalt);
        } else {
            bcrypt.hash(password, salt, (napakaHash, hash) => {
                return done(napakaHash, hash);
            });
        }
    });
}

export function comparePassword(unhashedPass, hash, done) {
    bcrypt.compare(unhashedPass, hash, (napaka, passMatch) => {
        if (napaka) {
            return done(napaka);
        } else {
            return done(null, passMatch);
        }
    });
}
