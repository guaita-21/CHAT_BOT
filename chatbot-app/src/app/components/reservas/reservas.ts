import { Component, ElementRef, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { Reserva } from '../../model/reservas.model';
import { Cliente } from '../../model/cliente.model';
import { Sede } from '../../model/sedes.model';
import { Equipo } from '../../model/equipos.model';
import { Combos } from '../../model/combos.model';

import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';

import { ReservaService } from '../../services/reservas';
import { ClienteService } from '../../services/cliente';
import { SedesService } from '../../services/sedes';
import { EquiposService } from '../../services/equipos';
import { CombosService } from '../../services/combos';

import { MatDialog } from '@angular/material/dialog';
import Swal, { SweetAlertResult } from 'sweetalert2';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-reservas',
  standalone: false,
  templateUrl: './reservas.html',
  styleUrl: './reservas.css',
})
export class ReservasComponent implements OnInit {

  reservas: Reserva[] = [];
  clientes: Cliente[] = [];
  sedes: Sede[] = [];
  equipos: Equipo[] = [];
  combos: Combos[] = [];

  reserva: Reserva = {} as Reserva;

  editar: boolean = false;
  idEditar: number | null = null;

  dataSource!: MatTableDataSource<Reserva>;
  reservaSeleccionada: Reserva | null = null;

  mostrarColumnas: string[] = [
    'detalles',
    'idReservas',
    'fechaReservas',
    'horaRegistro',
    'estado',
    'total',
    'cliente',
    'sedes',
    'equipos',
    'combo',
    'acciones'
  ];

  @ViewChild('formularioReserva') formularioReserva!: ElementRef;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild('modalReserva') modalReserva!: TemplateRef<any>;
  @ViewChild('modalDetalles') modalDetalles!: TemplateRef<any>;

  constructor(
    private reservaService: ReservaService,
    private clienteService: ClienteService,
    private sedesService: SedesService,
    private equiposService: EquiposService,
    private comboService: CombosService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.findAll();
    this.cargarClientes();
    this.cargarSedes();
    this.cargarEquipos();
    this.cargarCombos();
  }

  findAll(): void {
    this.reservaService.findAll().subscribe(data => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  cargarClientes(): void {
    this.clienteService.findAll().subscribe(data => {
      this.clientes = data;
    });
  }

  cargarSedes(): void {
    this.sedesService.findAll().subscribe(data => {
      this.sedes = data;
    });
  }

  cargarEquipos(): void {
    this.equiposService.findAll().subscribe(data => {
      this.equipos = data;
    });
  }

  cargarCombos(): void {
    this.comboService.findAll().subscribe((data: Combos[]) => {
      this.combos = data;
    });
  }

  save(): void {
    this.reservaService.save(this.reserva).subscribe(() => {
      this.reserva = {} as Reserva;
      this.findAll();
    });
  }

  update(): void {
    if (this.idEditar !== null) {
      this.reservaService.update(this.idEditar, this.reserva).subscribe(() => {
        this.reserva = {} as Reserva;
        this.editar = false;
        this.idEditar = null;
        this.findAll();
      });
    }
  }

  delete(reserva: Reserva): void {
    Swal.fire({
      title: '¿Desea eliminar la reserva?',
      text: 'Esta acción no se puede deshacer',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
    }).then((result: SweetAlertResult) => {

      if (result.isConfirmed) {
        this.reservaService.delete(reserva.idReservas).subscribe(() => {
          this.findAll();
          Swal.fire('Eliminado', 'La reserva ha sido eliminada', 'success');
        });
      }

    });
  }

  editarReserva(reserva: Reserva): void {
    this.reserva = { ...reserva };
    this.idEditar = reserva.idReservas;
    this.editar = true;

    setTimeout(() => {
      this.formularioReserva.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }, 100);
  }

  cancelarEdicion(form: NgForm): void {
    this.reserva = {} as Reserva;
    this.idEditar = null;
    this.editar = false;
    form.resetForm();
  }

  guardarReserva(): void {
    if (this.editar && this.idEditar !== null) {
      this.update();
    } else {
      this.save();
    }
    this.dialog.closeAll();
  }

  filtrarReserva(event: Event): void {
    const filtro = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filtro.trim().toLowerCase();
  }

  abrirModal(reserva?: Reserva): void {
    if (reserva) {
      this.reserva = { ...reserva };
      this.editar = true;
      this.idEditar = reserva.idReservas;
    } else {
      this.reserva = {} as Reserva;
      this.editar = false;
      this.idEditar = null;
    }

    this.dialog.open(this.modalReserva, {
      width: '800px',
      disableClose: true
    });
  }

  abrirModalDetalle(reserva: Reserva): void {
    this.reservaSeleccionada = reserva;
    this.dialog.open(this.modalDetalles, { width: '500px' });
  }

  cerrarModal(): void {
    this.dialog.closeAll();
    this.reservaSeleccionada = null;
  }

  compareCliente(c1: Cliente, c2: Cliente): boolean {
    return c1 && c2 ? c1.idCliente === c2.idCliente : c1 === c2;
  }

  compareSede(s1: Sede, s2: Sede): boolean {
    return s1 && s2 ? s1.idSedes === s2.idSedes : s1 === s2;
  }

  compareEquipo(e1: Equipo, e2: Equipo): boolean {
    return e1 && e2 ? e1.idEquipos === e2.idEquipos : e1 === e2;
  }

  compareCombo(c1: Combos, c2: Combos): boolean {
    return c1 && c2 ? c1.idCombo === c2.idCombo : c1 === c2;
  }

}
