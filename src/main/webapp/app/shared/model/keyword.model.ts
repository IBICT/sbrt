export interface IKeyword {
    id?: number;
    word?: string;
    answerId?: number;
}

export class Keyword implements IKeyword {
    constructor(public id?: number, public word?: string, public answerId?: number) {}
}
