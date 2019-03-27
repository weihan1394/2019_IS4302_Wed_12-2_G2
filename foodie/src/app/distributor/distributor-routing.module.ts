import { LayoutComponent } from './../layout/layout.component';
import { AuthGuard } from './../_guards/auth-guard';
import { DistributorComponent } from './distributor.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: '', component: LayoutComponent, canActivate: [AuthGuard], canActivateChild: [AuthGuard], children: [
      { path: 'farmer', component: DistributorComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DistributorRoutingModule { }
