"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = require("express");
const avtentikacija_1 = require("../services/avtentikacija");
const enc = require("../services/encrypt");
const resp = require("./odgovor");
class IndexRouter {
    constructor() {
        this.router = express_1.Router();
        this.init();
    }
    init() {
        this.router.get("/", this.getAll);
        this.router.post("/prijava", this.prijaviUporabnika);
        this.router.get("/hash", this.zakriptirajGeslo);
    }
    zakriptirajGeslo(req, res, next) {
        const geslo = req.query.geslo;
        enc.encryptPassword(geslo, (napaka, hash) => {
            res.json({ err: napaka, geslo: hash });
        });
    }
    prijaviUporabnika(req, res, next) {
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
    getAll(req, res, next) {
        res.json({
            sporocilo: "pozdravljen svet!",
        });
    }
}
exports.IndexRouter = IndexRouter;
const indexRoutes = new IndexRouter().router;
exports.default = indexRoutes;
