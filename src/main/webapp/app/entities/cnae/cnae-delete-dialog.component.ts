import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICnae } from 'app/shared/model/cnae.model';
import { CnaeService } from './cnae.service';

@Component({
    selector: 'jhi-cnae-delete-dialog',
    templateUrl: './cnae-delete-dialog.component.html'
})
export class CnaeDeleteDialogComponent {
    cnae: ICnae;

    constructor(protected cnaeService: CnaeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cnaeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'cnaeListModification',
                content: 'Deleted an cnae'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cnae-delete-popup',
    template: ''
})
export class CnaeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cnae }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CnaeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.cnae = cnae;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/cnae', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/cnae', { outlets: { popup: null } }]);
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
