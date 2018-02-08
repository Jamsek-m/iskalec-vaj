/* tslint:disable max-classes-per-file */
class Napaka {
    public message: string;
    public name: string;
    public stack: any;
}

export class PrijavaException {
    public napaka: Napaka;
    public sporocilo: string;
    public status: number;

    constructor(napaka: Napaka, sporocilo: string, status: number) {
        this.napaka = napaka;
        this.sporocilo = sporocilo;
        this.status = status;
    }

}
