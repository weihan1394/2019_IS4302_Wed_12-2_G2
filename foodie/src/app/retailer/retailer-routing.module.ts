import { AuthGuard } from './../_guards/auth-guard';
import { LayoutComponent } from './../layout/layout.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RetailerComponent } from './retailer.component';

const routes: Routes = [
  {
    path: '', component: LayoutComponent, canActivate: [AuthGuard], canActivateChild: [AuthGuard], children: [
      { path: 'retailer', component: RetailerComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RetailerRoutingModule { }
