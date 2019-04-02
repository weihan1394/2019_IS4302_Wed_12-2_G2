import { throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import * as moment from 'moment';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class FarmerService {
  baseUrl = "/api/farmer/"

  constructor(private httpClient:HttpClient) { }

  retrieveCrops() {
    let farmer = JSON.parse(localStorage.getItem('currentUser'));
    return this.httpClient.get<any>(this.baseUrl + "retrieveCropsByFarmer?email=" + farmer.email).pipe(
      tap(data => console.log(data)),
      catchError(this.handleError)
    )
  }

  retrieveCropsById(id) {
  }

  createCrop(name:string, weight:number, unit: string, producer:string): Observable<any> {
    let data = {"name": name, "quantity": weight, "unitOfMeasurements": unit, "producer": producer};
    return this.httpClient.post<any>(this.baseUrl + "createCrop", data, httpOptions).pipe(
      catchError(this.handleError)
    )
  }

  retrieveAllProducers() {
    return this.httpClient.get<any>(this.baseUrl + "retrieveAllProducers").pipe(
      tap(data => {console.log(data)}),
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    console.error(error)
    return throwError(error);
  }
}
