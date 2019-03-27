import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { LayoutComponent } from './layout/layout.component';
import { FarmerComponent } from './farmer/farmer.component';
import { HomeComponent } from './home/home.component';
import { AuthGuard } from './_guards/auth-guard';
import { LoginComponent } from './login/login.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: "full" },
  { path: 'login', component: LoginComponent },
  {
    path: '', component: LayoutComponent, canActivate: [AuthGuard], canActivateChild: [AuthGuard], children: [
      { path: 'home', component: HomeComponent }
    ]
  },
  { path: 'farmer', redirectTo: '/farmer', pathMatch: 'full' },
  { path: 'retailer', redirectTo: '/retailer', pathMatch: 'full' },
  { path: 'pageNotFound', component: PageNotFoundComponent },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
