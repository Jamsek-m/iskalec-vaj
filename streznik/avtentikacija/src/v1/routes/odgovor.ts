export function returnJSONres(res, status, data) {
    res.status(status);
    res.json(data);
}
