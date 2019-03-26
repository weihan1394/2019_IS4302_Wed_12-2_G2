import { CreateCropComponent } from './create-crop/create-crop.component';
import { LayoutComponent } from './../layout/layout.component';
import { FarmerComponent } from './farmer.component';
import { AuthGuard } from './../_guards/auth-guard';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditCropComponent } from './edit-crop/edit-crop.component';

const routes: Routes = [
  {
    path: '', component: LayoutComponent, canActivate: [AuthGuard], canActivateChild: [AuthGuard], children: [
      { path: 'farmer', component: FarmerComponent },
      { path: 'farmer/editCrop/:id', component: EditCropComponent },
      { path: 'farmer/createCrop', component: CreateCropComponent }
    ]
  }

];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FarmerRoutingModule { }
