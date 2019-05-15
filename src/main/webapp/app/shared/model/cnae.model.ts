import { IAnswer } from 'app/shared/model/answer.model';
import { ILegalEntity } from 'app/shared/model/legal-entity.model';

export interface ICnae {
    id?: number;
    level?: number;
    cod?: string;
    description?: string;
    answers?: IAnswer[];
    legalEntities?: ILegalEntity[];
}

export class Cnae implements ICnae {
    constructor(
        public id?: number,
        public level?: number,
        public cod?: string,
        public description?: string,
        public answers?: IAnswer[],
        public legalEntities?: ILegalEntity[]
    ) {}
}
