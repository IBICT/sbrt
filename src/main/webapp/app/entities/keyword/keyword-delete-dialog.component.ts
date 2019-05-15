import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKeyword } from 'app/shared/model/keyword.model';
import { KeywordService } from './keyword.service';

@Component({
    selector: 'jhi-keyword-delete-dialog',
    templateUrl: './keyword-delete-dialog.component.html'
})
export class KeywordDeleteDialogComponent {
    keyword: IKeyword;

    constructor(protected keywordService: KeywordService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.keywordService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'keywordListModification',
                content: 'Deleted an keyword'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-keyword-delete-popup',
    template: ''
})
export class KeywordDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ keyword }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(KeywordDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.keyword = keyword;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/keyword', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/keyword', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
