import { Moment } from 'moment';
import { IKeyword } from 'app/shared/model/keyword.model';

export interface IAnswer {
    id?: number;
    title?: string;
    description?: string;
    datePublished?: Moment;
    content?: any;
    timesSeen?: number;
    userId?: number;
    questionId?: number;
    legalEntityId?: number;
    cnaeId?: number;
    keywords?: IKeyword[];
}

export class Answer implements IAnswer {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public datePublished?: Moment,
        public content?: any,
        public timesSeen?: number,
        public userId?: number,
        public questionId?: number,
        public legalEntityId?: number,
        public cnaeId?: number,
        public keywords?: IKeyword[]
    ) {}
}
