import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DistributorRoutingModule } from './distributor-routing.module';
import { DistributorComponent } from './distributor.component';
import { SharedModule } from '../_shared/shared.module';

@NgModule({
  declarations: [DistributorComponent],
  imports: [
    CommonModule,
    SharedModule.forRoot(),
    DistributorRoutingModule
  ]
})
export class DistributorModule { }
