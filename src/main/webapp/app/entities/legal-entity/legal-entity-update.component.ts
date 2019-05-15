import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ILegalEntity } from 'app/shared/model/legal-entity.model';
import { LegalEntityService } from './legal-entity.service';
import { ICnae } from 'app/shared/model/cnae.model';
import { CnaeService } from 'app/entities/cnae';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person';

@Component({
    selector: 'jhi-legal-entity-update',
    templateUrl: './legal-entity-update.component.html'
})
export class LegalEntityUpdateComponent implements OnInit {
    legalEntity: ILegalEntity;
    isSaving: boolean;

    cnaes: ICnae[];

    people: IPerson[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected legalEntityService: LegalEntityService,
        protected cnaeService: CnaeService,
        protected personService: PersonService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ legalEntity }) => {
            this.legalEntity = legalEntity;
        });
        this.cnaeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICnae[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICnae[]>) => response.body)
            )
            .subscribe((res: ICnae[]) => (this.cnaes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.personService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPerson[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPerson[]>) => response.body)
            )
            .subscribe((res: IPerson[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.legalEntity.id !== undefined) {
            this.subscribeToSaveResponse(this.legalEntityService.update(this.legalEntity));
        } else {
            this.subscribeToSaveResponse(this.legalEntityService.create(this.legalEntity));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILegalEntity>>) {
        result.subscribe((res: HttpResponse<ILegalEntity>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCnaeById(index: number, item: ICnae) {
        return item.id;
    }

    trackPersonById(index: number, item: IPerson) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
