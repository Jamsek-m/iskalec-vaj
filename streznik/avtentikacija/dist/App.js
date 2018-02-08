"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const bodyParser = require("body-parser");
const cookieParser = require("cookie-parser");
const express = require("express");
const logger = require("morgan");
const IndexRouter_1 = require("./routes/IndexRouter");
class App {
    constructor() {
        this.express = express();
        this.middleware();
        this.routes();
    }
    middleware() {
        this.express.use(logger("dev"));
        this.express.use(bodyParser.json());
        this.express.use(bodyParser.urlencoded({ extended: false }));
        this.express.use(cookieParser());
        // lovilci napak
        this.express.use(this.error401);
        this.express.use(this.error404);
        this.express.use(this.errorHandler);
    }
    routes() {
        const router = express.Router();
        this.express.use("/v1/", IndexRouter_1.default);
    }
    errorHandler(err, req, res, next) {
        console.error("handling error...");
        res.status(err.status || 500);
        if (req.app.get("env") === "development") {
            res.json({ sporocilo: err.message, stack: err.stack });
        }
        else {
            res.json({ sporocilo: err.message });
        }
    }
    error404(err, req, res, next) {
        const napaka = {
            message: err.message,
            name: err.name,
            stack: err.stack,
            status: 404,
        };
        next(napaka);
    }
    error401(err, req, res, next) {
        if (err.name === "UnauthorizedError") {
            res.status(401);
            res.json({ sporocilo: `${err.name}: ${err.message}` });
        }
        else {
            next();
        }
    }
}
exports.default = new App().express;
