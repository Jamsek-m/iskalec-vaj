"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
function returnJSONres(res, status, data) {
    res.status(status);
    res.json(data);
}
exports.returnJSONres = returnJSONres;
