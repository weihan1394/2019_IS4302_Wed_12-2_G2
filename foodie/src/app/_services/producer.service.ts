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
  crops: any[] = [
    { ID: '1', Name: 'Corn', Weight: '500', unit: 'g', Date: moment('23/3/2019', "DD-MM-YYYY").format("DD-MM-YYYY"), Time: moment('08:00', "hh:mm").format("hh:mm"), producer: "alice_linfs@foodie.com" },
    { ID: '2', Name: 'Carrot', Weight: '200', unit: 'g', Date: moment('20/3/2019', "DD-MM-YYYY").format("DD-MM-YYYY"), Time: moment('14:00', "hh:mm").format("hh:mm"), producer: "alice_linfs@foodie.com", finishedGood: '2' },
    { ID: '3', Name: 'Cabbage', Weight: '1000', unit: 'g', Date: moment('1/3/2019', "DD-MM-YYYY").format("DD-MM-YYYY"), Time: moment('15:00', "hh:mm").format("hh:mm"), producer: "3" },
    { ID: '4', Name: 'Lettuce', Weight: '800', unit: 'g', Date: moment('8/3/2019', "DD-MM-YYYY").format("DD-MM-YYYY"), Time: moment('12:00', "hh:mm").format("hh:mm"), producer: "4" },
    { ID: '5', Name: 'Potato', Weight: '300', unit: 'g', Date: moment('6/3/2019', "DD-MM-YYYY").format("DD-MM-YYYY"), Time: moment('09:00', "hh:mm").format("hh:mm"), producer: "5" }
  ];

  processed: any[] = [
    { ID: '1', packType: 'Box', processedDate: moment('24/3/2019', "DD-MM-YYYY").format("DD-MM-YYYY"), processedTime: moment('08:00', "hh:mm").format("hh:mm"), deliveryDate: null, deliveryTime: null, status: "Processed", weight: "300g", producer: "alice_linfs@foodie.com", farmer: "alice_kohft@foodie.com" },
    { ID: '2', packType: 'Box', processedDate: moment('22/3/2019', "DD-MM-YYYY").format("DD-MM-YYYY"), processedTime: moment('08:00', "hh:mm").format("hh:mm"), deliveryDate: null, deliveryTime: null, status: "Processed", weight: "200g", producer: "alice_linfs@foodie.com", farmer: "alice_kohft@foodie.com" }
  ]

  constructor(private httpClient: HttpClient) { }

  private handleError(error: HttpErrorResponse) {
    return throwError(error);
  }

  retrieveCrops(email: string) {
    return this.httpClient.get<any>(this.baseUrl + "retrieveCropsByProducer?email=" + email).pipe(
      tap(data => { console.log(data) }),
      catchError(this.handleError)
    )
  }

  retrieveProcessed(email: string): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "retrieveFinishedByProducer?email=" + email).pipe(
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
}
