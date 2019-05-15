/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SbrtTestModule } from '../../../test.module';
import { CnaeComponent } from 'app/entities/cnae/cnae.component';
import { CnaeService } from 'app/entities/cnae/cnae.service';
import { Cnae } from 'app/shared/model/cnae.model';

describe('Component Tests', () => {
    describe('Cnae Management Component', () => {
        let comp: CnaeComponent;
        let fixture: ComponentFixture<CnaeComponent>;
        let service: CnaeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SbrtTestModule],
                declarations: [CnaeComponent],
                providers: []
            })
                .overrideTemplate(CnaeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CnaeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CnaeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Cnae(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.cnaes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
