import { ProducerComponent } from './producer.component';
import { SharedModule } from './../_shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProducerRoutingModule } from './producer-routing.module';
import { ProducerService } from '../_services/producer.service';
import { CreateBatchComponent } from './create-batch/create-batch.component';
import { SplitBatchesComponent } from './split-batches/split-batches.component';
import { CdkTableModule} from '@angular/cdk/table';
import { EditBatchComponent } from './edit-batch/edit-batch.component'

@NgModule({
  declarations: [ProducerComponent, CreateBatchComponent, SplitBatchesComponent, EditBatchComponent],
  imports: [
    CommonModule,
    SharedModule.forRoot(),
    CdkTableModule,
    ProducerRoutingModule
  ],
  providers: [
    ProducerService
  ]
})
export class ProducerModule { }
