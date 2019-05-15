import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICnae } from 'app/shared/model/cnae.model';

@Component({
    selector: 'jhi-cnae-detail',
    templateUrl: './cnae-detail.component.html'
})
export class CnaeDetailComponent implements OnInit {
    cnae: ICnae;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cnae }) => {
            this.cnae = cnae;
        });
    }

    previousState() {
        window.history.back();
    }
}
