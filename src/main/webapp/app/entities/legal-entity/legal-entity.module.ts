import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SbrtSharedModule } from 'app/shared';
import {
    LegalEntityComponent,
    LegalEntityDetailComponent,
    LegalEntityUpdateComponent,
    LegalEntityDeletePopupComponent,
    LegalEntityDeleteDialogComponent,
    legalEntityRoute,
    legalEntityPopupRoute
} from './';

const ENTITY_STATES = [...legalEntityRoute, ...legalEntityPopupRoute];

@NgModule({
    imports: [SbrtSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LegalEntityComponent,
        LegalEntityDetailComponent,
        LegalEntityUpdateComponent,
        LegalEntityDeleteDialogComponent,
        LegalEntityDeletePopupComponent
    ],
    entryComponents: [LegalEntityComponent, LegalEntityUpdateComponent, LegalEntityDeleteDialogComponent, LegalEntityDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbrtLegalEntityModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
