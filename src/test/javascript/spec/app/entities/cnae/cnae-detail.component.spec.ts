/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SbrtTestModule } from '../../../test.module';
import { CnaeDetailComponent } from 'app/entities/cnae/cnae-detail.component';
import { Cnae } from 'app/shared/model/cnae.model';

describe('Component Tests', () => {
    describe('Cnae Management Detail Component', () => {
        let comp: CnaeDetailComponent;
        let fixture: ComponentFixture<CnaeDetailComponent>;
        const route = ({ data: of({ cnae: new Cnae(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SbrtTestModule],
                declarations: [CnaeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CnaeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CnaeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.cnae).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
