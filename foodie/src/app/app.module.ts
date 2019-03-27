import { DistributorModule } from './distributor/distributor.module';
import { ProducerModule } from './producer/producer.module';
import { SharedModule } from './_shared/shared.module';
import { RetailerModule } from './retailer/retailer.module';
import { FarmerModule } from './farmer/farmer.module';
import { AuthenticationService } from './_services/authentication.service';
import { ErrorInterceptor } from './_helper/error.interceptor';
import { JwtInterceptor } from './_helper/jwt.interceptor';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CardModule } from 'primeng/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule, MatNativeDateModule, MatIconModule } from '@angular/material';
import { MatButtonModule } from '@angular/material/button';
import { ButtonModule } from 'primeng/button';
import { FormsModule, FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { LayoutComponent } from './layout/layout.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { DataService } from './_services/data.service';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    LayoutComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FarmerModule,
    RetailerModule,
    ProducerModule,
    DistributorModule,
    SharedModule.forRoot(),
    AppRoutingModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    AuthenticationService,
    FormBuilder,
    MessageService,
    DataService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
