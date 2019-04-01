import { User } from './../_model/user';
import { Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { DataService } from './../_services/data.service';
import { ProducerService } from './../_services/producer.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-producer',
  templateUrl: './producer.component.html',
  styleUrls: ['./producer.component.scss']
})
export class ProducerComponent implements OnInit {

  colsCrops: any[];
  colsProcessed: any[];
  crops: Array<any>;
  processed: Array<any>;
  constructor(private producerService: ProducerService, private formBuilder: FormBuilder, private router: Router,
    private dataService: DataService) { }

  ngOnInit() {
    let currentUser: User = JSON.parse(localStorage.getItem("currentUser"));
    this.dataService.changeTitle("View Crops & Processed Items");
    this.colsCrops = [
      { field: 'cropId', header: 'ID' },
      { field: 'name', header: 'Name' },
      { field: 'quantity', header: 'Weight' },
      { field: 'unitOfMeasurements', header: 'Unit' },
      { field: 'harvestedDate', header: 'Date' },
      { field: 'harvestedTime', header: 'Time' },
      { field: 'collects', header: 'Status' }
    ]
    this.colsProcessed = [
      { field: 'goodId', header: 'ID' },
      { field: 'packType', header: 'Pack Type' },
      { field: 'quantity', header: 'Weight' },
      { field: 'unitOfMeasurements', header: 'Unit' },
      { field: 'processedDate', header: 'Processed Date' },
      // { field: 'processedTime', header: 'Processed Time' },
      // { field: 'deliveryDate', header: 'Delivery Date' },
      // { field: 'deliveryTime', header: 'Delivery Time'},
      { field: 'status', header: 'Status' },
      { field: 'sourceOwner', header: 'Farmer' }
    ]

    this.producerService.retrieveCrops(currentUser.email).subscribe(
      res => {
        this.crops = res;
      }, err => {
        console.error(err);
      }
    );
    this.producerService.retrieveProcessed(currentUser.email).subscribe(
      res => {
        this.processed = res;
      }, err => {
        console.error(err);
      }
    );
  }

  onCropRowSelect(event) {
    let selectData = event.data;
    this.router.navigate(['producer/createBatch/' + selectData.cropId]);
    // console.log(selectData);
    // this.router.navigate(['farmer/editCrop', selectData.ID])
  }

  onProcessedRowSelect(event) {

  }

  switchPage(page) {
    this.router.navigate([page]);
  }
}
