import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from './question.service';
import { IUser, UserService } from 'app/core';
import { ILegalEntity } from 'app/shared/model/legal-entity.model';
import { LegalEntityService } from 'app/entities/legal-entity';

@Component({
    selector: 'jhi-question-update',
    templateUrl: './question-update.component.html'
})
export class QuestionUpdateComponent implements OnInit {
    question: IQuestion;
    isSaving: boolean;

    users: IUser[];

    legalentities: ILegalEntity[];
    dateAsked: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected questionService: QuestionService,
        protected userService: UserService,
        protected legalEntityService: LegalEntityService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ question }) => {
            this.question = question;
            this.dateAsked = this.question.dateAsked != null ? this.question.dateAsked.format(DATE_TIME_FORMAT) : null;
        });
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.legalEntityService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ILegalEntity[]>) => mayBeOk.ok),
                map((response: HttpResponse<ILegalEntity[]>) => response.body)
            )
            .subscribe((res: ILegalEntity[]) => (this.legalentities = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.question.dateAsked = this.dateAsked != null ? moment(this.dateAsked, DATE_TIME_FORMAT) : null;
        if (this.question.id !== undefined) {
            this.subscribeToSaveResponse(this.questionService.update(this.question));
        } else {
            this.subscribeToSaveResponse(this.questionService.create(this.question));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestion>>) {
        result.subscribe((res: HttpResponse<IQuestion>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackLegalEntityById(index: number, item: ILegalEntity) {
        return item.id;
    }
}
