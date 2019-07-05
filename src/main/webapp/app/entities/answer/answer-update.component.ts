import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAnswer } from 'app/shared/model/answer.model';
import { AnswerService } from './answer.service';
import { IUser, UserService } from 'app/core';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from 'app/entities/question';
import { ILegalEntity } from 'app/shared/model/legal-entity.model';
import { LegalEntityService } from 'app/entities/legal-entity';
import { ICnae } from 'app/shared/model/cnae.model';
import { CnaeService } from 'app/entities/cnae';

@Component({
    selector: 'jhi-answer-update',
    templateUrl: './answer-update.component.html'
})
export class AnswerUpdateComponent implements OnInit {
    answer: IAnswer;
    isSaving: boolean;

    users: IUser[];

    questions: IQuestion[];

    legalentities: ILegalEntity[];

    cnaes: ICnae[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected answerService: AnswerService,
        protected userService: UserService,
        protected questionService: QuestionService,
        protected legalEntityService: LegalEntityService,
        protected cnaeService: CnaeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ answer }) => {
            this.answer = answer;
        });
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.questionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IQuestion[]>) => mayBeOk.ok),
                map((response: HttpResponse<IQuestion[]>) => response.body)
            )
            .subscribe((res: IQuestion[]) => (this.questions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.legalEntityService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ILegalEntity[]>) => mayBeOk.ok),
                map((response: HttpResponse<ILegalEntity[]>) => response.body)
            )
            .subscribe((res: ILegalEntity[]) => (this.legalentities = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.cnaeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICnae[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICnae[]>) => response.body)
            )
            .subscribe((res: ICnae[]) => (this.cnaes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.answer.id !== undefined) {
            this.subscribeToSaveResponse(this.answerService.update(this.answer));
        } else {
            this.subscribeToSaveResponse(this.answerService.create(this.answer));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnswer>>) {
        result.subscribe((res: HttpResponse<IAnswer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackQuestionById(index: number, item: IQuestion) {
        return item.id;
    }

    trackLegalEntityById(index: number, item: ILegalEntity) {
        return item.id;
    }

    trackCnaeById(index: number, item: ICnae) {
        return item.id;
    }
}
