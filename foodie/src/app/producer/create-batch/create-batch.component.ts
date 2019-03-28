import { DataService } from './../../_services/data.service';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { ProducerService } from './../../_services/producer.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-create-batch',
  templateUrl: './create-batch.component.html',
  styleUrls: ['./create-batch.component.scss']
})
export class CreateBatchComponent implements OnInit {

  createForm: FormGroup;
  packType: any[] = [
    { value: "Box" },
    { value: "PLasticBag" }
  ]

  splitTypes: string[] = ["Weight", "NumPacks"]
  splitType: string;

  constructor(private producerService: ProducerService, private formBuilder: FormBuilder, private dataService: DataService) { }

  ngOnInit() {
    this.dataService.changeTitle('');
    this.createForm = this.formBuilder.group({
      packType: new FormControl('', {
        validators: [Validators.required]
      }),
      weightPerPack: new FormControl('', {
        validators: [Validators.required, Validators.min(0)]
      }),
      producerId: new FormControl('', {
        validators: [Validators.required]
      }),
      distributorId: new FormControl('', {
        validators: [Validators.required]
      }),
      retailerId: new FormControl('', {
        validators: [Validators.required]
      })
    })
  }

  onSubmit() {
    if (this.createForm.valid) {
      let formValues = this.createForm.value;
      let name = formValues.name;
      let weight = formValues.weight;
      let date = formValues.date;
      let time = formValues.hours + " " + formValues.minutes;

      
    } else {
      Object.keys(this.createForm.controls).forEach(field => {
        const control = this.createForm.get(field);
        control.markAsTouched({ onlySelf: true });
      });
    }
  }

}
