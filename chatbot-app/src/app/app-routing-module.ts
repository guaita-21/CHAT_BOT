import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CombosComponent } from './components/combos/combos';
import { ReservasComponent } from './components/reservas/reservas';
import { SedesComponent } from './components/sedes/sedes';
import { EquiposComponent } from './components/equipos/equipos';
import { ClienteComponent } from './components/cliente/cliente';
import { CombosListComponent } from './components/combos-list/combos-list';
import { CarritoComponent } from './components/carrito/carrito';

const routes: Routes = [

  {path: '', redirectTo: 'comboslist', pathMatch: 'full'},
  {path: 'comboslist', component: CombosListComponent},
  {path: 'clientes', component: ClienteComponent},
  {path: 'equipos', component: EquiposComponent},
  {path: 'sedes', component: SedesComponent},
  {path: 'reservas', component: ReservasComponent},
  {path: 'combos', component: CombosComponent},
  { path: 'carrito', component: CarritoComponent }, 
 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
