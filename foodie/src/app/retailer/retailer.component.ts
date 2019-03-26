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
  foods: Array<any>;
  constructor(private dataService:DataService, private retailerService:RetailerService, private router:Router) { }

  ngOnInit() {
    this.dataService.changeTitle("View Items");

    this.cols = [
      { field: 'ID', header: 'ID' },
      { field: 'Name', header: 'Name' },
      { field: 'Weight', header: 'Weight' },
      { field: 'Date', header: 'Date' },
      { field: 'Time', header: 'Time' }
    ]

    this.foods = this.retailerService.retrieveFoods();
  }

  onRowSelect(event) {
    let selectData = event.data;
    console.log(selectData);
    // this.router.navigate(['farmer/editCrop', selectData.ID])
  }
}
