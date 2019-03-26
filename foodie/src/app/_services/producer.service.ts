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

  crops: any[] = [
    { ID: '1', Name: 'Corn', Weight: '500g', Date: moment('23/3/2019', "DD-MM-YYYY").format("DD-MM-YYYY"), Time: moment('08:00', "hh:mm").format("hh:mm"), ProducerId: "alice@test.com" },
    { ID: '2', Name: 'Carrot', Weight: '200g', Date: moment('20/3/2019', "DD-MM-YYYY").format("DD-MM-YYYY"), Time: moment('14:00', "hh:mm").format("hh:mm"), ProducerId: "alice@test.com", finishedGood: '2' },
    { ID: '3', Name: 'Cabbage', Weight: '1000g', Date: moment('1/3/2019', "DD-MM-YYYY").format("DD-MM-YYYY"), Time: moment('15:00', "hh:mm").format("hh:mm"), ProducerId: "3" },
    { ID: '4', Name: 'Lettuce', Weight: '800g', Date: moment('8/3/2019', "DD-MM-YYYY").format("DD-MM-YYYY"), Time: moment('12:00', "hh:mm").format("hh:mm"), ProducerId: "4" },
    { ID: '5', Name: 'Potato', Weight: '300g', Date: moment('6/3/2019', "DD-MM-YYYY").format("DD-MM-YYYY"), Time: moment('09:00', "hh:mm").format("hh:mm"), ProducerId: "5" }
  ];

  processed: any[] = [
    { ID: '1', packType: 'Box', processedDate: moment('24/3/2019', "DD-MM-YYYY").format("DD-MM-YYYY"), processedTime: moment('08:00', "hh:mm").format("hh:mm"), deliveryDate: null, deliveryTime: null, status: "Processed", weight: "300g", producerId: "alice@test.com", farmerId: "alice@test.com" },
    { ID: '2', packType: 'Box', processedDate: moment('22/3/2019', "DD-MM-YYYY").format("DD-MM-YYYY"), processedTime: moment('08:00', "hh:mm").format("hh:mm"), deliveryDate: null, deliveryTime: null, status: "Processed", weight: "200g", producerId: "alice@test.com", farmerId: "alice@test.com" }
  ]

  constructor(private httpClient: HttpClient) { }

  private handleError(error: HttpErrorResponse) {
    return throwError(error);
  }

  retrieveCrops(producerId: string) {
    let myCrops = [];
    for (let i = 0; i < this.crops.length; i++) {
      if (this.crops[i].ProducerId == producerId) {
        myCrops.push(this.crops[i]);
      }
    }
    return myCrops;
  }

  retrieveProcessed(producerId: string) {
    let myProcessed = [];
    for (let i = 0; i < this.processed.length; i++) {
      if (this.processed[i].producerId == producerId) {
        myProcessed.push(this.processed[i]);
      }
    }
    return myProcessed;
  }
}
