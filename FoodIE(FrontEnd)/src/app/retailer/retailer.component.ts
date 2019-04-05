import { ConfirmationService, MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { RetailerService } from './../_services/retailer.service';
import { DataService } from './../_services/data.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-retailer',
  templateUrl: './retailer.component.html',
  styleUrls: ['./retailer.component.scss']
})
export class RetailerComponent implements OnInit {

  cols: any[];
  goods: Array<any>;

  constructor(private retailerService: RetailerService, private router: Router, private dataService: DataService) { }

  ngOnInit() {
    this.dataService.changeTitle("View Goods");
    this.cols = [
      { field: 'goodId', header: 'ID' },
      { field: 'packType', header: 'Pack Type' },
      { field: 'quantity', header: 'Weight' },
      { field: 'unitOfMeasurements', header: 'Unit' },
      { field: 'processedDate', header: 'Processed Date' },
      // { field: 'processedTime', header: 'Processed Time' },
      // { field: 'deliveryDate', header: 'Delivery Date' },
      // { field: 'deliveryTime', header: 'Delivery Time'},
      { field: 'status', header: 'Status' },
      { field: 'distributor', header: 'Distributor' }
    ]
    this.retailerService.retrieveGoods().subscribe(
      res => {
        this.goods = res;
      }, err => {
        console.error(err);
      }
    );
  }

  onRowSelect(event) {
    let selectData = event.data;
    this.router.navigate(['/retailer/viewBatchDetails/' + selectData.goodId])

  }
}
