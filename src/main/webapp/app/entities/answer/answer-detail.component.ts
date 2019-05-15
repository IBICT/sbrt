import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAnswer } from 'app/shared/model/answer.model';

@Component({
    selector: 'jhi-answer-detail',
    templateUrl: './answer-detail.component.html'
})
export class AnswerDetailComponent implements OnInit {
    answer: IAnswer;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ answer }) => {
            this.answer = answer;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
