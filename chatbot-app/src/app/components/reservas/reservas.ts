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
  styleUrls: ['./reservas.css'],
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

  // Propiedades para imágenes
  imagenAnterior?: string;
  seleccionarArchivo?: File;

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

  @ViewChild('formularioReserva') formularioReserva!: NgForm;
  @ViewChild('formContainer') formContainer!: ElementRef;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild('modalReserva') modalReserva!: TemplateRef<any>;
  @ViewChild('modalDetalles') modalDetalles!: TemplateRef<any>;
  @ViewChild('fileInput') fileInput!: ElementRef;

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
    this.reservaService.findAll().subscribe({
      next: (data: Reserva[]) => {
        console.log('Reservas cargadas:', data);
        this.reservas = data;
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      error: (error: any) => {
        console.error('Error al cargar reservas:', error);
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudieron cargar las reservas'
        });
      }
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

  onFileSelected(event: any): void {
    const file: File = event.target.files[0];
    if (file) {
      this.seleccionarArchivo = file;
      
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.imagenAnterior = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }

  subirImagen(): void {
    if (this.seleccionarArchivo) {
      const formData = new FormData();
      formData.append('file', this.seleccionarArchivo);
      
      Swal.fire({
        title: 'Subiendo imagen...',
        allowOutsideClick: false,
        didOpen: () => {
          Swal.showLoading();
        }
      });

      this.reservaService.subirImagen(formData).subscribe({
        next: (response: any) => {
          Swal.close();
          if (response && response.ruta) {
            this.reserva.imagen = response.ruta;
            this.seleccionarArchivo = undefined;
            this.imagenAnterior = undefined;
            Swal.fire({
              icon: 'success',
              title: 'Éxito',
              text: 'Imagen subida correctamente',
              timer: 1500,
              showConfirmButton: false
            });
          }
        },
        error: (error: any) => {
          Swal.close();
          console.error('Error al subir imagen:', error);
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'No se pudo subir la imagen'
          });
        }
      });
    } else {
      Swal.fire({
        icon: 'warning',
        title: 'Advertencia',
        text: 'Por favor selecciona un archivo primero'
      });
    }
  }

  getImagenUrl(reserva: Reserva | null): string {
    if (!reserva) return '';
    return reserva.imagen ? `http://localhost:8080/${reserva.imagen}` : '';
  }

  getImagenActual(): string {
    if (this.imagenAnterior) {
      return this.imagenAnterior;
    }
    if (this.reserva && this.reserva.imagen) {
      return `http://localhost:8080/${this.reserva.imagen}`;
    }
    return '';
  }

  limpiarImagen(): void {
    this.seleccionarArchivo = undefined;
    this.imagenAnterior = undefined;
    if (this.fileInput) {
      this.fileInput.nativeElement.value = '';
    }
  }

  save(): void {
    // Construir payload con estructura correcta
    const reservaToSave: any = {
      fechaReservas: this.reserva.fechaReservas,
      estado: this.reserva.estado,
      total: Number(this.reserva.total),
      cliente: this.reserva.cliente,
      sedes: this.reserva.sedes,
      equipos: this.reserva.equipos,
      combo: this.reserva.combo
    };

    if (this.reserva.horaRegistro && this.reserva.horaRegistro.trim() !== '') {
      reservaToSave.horaRegistro = this.reserva.horaRegistro.includes(':00') 
        ? this.reserva.horaRegistro 
        : this.reserva.horaRegistro + ':00';
    }

    if (this.reserva.imagen) {
      reservaToSave.imagen = this.reserva.imagen;
    }

    console.log('Datos a guardar:', JSON.stringify(reservaToSave, null, 2));

    this.reservaService.save(reservaToSave).subscribe({
      next: (response: any) => {
        console.log('Respuesta exitosa:', response);
        Swal.fire({
          icon: 'success',
          title: 'Éxito',
          text: 'Reserva guardada correctamente',
          timer: 1500,
          showConfirmButton: false
        });
        this.reserva = {} as Reserva;
        this.limpiarImagen();
        this.findAll();
        this.cerrarModal();
      },
      error: (error: any) => {
        console.error('Error al guardar:', error);
        let mensajeError = 'No se pudo guardar la reserva';
        if (error.error && error.error.mensaje) {
          mensajeError = error.error.mensaje;
        } else if (error.error && typeof error.error === 'string') {
          mensajeError = error.error;
        }
        
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: mensajeError
        });
      }
    });
  }

  update(): void {
    if (this.idEditar !== null) {
      const reservaToUpdate: any = {
        idReservas: this.idEditar,
        fechaReservas: this.reserva.fechaReservas,
        estado: this.reserva.estado,
        total: Number(this.reserva.total),
        cliente: this.reserva.cliente,
        sedes: this.reserva.sedes,
        equipos: this.reserva.equipos,
        combo: this.reserva.combo
      };

      if (this.reserva.horaRegistro && this.reserva.horaRegistro.trim() !== '') {
        reservaToUpdate.horaRegistro = this.reserva.horaRegistro.includes(':00')
          ? this.reserva.horaRegistro
          : this.reserva.horaRegistro + ':00';
      }

      if (this.reserva.imagen) {
        reservaToUpdate.imagen = this.reserva.imagen;
      }

      console.log('ID que se envía:', this.idEditar);
      console.log('Datos a actualizar:', JSON.stringify(reservaToUpdate, null, 2));

      this.reservaService.update(this.idEditar, reservaToUpdate).subscribe({
        next: () => {
          Swal.fire({
            icon: 'success',
            title: 'Éxito',
            text: 'Reserva actualizada correctamente',
            timer: 1500,
            showConfirmButton: false
          });
          this.reserva = {} as any;
          this.editar = false;
          this.idEditar = null;
          this.limpiarImagen();
          this.findAll();
          this.cerrarModal();
        },
        error: (error: any) => {
          console.error('Error al actualizar:', error);
          let mensajeError = 'No se pudo actualizar la reserva';
          if (error.error && error.error.mensaje) {
            mensajeError = error.error.mensaje;
          } else if (error.error && typeof error.error === 'string') {
            mensajeError = error.error;
          }
          
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: mensajeError
          });
        }
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
        const idToDelete = reserva.idReservas;
        if (idToDelete) {
          this.reservaService.delete(idToDelete).subscribe({
            next: () => {
              this.findAll();
              Swal.fire('Eliminado', 'La reserva ha sido eliminada', 'success');
            },
            error: (error: any) => {
              console.error('Error al eliminar:', error);
              let mensajeError = 'No se pudo eliminar la reserva';
              if (error.error && error.error.mensaje) {
                mensajeError = error.error.mensaje;
              }
              Swal.fire('Error', mensajeError, 'error');
            }
          });
        } else {
          Swal.fire('Error', 'No se pudo obtener el ID de la reserva', 'error');
        }
      }
    });
  }

  editarReserva(reserva: Reserva): void {
    this.reserva = { ...reserva };
    this.idEditar = reserva.idReservas ?? null;
    this.editar = true;

    if (reserva.imagen) {
      this.imagenAnterior = reserva.imagen;
    }

    setTimeout(() => {
      try {
        this.formContainer?.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
      } catch (e) {
        // fallback: no hacer nada
      }
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
  }

  filtrarReserva(event: Event): void {
    const filtro = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filtro.trim().toLowerCase();
  }

  abrirModal(reserva?: Reserva): void {
    if (reserva) {
      this.reserva = { ...reserva };
      this.editar = true;
      this.idEditar = reserva.idReservas ?? null;

      if (reserva.imagen) {
        this.imagenAnterior = reserva.imagen;
      }
    } else {
      this.reserva = {} as Reserva;
      this.editar = false;
      this.idEditar = null;
      this.limpiarImagen();
    }

    setTimeout(() => {
      if (this.formularioReserva) {
        this.formularioReserva.resetForm(this.reserva);
      }
    }, 100);

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
