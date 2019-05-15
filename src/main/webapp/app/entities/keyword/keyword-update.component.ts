import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IKeyword } from 'app/shared/model/keyword.model';
import { KeywordService } from './keyword.service';
import { IAnswer } from 'app/shared/model/answer.model';
import { AnswerService } from 'app/entities/answer';

@Component({
    selector: 'jhi-keyword-update',
    templateUrl: './keyword-update.component.html'
})
export class KeywordUpdateComponent implements OnInit {
    keyword: IKeyword;
    isSaving: boolean;

    answers: IAnswer[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected keywordService: KeywordService,
        protected answerService: AnswerService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ keyword }) => {
            this.keyword = keyword;
        });
        this.answerService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAnswer[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAnswer[]>) => response.body)
            )
            .subscribe((res: IAnswer[]) => (this.answers = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.keyword.id !== undefined) {
            this.subscribeToSaveResponse(this.keywordService.update(this.keyword));
        } else {
            this.subscribeToSaveResponse(this.keywordService.create(this.keyword));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IKeyword>>) {
        result.subscribe((res: HttpResponse<IKeyword>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAnswerById(index: number, item: IAnswer) {
        return item.id;
    }
}
