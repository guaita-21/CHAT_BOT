import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { ClienteComponent } from './components/cliente/cliente';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { CombosComponent } from './components/combos/combos';
import { EquiposComponent } from './components/equipos/equipos';
import { SedesComponent } from './components/sedes/sedes';
import { ReservasComponent } from './components/reservas/reservas';
import { MatDialogModule} from '@angular/material/dialog';
import { MatSelectModule} from '@angular/material/select';
import { MatOptionModule, MatNativeDateModule } from '@angular/material/core';
import {MatDatepickerModule,} from '@angular/material/datepicker'
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatMenuModule } from '@angular/material/menu';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import {  CombosListComponent } from './components/combos-list/combos-list';
import {  CarritoComponent } from './components/carrito/carrito';



@NgModule({
  declarations: [
    App,
    ClienteComponent,
    CombosComponent,
    EquiposComponent,
    SedesComponent,
    ReservasComponent,
    CombosListComponent,
    CarritoComponent
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatFormFieldModule,
    MatIconModule,
    MatButtonModule,
    MatDialogModule,
    MatSelectModule,
    MatOptionModule,
    MatDatepickerModule,
    MatNativeDateModule,
   // mas librerias
    ReactiveFormsModule,
    MatToolbarModule,
    MatCardModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatMenuModule,
    MatSidenavModule, 
    MatListModule

  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
    MatDatepickerModule
  ],
  bootstrap: [App]
})
export class AppModule { }
