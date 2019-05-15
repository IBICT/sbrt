import { Moment } from 'moment';
import { IAnswer } from 'app/shared/model/answer.model';

export interface IQuestion {
    id?: number;
    title?: string;
    description?: string;
    dateAsked?: Moment;
    userId?: number;
    legalEntityId?: number;
    answers?: IAnswer[];
}

export class Question implements IQuestion {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public dateAsked?: Moment,
        public userId?: number,
        public legalEntityId?: number,
        public answers?: IAnswer[]
    ) {}
}
