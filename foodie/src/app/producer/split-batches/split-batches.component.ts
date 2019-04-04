import { ActivatedRoute, Router } from '@angular/router';
import { ProducerService } from './../../_services/producer.service';
import { DataService } from './../../_services/data.service';
import { FormGroup, FormBuilder, FormControl, FormArray } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-split-batches',
  templateUrl: './split-batches.component.html',
  styleUrls: ['./split-batches.component.scss']
})
export class SplitBatchesComponent implements OnInit {

  crop;
  createInfo: any;
  batchesForm: FormGroup;
  cols: any[];
  displayedColumns: string[] = ['packType', 'weight', 'distributor', 'retailer'];
  dataSource = [];
  packType: any[] = [
    { label:'Box', value: "BOX" },
    { label:'Plastic Bag', value: "PLASTICBAG" }
  ]
  constructor(private router:Router, private formBuilder: FormBuilder, private dataService: DataService, 
    private producerService:ProducerService, private messageService:MessageService) { }

  ngOnInit() {
    // this.cropId = parseInt(this.activatedRoute.snapshot.paramMap.get('id'));
    // this.crop = this.producerService.retrieveCropById(cropId);
    // this.cols = [
    //   { field: 'packType', header: 'Pack Type' },
    //   { field: 'weight', header: 'Weight' },
    //   { field: 'distributorId', header: 'Distributor' },
    //   { field: 'retailerId', header: 'Retailer' }
    // ]
    this.createInfo = JSON.parse(sessionStorage.getItem('batchInfo'))
    this.dataService.changeTitle('');
    this.batchesForm = this.formBuilder.group({
      batch: this.formBuilder.array(new Array<any>())
    })

    let splitType = this.createInfo.splitType;
    this.crop = this.createInfo.crop;
    let number = this.createInfo.number;
    let arrLength;
    let weight;
    if(splitType == "Weight") {
      weight = number;
      arrLength = this.crop.quantity/number;
    } else {
      arrLength = number;
      weight = this.crop.quantity/number;
    }

    for(let i = 0; i < arrLength; i++) {
      let b = this.batchesForm.get('batch') as FormArray;
      let data = {"id": i, "packType": "BOX", "weight": weight, "unitOfMeasurements": this.crop.unitOfMeasurements, "distributor": '', "retailer": ''}
      b.push(this.createBatch(data));
      this.dataSource.push(data)
    }
    console.log("data", this.dataSource)

  }

  createBatch(data) {
    return this.formBuilder.group({
      packType: new FormControl(data.packType),
      weight: new FormControl({value: data.weight, disabled: true}),
      unitOfMeasurements: new FormControl({value: this.crop.unitOfMeasurements, disabled: true}),
      distributor: new FormControl(data.distributor),
      retailer: new FormControl(data.retailer)
    })
  }

  get batches(): FormArray {
    return this.batchesForm.get('batch') as FormArray;
  }

  onSubmit() {
    if(this.batchesForm.valid) {
      let batchesArr = [];
      let i = 0;
      for(let control of this.batches.controls) {
        let obj = control.value;
        obj.weight = this.dataSource[i].weight;
        obj.unitOfMeasurements = this.dataSource[i].unitOfMeasurements;
        batchesArr.push(obj);
        i++;
      }
      this.producerService.createBatches(batchesArr, this.crop.cropId).subscribe(
        res => {
          this.router.navigate(["/producer"]);
        }, err => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: err });
        }
      )
    } else {
      
    }
  }
  
}
