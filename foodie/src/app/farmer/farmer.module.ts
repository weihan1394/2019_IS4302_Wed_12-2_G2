import { DataService } from './../_services/data.service';
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
import {MatSelectModule} from '@angular/material/select';

@NgModule({
  declarations: [
    FarmerComponent, 
    EditCropComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    BrowserAnimationsModule,
    TableModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatInputModule,
    CardModule,
    FormsModule,
    ReactiveFormsModule,
    ButtonModule,
    MatSelectModule,
    FarmerRoutingModule
  ],
  providers: [
    FarmerService
  ]
})
export class FarmerModule { }
