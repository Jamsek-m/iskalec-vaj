import * as crypto from "crypto";
import {NextFunction, Request, Response, Router} from "express";
import * as passport from "passport";
import * as request from "request";
import {PrijavaException} from "../entities/exceptions/PrijavaException";
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
        this.router.post("/prijava", this.prijaviUporabnika);
        this.router.post("/register", this.registrirajUporabnika);
    }

    public prijaviUporabnika(req: Request, res: Response, next: NextFunction) {
        if (!req.body.geslo || !req.body.geslo) {
            resp.returnJSONres(res, 400, {sporocilo: "Manjka email in/ali geslo!"});
            return;
        }

        const avtentikacijaService: Avtentikacija = new Avtentikacija();

        avtentikacijaService.prijaviUporabnika(req.body.email, req.body.geslo, (napaka: PrijavaException,
            token: string|boolean) => {
            if (napaka) {
                if (napaka.status === 424) {
                    resp.returnJSONres(res, 424, {sporocilo: "Storitev uporabnikov trenutno ni na voljo!"});
                } else {
                    resp.returnJSONres(res, napaka.status, {napaka: napaka.sporocilo});
                }
            } else {
                if (!token) {
                    resp.returnJSONres(res, 401, {sporocilo: "Napacen email in/ali geslo!"});
                } else {
                    resp.returnJSONres(res, 200, {zeton: token, sporocilo: "Prijava uspešna!"});
                }
            }
        });
    }

    public registrirajUporabnika(req: Request, res: Response, next: NextFunction) {
        const b = req.body;

        const prazneVrednostiRezultat: [boolean, PrijavaException] = Uporabnik.preveriPrazneVrednosti(b);
        if (prazneVrednostiRezultat[0]) {
            resp.returnJSONres(res, 400, {
                sporocilo: prazneVrednostiRezultat[1].sporocilo,
                status: prazneVrednostiRezultat[1].status,
            });
            return;
        }

        if (b.geslo1 !== b.geslo2) {
            const napaka = new PrijavaException({
                message: "Gesli se ne ujemata!",
                name: "Password mismatch",
                stack: null,
            }, "Gesli se ne ujemata", 400);
            resp.returnJSONres(res, 400, {status: napaka.status, sporocilo: napaka.sporocilo});
            return;
        }

        const avtentikacijaService: Avtentikacija = new Avtentikacija();

        const uporabnik: Uporabnik = new Uporabnik(b.uporabniskoIme, b.ime, b.priimek, b.email,
            b.geslo1, parseInt(b.letnik, 10));

        avtentikacijaService.registrirajUporabnika(uporabnik, (napaka: PrijavaException, uspeh: boolean) => {
            if (napaka) {
                if (napaka.status === 424) {
                    resp.returnJSONres(res, 424, {sporocilo: "Storitev uporabnikov trenutno ni na voljo!"});
                } else {
                    resp.returnJSONres(res, napaka.status, {napaka: napaka.sporocilo});
                }
            } else if (uspeh) {
                resp.returnJSONres(res, 201, {sporocilo: "OK"});
            } else {
                resp.returnJSONres(res, napaka.status, {napaka: napaka.sporocilo});
            }
        });
    }

}

const indexRoutes = new IndexRouter().router;

export default indexRoutes;
