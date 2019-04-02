import { ActivatedRoute, Router } from '@angular/router';
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

  crop;
  distributors: any[] = [];
  retailers: any[] = [];
  createForm: FormGroup;
  packType: any[] = [
    { value: "Box" },
    { value: "PlasticBag" }
  ]
  btnLabel: string;

  splitTypes: string[] = ["Weight", "NumPacks"]
  // splitType: string;
  isSameData: boolean;

  constructor(private activatedRoute: ActivatedRoute, private producerService: ProducerService,
    private formBuilder: FormBuilder, private dataService: DataService, private router: Router) { }

  ngOnInit() {
    this.dataService.changeTitle('');
    // let user = JSON.parse(localStorage.getItem('currentUser'));
    let cropId = this.activatedRoute.snapshot.paramMap.get('id');
    this.producerService.retrieveCropById(cropId).subscribe(
      res => {
        this.crop = res;
      }, err => {
        console.error(err);
      }
    );
    this.producerService.retrieveAllDistributors().subscribe(
      res => {
        res.forEach(element => {
          this.distributors.push(element.Id);
          console.log("dis", this.distributors)
        });
      }
    )
    this.producerService.retrieveAllRetailers().subscribe(
      res => {
        res.forEach(element => {
          this.retailers.push(element.Id);
          console.log("ret", this.retailers)
        });
      }
    )
    this.isSameData = false;
    this.btnLabel = "Next";
    this.createForm = this.formBuilder.group({
      isSameData: new FormControl(false),
      splitType: new FormControl('', {
        validators: [Validators.required]
      }),
      packType: new FormControl(''),
      weightPerPack: new FormControl(''),
      numPacks: new FormControl(''),
      distributor: new FormControl(''),
      retailer: new FormControl('')
    })

    this.createForm.get('splitType').valueChanges.subscribe(
      value => {
        if (value == "Weight") {
          this.createForm.get('numPacks').setValue('');
          this.createForm.get('numPacks').clearValidators();
          this.createForm.get('weightPerPack').setValidators([Validators.required, Validators.min(1)]);
        } else if (value == "NumPacks") {
          this.createForm.get('weightPerPack').setValue('');
          this.createForm.get('weightPerPack').clearValidators();
          this.createForm.get('numPacks').setValidators([Validators.required, Validators.min(1)]);
        } else {
          this.createForm.get('numPacks').setValue('');
          this.createForm.get('weightPerPack').setValue('');
          this.createForm.get('weightPerPack').clearValidators();
          this.createForm.get('numPacks').clearValidators();
        }
      })

    this.createForm.get('isSameData').valueChanges.subscribe(
      value => {
        if (value) {
          this.createForm.get('packType').setValidators([Validators.required]);
          this.createForm.get('distributor').setValidators([Validators.required]);
          this.createForm.get('retailer').setValidators([Validators.required]);
          this.btnLabel = "Create";
        } else {
          this.createForm.get('packType').clearValidators();
          this.createForm.get('distributor').clearValidators();
          this.createForm.get('retailer').clearValidators();
          this.btnLabel = "Next";
        }
      })
  }

  onSplitTypeChange(event) {

  }

  onSubmit() {
    if (this.createForm.valid) {
      let formValues = this.createForm.value;
      let isSameData = formValues.isSameData;
      let splitType = formValues.splitType;
      let number: number;
      if (splitType == "Weight") {
        number = formValues.weightPerPack;
      } else {
        number = formValues.numPacks;
      }
      if (!isSameData) {
        let splitBatch = { "crop": this.crop, "splitType": splitType, "number": number };
        console.log(splitBatch)
        sessionStorage.setItem('batchInfo', JSON.stringify(splitBatch));
        this.router.navigate(['producer/splitBatch']);
      }

    } else {
      console.error("error");
      Object.keys(this.createForm.controls).forEach(field => {
        const control = this.createForm.get(field);
        control.markAsTouched({ onlySelf: true });
      });
    }
  }

}
