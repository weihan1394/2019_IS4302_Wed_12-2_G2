import { ProducerComponent } from './producer.component';
import { SharedModule } from './../_shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProducerRoutingModule } from './producer-routing.module';
import { ProducerService } from '../_services/producer.service';


@NgModule({
  declarations: [ProducerComponent],
  imports: [
    CommonModule,
    SharedModule.forRoot(),
    ProducerRoutingModule
  ],
  providers: [
    ProducerService
  ]
})
export class ProducerModule { }
