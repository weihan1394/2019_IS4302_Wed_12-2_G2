import { EditBatchComponent } from './edit-batch/edit-batch.component';
import { SplitBatchesComponent } from './split-batches/split-batches.component';
import { CreateBatchComponent } from './create-batch/create-batch.component';
import { AuthGuard } from './../_guards/auth-guard';
import { LayoutComponent } from './../layout/layout.component';
import { ProducerComponent } from './producer.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: '', component: LayoutComponent, canActivate: [AuthGuard], canActivateChild: [AuthGuard], children: [
      { path: 'producer', component: ProducerComponent },
      { path: 'producer/createBatch/:id', component: CreateBatchComponent},
      { path: 'producer/splitBatch', component: SplitBatchesComponent},
      { path: 'producer/editBatch/:id', component: EditBatchComponent}
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProducerRoutingModule { }
