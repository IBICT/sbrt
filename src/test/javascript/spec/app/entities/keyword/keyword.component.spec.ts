/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SbrtTestModule } from '../../../test.module';
import { KeywordComponent } from 'app/entities/keyword/keyword.component';
import { KeywordService } from 'app/entities/keyword/keyword.service';
import { Keyword } from 'app/shared/model/keyword.model';

describe('Component Tests', () => {
    describe('Keyword Management Component', () => {
        let comp: KeywordComponent;
        let fixture: ComponentFixture<KeywordComponent>;
        let service: KeywordService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SbrtTestModule],
                declarations: [KeywordComponent],
                providers: []
            })
                .overrideTemplate(KeywordComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(KeywordComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KeywordService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Keyword(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.keywords[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
