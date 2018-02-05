import * as crypto from "crypto";
import {NextFunction, Request, Response, Router} from "express";
import * as passport from "passport";
import * as request from "request";
import { Uporabnik } from "../entities/Uporabnik";
import {Avtentikacija} from "../services/avtentikacija";
import * as enc from "../services/encrypt";
import * as resp from "./odgovor";

export class IndexRouter {

    public router: Router;

    constructor() {
        this.router = Router();
        this.init();
    }

    public init() {
        this.router.get("/", this.getAll);
        this.router.post("/prijava", this.prijaviUporabnika);
        this.router.post("/register", this.registrirajUporabnika);
        this.router.get("/hash", this.zakriptirajGeslo);
    }

    public zakriptirajGeslo(req: Request, res: Response, next: NextFunction) {
        const geslo = req.query.geslo;
        enc.encryptPassword(geslo, (napaka, hash) => {
            res.json({err: napaka, geslo: hash});
        });
    }

    public prijaviUporabnika(req: Request, res: Response, next: NextFunction) {
        const avtentikacijaService: Avtentikacija = new Avtentikacija();
        avtentikacijaService.prijaviUporabnika(req.body.email, req.body.geslo, (napaka, token) => {
            if (napaka) {
                resp.returnJSONres(res, 500, {err: napaka});
            } else {
                if (!token) {
                    resp.returnJSONres(res, 401, {sporocilo: "Napaka!"});
                } else {
                    resp.returnJSONres(res, 200, {zeton: token});
                }
            }
        });
    }

    public registrirajUporabnika(req: Request, res: Response, next: NextFunction) {
        const b = req.body;
        const avtentikacijaService: Avtentikacija = new Avtentikacija();
        const uporabnik: Uporabnik = new Uporabnik(b.uporabniskoIme, b.ime, b.priimek, b.email,
            b.geslo1, b.geslo2, parseInt(b.letnik, 10));
        avtentikacijaService.registrirajUporabnika(uporabnik, (napaka, uspeh) => {
            if (napaka) {
                resp.returnJSONres(res, 500, {err: napaka});
            } else if (uspeh) {
                resp.returnJSONres(res, 201, {sporocilo: "OK"});
            } else {
                resp.returnJSONres(res, 400, {napaka: "slaba zahteva!"});
            }
        });
    }

    public getAll(req: Request, res: Response, next: NextFunction) {
        res.json({
            sporocilo: "pozdravljen svet!",
        });
    }

}

const indexRoutes = new IndexRouter().router;

export default indexRoutes;
