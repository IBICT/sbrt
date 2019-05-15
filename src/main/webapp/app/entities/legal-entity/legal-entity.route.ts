import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LegalEntity } from 'app/shared/model/legal-entity.model';
import { LegalEntityService } from './legal-entity.service';
import { LegalEntityComponent } from './legal-entity.component';
import { LegalEntityDetailComponent } from './legal-entity-detail.component';
import { LegalEntityUpdateComponent } from './legal-entity-update.component';
import { LegalEntityDeletePopupComponent } from './legal-entity-delete-dialog.component';
import { ILegalEntity } from 'app/shared/model/legal-entity.model';

@Injectable({ providedIn: 'root' })
export class LegalEntityResolve implements Resolve<ILegalEntity> {
    constructor(private service: LegalEntityService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILegalEntity> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<LegalEntity>) => response.ok),
                map((legalEntity: HttpResponse<LegalEntity>) => legalEntity.body)
            );
        }
        return of(new LegalEntity());
    }
}

export const legalEntityRoute: Routes = [
    {
        path: '',
        component: LegalEntityComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'sbrtApp.legalEntity.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LegalEntityDetailComponent,
        resolve: {
            legalEntity: LegalEntityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sbrtApp.legalEntity.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LegalEntityUpdateComponent,
        resolve: {
            legalEntity: LegalEntityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sbrtApp.legalEntity.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LegalEntityUpdateComponent,
        resolve: {
            legalEntity: LegalEntityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sbrtApp.legalEntity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const legalEntityPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: LegalEntityDeletePopupComponent,
        resolve: {
            legalEntity: LegalEntityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sbrtApp.legalEntity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
