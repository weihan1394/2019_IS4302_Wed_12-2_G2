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
    private dataService:DataService) { }

  ngOnInit() {
    this.dataService.changeTitle("View Crops");
    this.cols = [
      { field: 'ID', header: 'ID' },
      { field: 'Name', header: 'Name' },
      { field: 'Weight', header: 'Weight' },
      { field: 'Date', header: 'Date' },
      { field: 'Time', header: 'Time' }
    ]

    this.crops = this.farmerService.retrieveCrops();
  }

  onRowSelect(event) {
    let selectData = event.data;
    console.log(selectData);
    this.router.navigate(['farmer/editCrop', selectData.ID])
  }
}
