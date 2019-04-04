import { SharedModule } from './../_shared/shared.module';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RetailerRoutingModule } from './retailer-routing.module';
import { RetailerComponent } from './retailer.component';
import { RetailerService } from '../_services/retailer.service';
import { ViewBatchDetailsComponent } from './view-batch-details/view-batch-details.component';

@NgModule({
  declarations: [RetailerComponent, ViewBatchDetailsComponent],
  imports: [
    CommonModule,
    SharedModule.forRoot(),
    RetailerRoutingModule
  ],
  providers: [
    RetailerService
  ]
})
export class RetailerModule { }
