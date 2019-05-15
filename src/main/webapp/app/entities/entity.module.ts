import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'legal-entity',
                loadChildren: './legal-entity/legal-entity.module#SbrtLegalEntityModule'
            },
            {
                path: 'contact',
                loadChildren: './contact/contact.module#SbrtContactModule'
            },
            {
                path: 'person',
                loadChildren: './person/person.module#SbrtPersonModule'
            },
            {
                path: 'question',
                loadChildren: './question/question.module#SbrtQuestionModule'
            },
            {
                path: 'answer',
                loadChildren: './answer/answer.module#SbrtAnswerModule'
            },
            {
                path: 'keyword',
                loadChildren: './keyword/keyword.module#SbrtKeywordModule'
            },
            {
                path: 'cnae',
                loadChildren: './cnae/cnae.module#SbrtCnaeModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbrtEntityModule {}
