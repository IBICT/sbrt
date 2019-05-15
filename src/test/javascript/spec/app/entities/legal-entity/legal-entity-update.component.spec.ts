/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SbrtTestModule } from '../../../test.module';
import { LegalEntityUpdateComponent } from 'app/entities/legal-entity/legal-entity-update.component';
import { LegalEntityService } from 'app/entities/legal-entity/legal-entity.service';
import { LegalEntity } from 'app/shared/model/legal-entity.model';

describe('Component Tests', () => {
    describe('LegalEntity Management Update Component', () => {
        let comp: LegalEntityUpdateComponent;
        let fixture: ComponentFixture<LegalEntityUpdateComponent>;
        let service: LegalEntityService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SbrtTestModule],
                declarations: [LegalEntityUpdateComponent]
            })
                .overrideTemplate(LegalEntityUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LegalEntityUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LegalEntityService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new LegalEntity(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.legalEntity = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new LegalEntity();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.legalEntity = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
