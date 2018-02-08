"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = require("express");
const PrijavaException_1 = require("../entities/exceptions/PrijavaException");
const Uporabnik_1 = require("../entities/Uporabnik");
const avtentikacija_1 = require("../services/avtentikacija");
const resp = require("./odgovor");
class IndexRouter {
    constructor() {
        this.router = express_1.Router();
        this.init();
    }
    init() {
        this.router.post("/prijava", this.prijaviUporabnika);
        this.router.post("/register", this.registrirajUporabnika);
    }
    prijaviUporabnika(req, res, next) {
        if (!req.body.geslo || !req.body.geslo) {
            resp.returnJSONres(res, 400, { sporocilo: "Manjka email in/ali geslo!" });
            return;
        }
        const avtentikacijaService = new avtentikacija_1.Avtentikacija();
        avtentikacijaService.prijaviUporabnika(req.body.email, req.body.geslo, (napaka, token) => {
            if (napaka) {
                if (napaka.status === 424) {
                    resp.returnJSONres(res, 424, { sporocilo: "Storitev uporabnikov trenutno ni na voljo!" });
                }
                else {
                    resp.returnJSONres(res, napaka.status, { napaka: napaka.sporocilo });
                }
            }
            else {
                if (!token) {
                    resp.returnJSONres(res, 401, { sporocilo: "Napacen email in/ali geslo!" });
                }
                else {
                    resp.returnJSONres(res, 200, { zeton: token, sporocilo: "Prijava uspešna!" });
                }
            }
        });
    }
    registrirajUporabnika(req, res, next) {
        const b = req.body;
        const prazneVrednostiRezultat = Uporabnik_1.Uporabnik.preveriPrazneVrednosti(b);
        if (prazneVrednostiRezultat[0]) {
            resp.returnJSONres(res, 400, {
                sporocilo: prazneVrednostiRezultat[1].sporocilo,
                status: prazneVrednostiRezultat[1].status,
            });
            return;
        }
        if (b.geslo1 !== b.geslo2) {
            const napaka = new PrijavaException_1.PrijavaException({
                message: "Gesli se ne ujemata!",
                name: "Password mismatch",
                stack: null,
            }, "Gesli se ne ujemata", 400);
            resp.returnJSONres(res, 400, { status: napaka.status, sporocilo: napaka.sporocilo });
            return;
        }
        const avtentikacijaService = new avtentikacija_1.Avtentikacija();
        const uporabnik = new Uporabnik_1.Uporabnik(b.uporabniskoIme, b.ime, b.priimek, b.email, b.geslo1, parseInt(b.letnik, 10));
        avtentikacijaService.registrirajUporabnika(uporabnik, (napaka, uspeh) => {
            if (napaka) {
                if (napaka.status === 424) {
                    resp.returnJSONres(res, 424, { sporocilo: "Storitev uporabnikov trenutno ni na voljo!" });
                }
                else {
                    resp.returnJSONres(res, napaka.status, { napaka: napaka.sporocilo });
                }
            }
            else if (uspeh) {
                resp.returnJSONres(res, 201, { sporocilo: "OK" });
            }
            else {
                resp.returnJSONres(res, napaka.status, { napaka: napaka.sporocilo });
            }
        });
    }
}
exports.IndexRouter = IndexRouter;
const indexRoutes = new IndexRouter().router;
exports.default = indexRoutes;
