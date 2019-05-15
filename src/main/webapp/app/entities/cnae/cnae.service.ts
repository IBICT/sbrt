import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICnae } from 'app/shared/model/cnae.model';

type EntityResponseType = HttpResponse<ICnae>;
type EntityArrayResponseType = HttpResponse<ICnae[]>;

@Injectable({ providedIn: 'root' })
export class CnaeService {
    public resourceUrl = SERVER_API_URL + 'api/cnaes';

    constructor(protected http: HttpClient) {}

    create(cnae: ICnae): Observable<EntityResponseType> {
        return this.http.post<ICnae>(this.resourceUrl, cnae, { observe: 'response' });
    }

    update(cnae: ICnae): Observable<EntityResponseType> {
        return this.http.put<ICnae>(this.resourceUrl, cnae, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICnae>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICnae[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
