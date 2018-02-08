"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
/* tslint:disable max-classes-per-file */
class Napaka {
}
class PrijavaException {
    constructor(napaka, sporocilo, status) {
        this.napaka = napaka;
        this.sporocilo = sporocilo;
        this.status = status;
    }
}
exports.PrijavaException = PrijavaException;
