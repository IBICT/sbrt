import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ICnae } from 'app/shared/model/cnae.model';
import { CnaeService } from './cnae.service';

@Component({
    selector: 'jhi-cnae-update',
    templateUrl: './cnae-update.component.html'
})
export class CnaeUpdateComponent implements OnInit {
    cnae: ICnae;
    isSaving: boolean;

    constructor(protected cnaeService: CnaeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cnae }) => {
            this.cnae = cnae;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.cnae.id !== undefined) {
            this.subscribeToSaveResponse(this.cnaeService.update(this.cnae));
        } else {
            this.subscribeToSaveResponse(this.cnaeService.create(this.cnae));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICnae>>) {
        result.subscribe((res: HttpResponse<ICnae>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
