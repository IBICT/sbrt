import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILegalEntity } from 'app/shared/model/legal-entity.model';
import { LegalEntityService } from './legal-entity.service';

@Component({
    selector: 'jhi-legal-entity-delete-dialog',
    templateUrl: './legal-entity-delete-dialog.component.html'
})
export class LegalEntityDeleteDialogComponent {
    legalEntity: ILegalEntity;

    constructor(
        protected legalEntityService: LegalEntityService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.legalEntityService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'legalEntityListModification',
                content: 'Deleted an legalEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-legal-entity-delete-popup',
    template: ''
})
export class LegalEntityDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ legalEntity }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LegalEntityDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.legalEntity = legalEntity;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/legal-entity', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/legal-entity', { outlets: { popup: null } }]);
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
