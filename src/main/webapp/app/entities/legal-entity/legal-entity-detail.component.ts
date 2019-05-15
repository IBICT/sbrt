import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILegalEntity } from 'app/shared/model/legal-entity.model';

@Component({
    selector: 'jhi-legal-entity-detail',
    templateUrl: './legal-entity-detail.component.html'
})
export class LegalEntityDetailComponent implements OnInit {
    legalEntity: ILegalEntity;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ legalEntity }) => {
            this.legalEntity = legalEntity;
        });
    }

    previousState() {
        window.history.back();
    }
}
