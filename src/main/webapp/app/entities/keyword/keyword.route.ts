import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Keyword } from 'app/shared/model/keyword.model';
import { KeywordService } from './keyword.service';
import { KeywordComponent } from './keyword.component';
import { KeywordDetailComponent } from './keyword-detail.component';
import { KeywordUpdateComponent } from './keyword-update.component';
import { KeywordDeletePopupComponent } from './keyword-delete-dialog.component';
import { IKeyword } from 'app/shared/model/keyword.model';

@Injectable({ providedIn: 'root' })
export class KeywordResolve implements Resolve<IKeyword> {
    constructor(private service: KeywordService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IKeyword> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Keyword>) => response.ok),
                map((keyword: HttpResponse<Keyword>) => keyword.body)
            );
        }
        return of(new Keyword());
    }
}

export const keywordRoute: Routes = [
    {
        path: '',
        component: KeywordComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sbrtApp.keyword.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: KeywordDetailComponent,
        resolve: {
            keyword: KeywordResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sbrtApp.keyword.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: KeywordUpdateComponent,
        resolve: {
            keyword: KeywordResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sbrtApp.keyword.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: KeywordUpdateComponent,
        resolve: {
            keyword: KeywordResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sbrtApp.keyword.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const keywordPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: KeywordDeletePopupComponent,
        resolve: {
            keyword: KeywordResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sbrtApp.keyword.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
