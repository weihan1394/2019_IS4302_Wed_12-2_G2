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
  constructor(private producerService:ProducerService, private formBuilder: FormBuilder, private router: Router, 
    private dataService:DataService) { }

  ngOnInit() {
    let currentUser:User = JSON.parse(localStorage.getItem("currentUser"));
    this.dataService.changeTitle("View Crops & Processed Items");
    this.colsCrops = [
      { field: 'ID', header: 'ID' },
      { field: 'Name', header: 'Name' },
      { field: 'Weight', header: 'Weight' },
      { field: 'Date', header: 'Date' },
      { field: 'Time', header: 'Time' },
      { field: 'finishedGood', header: 'Processed Id' }
    ]
    this.colsProcessed = [
      { field: 'ID', header: 'ID' },
      { field: 'packType', header: 'Pack Type' },
      { field: 'processedDate', header: 'Processed Date' },
      { field: 'processedTime', header: 'Processed Time' },
      { field: 'deliveryDate', header: 'Delivery Date' },
      { field: 'deliveryTime', header: 'Delivery Time'},
      { field: 'status', header: 'Status'},
      { field: 'weight', header: 'Weight'},
      { field: 'farmerId', header: 'Farmer Id' }
    ]

    this.crops = this.producerService.retrieveCrops(currentUser.email);
    this.processed = this.producerService.retrieveProcessed(currentUser.email);
  }

  onCropRowSelect(event) {
    let selectData = event.data;
    this.router.navigate(['producer/createBatch/' + selectData.ID]);
    // console.log(selectData);
    // this.router.navigate(['farmer/editCrop', selectData.ID])
  }

  onProcessedRowSelect(event) {

  }

  switchPage(page) {
    this.router.navigate([page]);
  }
}
