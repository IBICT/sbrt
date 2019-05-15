import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICnae } from 'app/shared/model/cnae.model';
import { AccountService } from 'app/core';
import { CnaeService } from './cnae.service';

@Component({
    selector: 'jhi-cnae',
    templateUrl: './cnae.component.html'
})
export class CnaeComponent implements OnInit, OnDestroy {
    cnaes: ICnae[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected cnaeService: CnaeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.cnaeService
            .query()
            .pipe(
                filter((res: HttpResponse<ICnae[]>) => res.ok),
                map((res: HttpResponse<ICnae[]>) => res.body)
            )
            .subscribe(
                (res: ICnae[]) => {
                    this.cnaes = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCnaes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICnae) {
        return item.id;
    }

    registerChangeInCnaes() {
        this.eventSubscriber = this.eventManager.subscribe('cnaeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
