import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SbrtSharedModule } from 'app/shared';
import {
    CnaeComponent,
    CnaeDetailComponent,
    CnaeUpdateComponent,
    CnaeDeletePopupComponent,
    CnaeDeleteDialogComponent,
    cnaeRoute,
    cnaePopupRoute
} from './';

const ENTITY_STATES = [...cnaeRoute, ...cnaePopupRoute];

@NgModule({
    imports: [SbrtSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [CnaeComponent, CnaeDetailComponent, CnaeUpdateComponent, CnaeDeleteDialogComponent, CnaeDeletePopupComponent],
    entryComponents: [CnaeComponent, CnaeUpdateComponent, CnaeDeleteDialogComponent, CnaeDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbrtCnaeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
