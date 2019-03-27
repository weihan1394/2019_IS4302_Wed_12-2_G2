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

  constructor(private httpClient: HttpClient) { }

  private handleError(error: HttpErrorResponse) {
    return throwError(error);
  }
}
