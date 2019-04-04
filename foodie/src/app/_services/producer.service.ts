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
export class ProducerService {
  baseUrl = "/api/producer/";
  
  constructor(private httpClient: HttpClient) { }

  private handleError(error: HttpErrorResponse) {
    return throwError(error);
  }

  retrieveCrops() {
    return this.httpClient.get<any>(this.baseUrl + "retrieveCropsByProducer").pipe(
      tap(data => { console.log(data) }),
      catchError(this.handleError)
    )
  }

  retrieveProcessed(): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "retrieveFinishedByProducer").pipe(
      tap(data => { console.log(data) }),
      catchError(this.handleError)
    )
  }

  retrieveCropById(cropId) {
    return this.httpClient.get<any>(this.baseUrl + "retrieveCropById?cropId=" + cropId).pipe(
      tap(data => { console.log(data) }),
      catchError(this.handleError)
    )
  }

  createBatches(batchesArr, cropId) {
    let batchesJSON = { "batches": batchesArr, "cropId": cropId };
    console.log(batchesJSON)
    return this.httpClient.post<any>(this.baseUrl + "createBatches", batchesJSON, httpOptions).pipe(
      tap(data => console.log(data)),
      catchError(this.handleError)
    )
  }

  collectCrop(cropId) {
    return this.httpClient.get<any>(this.baseUrl + "collectCrop?cropId=" + cropId).pipe(
      tap(data => { console.log(data) }),
      catchError(this.handleError)
    )
  }

  retrieveAllDistributors() {
    return this.httpClient.get<any>(this.baseUrl + "retrieveAllDistributors").pipe(
      tap(data => { console.log(data) }),
      catchError(this.handleError)
    )
  }

  retrieveAllRetailers() {
    return this.httpClient.get<any>(this.baseUrl + "retrieveAllRetailers").pipe(
      tap(data => { console.log(data) }),
      catchError(this.handleError)
    )
  }
}
