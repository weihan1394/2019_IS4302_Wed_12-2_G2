import { Router } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DataService } from './../_services/data.service';
import { DistributorService } from './../_services/distributor.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-distributor',
  templateUrl: './distributor.component.html',
  styleUrls: ['./distributor.component.scss']
})
export class DistributorComponent implements OnInit {

  cols: any[];
  deliveryGoods: Array<any>;

  constructor(private distributorService: DistributorService, private messageService: MessageService, private router: Router,
    private dataService: DataService, private confirmationService: ConfirmationService) { }

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
      { field: 'retailer', header: 'Retailer' }
    ]
    this.distributorService.retrieveGoods().subscribe(
      res => {
        this.deliveryGoods = res;
      }, err => {
        console.error(err);
      }
    );
  }

  onRowSelect(event) {
    let selectData = event.data;
    this.confirmationService.confirm({
      message: 'Do you want to change the status to DELIVERING?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.distributorService.collectBatch(selectData.goodId).subscribe(
          res => {
            for (let g of this.deliveryGoods) {
              if (g.goodId == selectData.goodId) {
                g.status = "DELIVERING"
                break;
              }
              this.messageService.add({ severity: 'success', summary: 'Success', detail: "Batch status changed!" });
            }
          }, err => {
            this.messageService.add({ severity: 'error', summary: 'Error', detail: err });
          }
        )
      },
      reject: () => {
      }
    });
  }

}
