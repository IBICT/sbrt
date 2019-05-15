import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Cnae } from 'app/shared/model/cnae.model';
import { CnaeService } from './cnae.service';
import { CnaeComponent } from './cnae.component';
import { CnaeDetailComponent } from './cnae-detail.component';
import { CnaeUpdateComponent } from './cnae-update.component';
import { CnaeDeletePopupComponent } from './cnae-delete-dialog.component';
import { ICnae } from 'app/shared/model/cnae.model';

@Injectable({ providedIn: 'root' })
export class CnaeResolve implements Resolve<ICnae> {
    constructor(private service: CnaeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICnae> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Cnae>) => response.ok),
                map((cnae: HttpResponse<Cnae>) => cnae.body)
            );
        }
        return of(new Cnae());
    }
}

export const cnaeRoute: Routes = [
    {
        path: '',
        component: CnaeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sbrtApp.cnae.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CnaeDetailComponent,
        resolve: {
            cnae: CnaeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sbrtApp.cnae.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CnaeUpdateComponent,
        resolve: {
            cnae: CnaeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sbrtApp.cnae.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CnaeUpdateComponent,
        resolve: {
            cnae: CnaeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sbrtApp.cnae.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cnaePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CnaeDeletePopupComponent,
        resolve: {
            cnae: CnaeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sbrtApp.cnae.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
