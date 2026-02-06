import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Sede } from '../../model/sedes.model';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { SedesService } from '../../services/sedes';
import Swal from 'sweetalert2';
import { NgForm } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-sedes',
  standalone: false,
  templateUrl: './sedes.html',
  styleUrls: ['./sedes.css'],
})
export class SedesComponent implements OnInit {

  @ViewChild('formularioSedes') formularioSedes!: ElementRef;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  sedes: Sede[] = [];
  sede: Sede = {} as Sede;
  editar: boolean = false;
  idEditar: number | null = null;

  dataSource!: MatTableDataSource<Sede>;
  mostrarColumnas: string[] = ['idSedes', 'nombre', 'direccion', 'telefono', 'acciones'];

  constructor(private sedesService: SedesService) {}

  ngOnInit(): void {
    this.findAll();
  }

  findAll(): void {
    this.sedesService.findAll().subscribe(data => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

save(): void {
  console.log('Intentando guardar sede:', this.sede);
  
  // Asegúrate de que el ID sea 0 (para auto-increment)
  const sedeParaGuardar = {
    ...this.sede,
    idSedes: 0  // Fuerza ID a 0
  };
  
  this.sedesService.save(sedeParaGuardar).subscribe({
    next: (response) => {
      console.log('✅ Sede creada exitosamente:', response);
      Swal.fire('Éxito', 'Sede creada correctamente', 'success');
      
      // Limpiar formulario
      this.sede = {
        idSedes: 0,
        nombre: '',
        direccion: '',
        telefono: ''
      };
      
      // Recargar lista
      this.findAll();
    },
    error: (error) => {
      console.error(' Error al guardar sede:', error);
      
      // Mostrar mensaje de error detallado
      let mensajeError = 'Error desconocido';
      if (error.status === 400) {
        mensajeError = 'Datos inválidos. Verifica la información.';
      } else if (error.status === 500) {
        mensajeError = 'Error del servidor. ' + (error.error || '');
      }
      
      Swal.fire('Error', mensajeError, 'error');
    }
  });
}

  update(): void {
    if (this.idEditar !== null) {
      this.sedesService.update(this.idEditar, this.sede).subscribe(() => {
        this.sede = {} as Sede;
        this.editar = false;
        this.idEditar = null;
        this.findAll();
      });
    }
  }

 deleteSede(sede: Sede): void {
  Swal.fire({
    title: '¿Desea eliminar la sede?',
    text: 'Esta acción no se puede deshacer',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonText: 'Sí, eliminar',
    cancelButtonText: 'Cancelar',
    confirmButtonColor: '#d33',
    cancelButtonColor: '#3085d6'
  }).then((result) => {
    if (result.isConfirmed) {
      this.sedesService.delete(sede.idSedes).subscribe({
        next: () => {
          this.findAll();
          Swal.fire('Eliminado', 'La sede ha sido eliminada', 'success');
        },
        error: (error) => {
          console.error('Error al eliminar:', error);
          
          let mensajeError = 'No se pudo eliminar la sede. ';
          if (error.status === 404) {
            mensajeError += 'La sede no existe.';
          } else if (error.status === 409) {
            mensajeError += 'Tiene registros relacionados.';
          } else if (error.error) {
            mensajeError += error.error;
          }
          
          Swal.fire('Error', mensajeError, 'error');
        }
      });
    }
  });
}


  // interaccion en la pagina web

  editarSede(sede: Sede): void {
    this.sede = { ...sede };
    this.idEditar = sede.idSedes;
    this.editar = true;

    setTimeout(() => {
      this.formularioSedes.nativeElement.scrollIntoView({
        behavior: 'smooth',
        block: 'start'
      });
    }, 100);
  }

  cancelarEdicion(form: NgForm): void {
    this.sede = {} as Sede;
    this.idEditar = null;
    this.editar = false;
    form.resetForm();
  }

  guardar(form: NgForm): void {
    if (this.editar && this.idEditar !== null) {
      this.update();
      form.resetForm();
    } else {
      this.save();
      form.resetForm();
    }
  }

  applyFilter(event: Event): void {
    const filtro = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filtro.trim().toLowerCase();
  }
}
