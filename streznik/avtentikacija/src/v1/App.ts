import * as bodyParser from "body-parser";
import * as cookieParser from "cookie-parser";
import * as express from "express";
import * as logger from "morgan";
import * as passport from "passport";
import * as path from "path";

import IndexRouter from "./routes/IndexRouter";

class App {

    public express: express.Application;

    constructor() {
        this.express = express();
        this.middleware();
        this.routes();
    }

    private middleware(): void {
        this.express.use(logger("dev"));
        this.express.use(bodyParser.json());
        this.express.use(bodyParser.urlencoded({extended: false}));
        this.express.use(cookieParser());

        // lovilci napak
        this.express.use(this.error401);
        this.express.use(this.error404);
        this.express.use(this.errorHandler);
    }

    private routes(): void {
        const router = express.Router();

        this.express.use("/v1/", IndexRouter);
    }

    private errorHandler(err: any, req: express.Request, res: express.Response, next: express.NextFunction) {
        console.error("handling error...");
        res.status(err.status || 500);
        if (req.app.get("env") === "development") {
            res.json({sporocilo: err.message, stack: err.stack});
        } else {
            res.json({sporocilo: err.message});
        }
    }

    private error404(err: Error, req: express.Request, res: express.Response, next: express.NextFunction) {
        const napaka = {
            message: err.message,
            name: err.name,
            stack: err.stack,
            status: 404,
        };
        next(napaka);
    }

    private error401(err: Error, req: express.Request, res: express.Response, next: express.NextFunction) {
        if (err.name === "UnauthorizedError") {
            res.status(401);
            res.json({sporocilo: `${err.name}: ${err.message}`});
        } else {
            next();
        }
    }

}

export default new App().express;
