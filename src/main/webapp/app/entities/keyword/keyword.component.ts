import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IKeyword } from 'app/shared/model/keyword.model';
import { AccountService } from 'app/core';
import { KeywordService } from './keyword.service';

@Component({
    selector: 'jhi-keyword',
    templateUrl: './keyword.component.html'
})
export class KeywordComponent implements OnInit, OnDestroy {
    keywords: IKeyword[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected keywordService: KeywordService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.keywordService
            .query()
            .pipe(
                filter((res: HttpResponse<IKeyword[]>) => res.ok),
                map((res: HttpResponse<IKeyword[]>) => res.body)
            )
            .subscribe(
                (res: IKeyword[]) => {
                    this.keywords = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInKeywords();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IKeyword) {
        return item.id;
    }

    registerChangeInKeywords() {
        this.eventSubscriber = this.eventManager.subscribe('keywordListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
