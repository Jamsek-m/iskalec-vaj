"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = require("express");
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
                resp.returnJSONres(res, 500, { err: napaka });
            }
            else {
                if (!token) {
                    resp.returnJSONres(res, 401, { sporocilo: "Napaka!" });
                }
                else {
                    resp.returnJSONres(res, 200, { zeton: token });
                }
            }
        });
    }
    registrirajUporabnika(req, res, next) {
        const b = req.body;
        const avtentikacijaService = new avtentikacija_1.Avtentikacija();
        const uporabnik = new Uporabnik_1.Uporabnik(b.uporabniskoIme, b.ime, b.priimek, b.email, b.geslo1, b.geslo2, parseInt(b.letnik, 10));
        avtentikacijaService.registrirajUporabnika(uporabnik, (napaka, uspeh) => {
            if (napaka) {
                resp.returnJSONres(res, 500, { err: napaka });
            }
            else if (uspeh) {
                resp.returnJSONres(res, 201, { sporocilo: "OK" });
            }
            else {
                resp.returnJSONres(res, 400, { napaka: "slaba zahteva!" });
            }
        });
    }
}
exports.IndexRouter = IndexRouter;
const indexRoutes = new IndexRouter().router;
exports.default = indexRoutes;
