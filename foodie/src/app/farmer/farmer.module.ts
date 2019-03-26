import { SharedModule } from './../_shared/shared.module';
import { ButtonModule } from 'primeng/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CardModule } from 'primeng/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { TableModule } from 'primeng/table';
import { FarmerComponent } from './farmer.component';
import { FarmerService } from './../_services/farmer.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditCropComponent } from './edit-crop/edit-crop.component';
import { FarmerRoutingModule } from './farmer-routing.module';
import { MatInputModule, MatNativeDateModule } from '@angular/material';
import { MatSelectModule } from '@angular/material/select';
import { CreateCropComponent } from './create-crop/create-crop.component';

@NgModule({
  declarations: [
    FarmerComponent,
    EditCropComponent,
    CreateCropComponent
  ],
  imports: [
    CommonModule,
    SharedModule.forRoot(),
    FarmerRoutingModule
  ],
  providers: [
    FarmerService
  ]
})
export class FarmerModule { }
