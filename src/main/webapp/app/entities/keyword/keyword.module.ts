import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SbrtSharedModule } from 'app/shared';
import {
    KeywordComponent,
    KeywordDetailComponent,
    KeywordUpdateComponent,
    KeywordDeletePopupComponent,
    KeywordDeleteDialogComponent,
    keywordRoute,
    keywordPopupRoute
} from './';

const ENTITY_STATES = [...keywordRoute, ...keywordPopupRoute];

@NgModule({
    imports: [SbrtSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        KeywordComponent,
        KeywordDetailComponent,
        KeywordUpdateComponent,
        KeywordDeleteDialogComponent,
        KeywordDeletePopupComponent
    ],
    entryComponents: [KeywordComponent, KeywordUpdateComponent, KeywordDeleteDialogComponent, KeywordDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbrtKeywordModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
