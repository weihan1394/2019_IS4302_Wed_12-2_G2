import { DataService } from './../../_services/data.service';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { FarmerService } from './../../_services/farmer.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import {MAT_MOMENT_DATE_FORMATS, MomentDateAdapter} from '@angular/material-moment-adapter';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';
import * as moment from 'moment';

@Component({
  selector: 'app-edit-crop',
  templateUrl: './edit-crop.component.html',
  styleUrls: ['./edit-crop.component.scss'],
  providers: [
    {provide: MAT_DATE_LOCALE, useValue: 'en-SG'},
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS},
  ],  
})
export class EditCropComponent implements OnInit {

  cropId: number;
  crop: any;
  editForm: FormGroup;
  unitSelected:string = "kg";
  units: any[] = [
    {value: "kg"},
    {value: "g"},
    {value: "lb"}
  ]

  constructor(private activatedRoute: ActivatedRoute, private farmerService: FarmerService, 
    private formBuilder: FormBuilder, private dataService:DataService, private router:Router) { }

  ngOnInit() {
    this.dataService.changeTitle("");
    this.cropId = parseInt(this.activatedRoute.snapshot.paramMap.get('id'));
    if (this.cropId) {
      this.crop = this.farmerService.retrieveCropsById(this.cropId);
      if (!this.crop) {
        console.error("error");
      }
    } else {
      console.error("Invalid id");
    }

    let weight = this.crop.Weight;
    let unit = isNaN(weight[weight.length - 2]) ? weight.substring(weight.length - 2) : weight.substring(weight.length - 1)
    this.unitSelected = unit;
    weight = weight.replace(unit, '');
    // let date = moment(this.crop.Date).format('DD-MM-YYYY')
    this.editForm = this.formBuilder.group({
      name: new FormControl(this.crop.Name, {
        validators: [Validators.required]
      }),
      weight: new FormControl(weight, {
        validators: [Validators.required]
      }),
      date: new FormControl(moment(this.crop.Date, "DD/MM/YYYY"), {
        validators: [Validators.required]
      }),
      time: new FormControl(this.crop.Time, {
        validators: [Validators.required]
      })
    })
  }

  onSubmit() {

  }
}
