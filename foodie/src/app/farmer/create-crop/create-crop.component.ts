import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { DataService } from './../../_services/data.service';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { FarmerService } from './../../_services/farmer.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-create-crop',
  templateUrl: './create-crop.component.html',
  styleUrls: ['./create-crop.component.scss']
})
export class CreateCropComponent implements OnInit {

  createForm: FormGroup;
  units: any[] = [
    { value: "KG" },
    { value: "G" },
    { value: "L" },
    { value: "ML" }
  ]
  producers: any[] = [];
  constructor(private farmerService: FarmerService, private formBuilder: FormBuilder, private dataService: DataService,
    private messageService: MessageService, private router:Router) { }

  ngOnInit() {
    this.dataService.changeTitle('');
    this.farmerService.retrieveAllProducers().subscribe(
      res => {
        res.forEach(element => {
          this.producers.push({ viewValue: element.name, value: element.Id });
        });
      }
    )
    this.createForm = this.formBuilder.group({
      name: new FormControl('', {
        validators: [Validators.required]
      }),
      weight: new FormControl('', {
        validators: [Validators.required, Validators.min(0)]
      }),
      unit: new FormControl('', {
        validators: [Validators.required]
      }),
      producer: new FormControl('', {
        validators: [Validators.required]
      })
    })
  }

  onSubmit() {
    if (this.createForm.valid) {
      let formValues = this.createForm.value;
      let name = formValues.name;
      let weight = formValues.weight;
      let unit = formValues.unit;
      let producer = formValues.producer;
      this.farmerService.createCrop(name, weight, unit, producer).subscribe(
        res => {
          this.messageService.add({ severity: 'info', summary: 'Success', detail: "Crop created!" });
          this.router.navigate(["/farmer"]);
        }, err => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: err });
        }
      )

    } else {
      Object.keys(this.createForm.controls).forEach(field => {
        const control = this.createForm.get(field);
        control.markAsTouched({ onlySelf: true });
      });
    }
  }
}