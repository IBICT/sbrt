import { IAnswer } from 'app/shared/model/answer.model';
import { IQuestion } from 'app/shared/model/question.model';
import { IPerson } from 'app/shared/model/person.model';

export interface ILegalEntity {
    id?: number;
    cnpj?: string;
    name?: string;
    address?: string;
    number?: number;
    complement?: string;
    city?: string;
    zipCode?: string;
    uf?: string;
    cnaeId?: number;
    answers?: IAnswer[];
    questions?: IQuestion[];
    persons?: IPerson[];
}

export class LegalEntity implements ILegalEntity {
    constructor(
        public id?: number,
        public cnpj?: string,
        public name?: string,
        public address?: string,
        public number?: number,
        public complement?: string,
        public city?: string,
        public zipCode?: string,
        public uf?: string,
        public cnaeId?: number,
        public answers?: IAnswer[],
        public questions?: IQuestion[],
        public persons?: IPerson[]
    ) {}
}
