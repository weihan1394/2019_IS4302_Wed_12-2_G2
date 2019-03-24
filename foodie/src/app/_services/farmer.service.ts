import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import * as moment from 'moment';

@Injectable({
  providedIn: 'root'
})
export class FarmerService {

  crops: any[] = [
    { ID: '1', Name: 'Corn', Weight: '500g', Date: moment('23/3/2019', "DD-MM-YYYY").format("DD-MM-YYYY"), Time: moment('08:00', "hh:mm").format("hh:mm")},
    { ID: '2', Name: 'Carrot', Weight: '200g', Date: moment('20/3/2019', "DD-MM-YYYY").format("DD-MM-YYYY"), Time: moment('14:00', "hh:mm").format("hh:mm") },
    { ID: '3', Name: 'Cabbage', Weight: '1000g', Date: moment('1/3/2019', "DD-MM-YYYY").format("DD-MM-YYYY"), Time: moment('15:00', "hh:mm").format("hh:mm") },
    { ID: '4', Name: 'Lettuce', Weight: '800g', Date: moment('8/3/2019', "DD-MM-YYYY").format("DD-MM-YYYY"), Time: moment('12:00', "hh:mm").format("hh:mm") },
    { ID: '5', Name: 'Potato', Weight: '300g', Date: moment('6/3/2019', "DD-MM-YYYY").format("DD-MM-YYYY"), Time: moment('09:00', "hh:mm").format("hh:mm") }
];

  constructor() { }

  retrieveCrops() {
    return this.crops;
  }

  retrieveCropsById(id) {
    let crop;
    for(let i = 0; i < this.crops.length; i++) {
      let cropObj = this.crops[i];
      if(cropObj.ID == id) {
        return cropObj;
      }
    }
    return null;
  }
}
