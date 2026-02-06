import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Equipo } from '../../model/equipos.model';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { EquiposService } from '../../services/equipos';
import Swal from 'sweetalert2';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-equipos',
  standalone: false,
  templateUrl: './equipos.html',
  styleUrls: ['./equipos.css'],
})
export class EquiposComponent implements OnInit {

  @ViewChild('formularioEquipos') formularioEquipos!: ElementRef;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  equipos: Equipo[] = [];
   equipo: Equipo = {
    idEquipos: 0,
    nombre: '',
    tipo: '',
    descripcion: ''
  };
  
  editar: boolean = false;

  idEditar: number | null = null;

  dataSource!: MatTableDataSource<Equipo>;
  mostrarColumnas: string[] = [
    'idEquipos',
    'nombre',
    'tipo',
    'descripcion',
    'acciones'
  ];

  constructor(private equiposService: EquiposService) {}

  ngOnInit(): void {
    this.findAll();
  }

  
  findAll(): void {
    this.equiposService.findAll().subscribe((data: any) => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }


  save(): void {
  // Validaciones básicas
  if (!this.equipo.nombre || this.equipo.nombre.trim() === '') {
    Swal.fire('Validación', 'El nombre del equipo es obligatorio', 'warning');
    return;
  }

  if (!this.equipo.tipo || this.equipo.tipo.trim() === '') {
    Swal.fire('Validación', 'El tipo de equipo es obligatorio', 'warning');
    return;
  }

  // Crear objeto para enviar (asegurar ID=0)
  const equipoParaGuardar = {
    ...this.equipo,
    idEquipos: 0  // Importante para auto-increment
  };

  this.equiposService.save(equipoParaGuardar).subscribe({
    next: (response) => {
      console.log('✅ Equipo guardado:', response);
      Swal.fire('Éxito', 'Equipo creado correctamente', 'success');
      
      // Limpiar formulario
      this.equipo = {
        idEquipos: 0,
        nombre: '',
        tipo: '',
        descripcion: ''
      };
      
      // Recargar lista
      this.findAll();
    },
    error: (error) => {
      console.error(' Error al guardar:', error);
      
      let mensaje = 'Error al guardar el equipo';
      if (error.status === 400) {
        mensaje = 'Datos inválidos. Verifique la información.';
      } else if (error.error && error.error.message) {
        mensaje = error.error.message;
      }
      
      Swal.fire('Error', mensaje, 'error');
    }
  });
}
  
  update(): void {
    if (this.idEditar !== null) {
      this.equiposService.update(this.idEditar, this.equipo).subscribe(() => {
        this.equipo = {} as Equipo;
        this.editar = false;
        this.idEditar = null;
        this.findAll();
      });
    }
  }


  delete(): void {
    Swal.fire({
      title: '¿Desea eliminar el equipo?',
      text: 'Esta acción no se puede deshacer',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
    }).then((result) => {
      if (result.isConfirmed) {
        this.equiposService.delete(this.equipo.idEquipos).subscribe(() => {
          this.findAll();
          this.equipo = {} as Equipo;
          Swal.fire('Eliminado', 'El equipo ha sido eliminado', 'success');
        });
      } else {
        this.equipo = {} as Equipo;
      }
    });
  }


  editarEquipos(equipo: Equipo): void {
    this.equipo = { ...equipo };
    this.idEditar = equipo.idEquipos;
    this.editar = true;

    setTimeout(() => {
      this.formularioEquipos.nativeElement.scrollIntoView({
        behavior: 'smooth',
        block: 'start'
      });
    }, 100);
  }

  editarEquiposCancelar(form: NgForm): void {
    this.equipo = {} as Equipo;
    this.idEditar = null;
    this.editar = false;
    form.resetForm();
  }


  
  guardar(form: NgForm): void {
    if (this.editar && this.idEditar !== null) {
      this.update();
    } else {
      this.save();
    }
    form.resetForm();
  }

 
  applyFilter(event: Event): void {
    const filtro = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filtro.trim().toLowerCase();
  }
}
