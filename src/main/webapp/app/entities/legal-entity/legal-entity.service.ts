import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILegalEntity } from 'app/shared/model/legal-entity.model';

type EntityResponseType = HttpResponse<ILegalEntity>;
type EntityArrayResponseType = HttpResponse<ILegalEntity[]>;

@Injectable({ providedIn: 'root' })
export class LegalEntityService {
    public resourceUrl = SERVER_API_URL + 'api/legal-entities';

    constructor(protected http: HttpClient) {}

    create(legalEntity: ILegalEntity): Observable<EntityResponseType> {
        return this.http.post<ILegalEntity>(this.resourceUrl, legalEntity, { observe: 'response' });
    }

    update(legalEntity: ILegalEntity): Observable<EntityResponseType> {
        return this.http.put<ILegalEntity>(this.resourceUrl, legalEntity, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILegalEntity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILegalEntity[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
