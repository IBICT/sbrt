import { ILegalEntity } from 'app/shared/model/legal-entity.model';
import { IContact } from 'app/shared/model/contact.model';

export const enum Schooling {
    FUNDAMENTAL_INCOMPLETO = 'FUNDAMENTAL_INCOMPLETO',
    FUNDAMENTAL_COMPLETO = 'FUNDAMENTAL_COMPLETO',
    MEDIO_INCOMPLETO = 'MEDIO_INCOMPLETO',
    MEDIO_COMPLETO = 'MEDIO_COMPLETO',
    SUPERIOR_INCOMPLETO = 'SUPERIOR_INCOMPLETO',
    SUPERIOR_COMPLETO = 'SUPERIOR_COMPLETO'
}

export interface IPerson {
    id?: number;
    name?: string;
    cpf?: string;
    schooling?: Schooling;
    gender?: number;
    city?: string;
    address?: string;
    number?: number;
    complement?: string;
    zipCode?: string;
    uf?: string;
    legalEntities?: ILegalEntity[];
    contacts?: IContact[];
}

export class Person implements IPerson {
    constructor(
        public id?: number,
        public name?: string,
        public cpf?: string,
        public schooling?: Schooling,
        public gender?: number,
        public city?: string,
        public address?: string,
        public number?: number,
        public complement?: string,
        public zipCode?: string,
        public uf?: string,
        public legalEntities?: ILegalEntity[],
        public contacts?: IContact[]
    ) {}
}
