import { DataService } from './../_services/data.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { FarmerService } from './../_services/farmer.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-farmer',
  templateUrl: './farmer.component.html',
  styleUrls: ['./farmer.component.scss']
})
export class FarmerComponent implements OnInit {

  cols: any[];
  crops: Array<any>;
  constructor(private farmerService: FarmerService, private formBuilder: FormBuilder, private router: Router,
    private dataService: DataService) { }

  ngOnInit() {
    // this.farmerService.retrieveAllProducers().subscribe(
    //   res => {
    //     console.log(res);
    //   }, err => {
    //     console.error(err);
    //   }
    // );
    this.dataService.changeTitle("View Crops");
    this.cols = [
      { field: 'cropId', header: 'ID' },
      { field: 'name', header: 'Name' },
      { field: 'quantity', header: 'Weight' },
      { field: 'unitOfMeasurements', header: 'Unit' },
      { field: 'harvestedDate', header: 'Date' },
      { field: 'harvestedTime', header: 'Time' },
      { field: 'producer', header: 'Producer' },
      { field: 'collects', header: 'Status' }
    ]

    this.farmerService.retrieveCrops().subscribe(
      res => {
        this.crops = res;
      }, error => {
        console.error(error);

      }
    );
  }

  onRowSelect(event) {
    let selectData = event.data;
    console.log(selectData);
    // this.router.navigate(['farmer/editCrop', selectData.ID])
  }

  switchPage(page) {
    this.router.navigate([page]);
  }
}
