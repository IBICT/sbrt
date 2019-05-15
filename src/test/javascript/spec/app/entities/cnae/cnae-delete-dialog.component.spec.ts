/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SbrtTestModule } from '../../../test.module';
import { CnaeDeleteDialogComponent } from 'app/entities/cnae/cnae-delete-dialog.component';
import { CnaeService } from 'app/entities/cnae/cnae.service';

describe('Component Tests', () => {
    describe('Cnae Management Delete Component', () => {
        let comp: CnaeDeleteDialogComponent;
        let fixture: ComponentFixture<CnaeDeleteDialogComponent>;
        let service: CnaeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SbrtTestModule],
                declarations: [CnaeDeleteDialogComponent]
            })
                .overrideTemplate(CnaeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CnaeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CnaeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
