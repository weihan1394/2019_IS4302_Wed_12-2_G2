import { ConfirmationService, MessageService } from 'primeng/api';
import { RetailerService } from './../../_services/retailer.service';
import { DataService } from './../../_services/data.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-view-batch-details',
  templateUrl: './view-batch-details.component.html',
  styleUrls: ['./view-batch-details.component.scss']
})
export class ViewBatchDetailsComponent implements OnInit {

  batch;

  constructor(private activatedRoute: ActivatedRoute, private messageService: MessageService, private router: Router,
    private dataService: DataService, private confirmationService: ConfirmationService,
    private retailerService: RetailerService) { }

  ngOnInit() {
    this.dataService.changeTitle("");
    let batchId = this.activatedRoute.snapshot.paramMap.get('id');
    this.retailerService.retrieveBatchById(batchId).subscribe(
      res => {
        this.batch = res;
      }, err => {
        console.error(err);
      }
    )
  }

  collectGood() {
    this.confirmationService.confirm({
      message: 'Do you want to collect this batch?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.retailerService.collectBatch(this.batch.goodId).subscribe(
          res => {
            this.messageService.add({ severity: 'success', summary: 'Success', detail: "Batch collected!" });
            this.router.navigate(["retailer"]);
          }, err => {
            this.messageService.add({ severity: 'error', summary: 'Error', detail: err });
          }
        )
      },
      reject: () => {
      }
    });
  }
}
