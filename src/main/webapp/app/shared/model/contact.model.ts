export interface IContact {
    id?: number;
    phoneNumber?: string;
    email?: string;
    personId?: number;
}

export class Contact implements IContact {
    constructor(public id?: number, public phoneNumber?: string, public email?: string, public personId?: number) {}
}
