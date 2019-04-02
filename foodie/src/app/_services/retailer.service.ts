import { tap, catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import * as moment from 'moment';
@Injectable({
  providedIn: 'root'
})
export class RetailerService {
  baseUrl = "/api/retailer/";

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

  retrieveBatchById(batchId): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "retrieveBatchById?batchId=" + batchId).pipe(
      tap(data => { console.log(data) }),
      catchError(this.handleError)
    )
  }

  private handleError(error: HttpErrorResponse) {
    return throwError(error);
  }
}
