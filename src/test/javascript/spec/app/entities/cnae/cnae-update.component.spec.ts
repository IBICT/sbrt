/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SbrtTestModule } from '../../../test.module';
import { CnaeUpdateComponent } from 'app/entities/cnae/cnae-update.component';
import { CnaeService } from 'app/entities/cnae/cnae.service';
import { Cnae } from 'app/shared/model/cnae.model';

describe('Component Tests', () => {
    describe('Cnae Management Update Component', () => {
        let comp: CnaeUpdateComponent;
        let fixture: ComponentFixture<CnaeUpdateComponent>;
        let service: CnaeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SbrtTestModule],
                declarations: [CnaeUpdateComponent]
            })
                .overrideTemplate(CnaeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CnaeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CnaeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Cnae(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.cnae = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Cnae();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.cnae = entity;
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
