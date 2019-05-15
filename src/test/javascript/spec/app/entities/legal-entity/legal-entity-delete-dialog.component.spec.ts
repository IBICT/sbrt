/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SbrtTestModule } from '../../../test.module';
import { LegalEntityDeleteDialogComponent } from 'app/entities/legal-entity/legal-entity-delete-dialog.component';
import { LegalEntityService } from 'app/entities/legal-entity/legal-entity.service';

describe('Component Tests', () => {
    describe('LegalEntity Management Delete Component', () => {
        let comp: LegalEntityDeleteDialogComponent;
        let fixture: ComponentFixture<LegalEntityDeleteDialogComponent>;
        let service: LegalEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SbrtTestModule],
                declarations: [LegalEntityDeleteDialogComponent]
            })
                .overrideTemplate(LegalEntityDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LegalEntityDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LegalEntityService);
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
