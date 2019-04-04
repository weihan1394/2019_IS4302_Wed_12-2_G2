import { tap, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import * as moment from 'moment';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class DistributorService {
  baseUrl = "/api/distributor/";

  constructor(private httpClient: HttpClient) { }

  retrieveGoods(): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "retrieveGoods").pipe(
      tap(data => { console.log(data) }),
      catchError(this.handleError)
    )
  }

  collectBatch(batchId): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "collectBatch?batchId=" + batchId).pipe(
      tap(data => { console.log(data) }),
      catchError(this.handleError)
    )
  }

  private handleError(error: HttpErrorResponse) {
    return throwError(error);
  }
}
