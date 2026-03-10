import { Component, ElementRef, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { Equipo } from '../../model/equipos.model';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { EquiposService } from '../../services/equipos';
import { MatDialog } from '@angular/material/dialog';
import Swal, { SweetAlertResult } from 'sweetalert2';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-equipos',
  standalone: false,
  templateUrl: './equipos.html',
  styleUrls: ['./equipos.css'],
})
export class EquiposComponent implements OnInit {

  @ViewChild('formularioEquipos') formularioEquipos!: NgForm;
  @ViewChild('formContainer') formContainer!: ElementRef;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  // 🔥 DOS MODALES DIFERENTES
  //@ViewChild('modalEquipo') modalEquipo!: TemplateRef<any>;
  @ViewChild('modalDetalles') modalDetalles!: TemplateRef<any>;

  @ViewChild('fileInput') fileInput!: ElementRef;

  equipos: Equipo[] = [];

  equipo: Equipo = {
    idEquipos: 0,
    nombre: '',
    tipo: '',
    descripcion: '',
    imagenEquipo: ''
  };

  equipoSeleccionado: Equipo | null = null;

  editar: boolean = false;
  idEditar: number | null = null;

  imagenAnterior?: string;
  seleccionarArchivo?: File;

  dataSource!: MatTableDataSource<Equipo>;

  mostrarColumnas: string[] = [
    'idEquipos',
    'nombre',
    'tipo',
    'descripcion',
    'imagenEquipo',
    'acciones'
  ];

  constructor(
    private equiposService: EquiposService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.findAll();
  }

  findAll(): void {
    this.equiposService.findAll().subscribe((data: any) => {
      this.equipos = data;
      console.log('Equipos obtenidos:', this.equipos);
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  // ===============================
  // 📸 IMÁGENES
  // ===============================

  onFileSelected(event: any): void {
    const file: File = event.target.files[0];

    if (file) {
      if (!file.type.startsWith('image/')) {
        Swal.fire('Error', 'Solo se permiten imágenes', 'error');
        return;
      }

      if (file.size > 5 * 1024 * 1024) {
        Swal.fire('Error', 'Máximo 5MB', 'error');
        return;
      }

      this.seleccionarArchivo = file;

      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.imagenAnterior = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }

  subirImagen(): void {
    if (!this.seleccionarArchivo) {
      Swal.fire('Advertencia', 'Selecciona una imagen primero', 'warning');
      return;
    }

    const formData = new FormData();
    formData.append('file', this.seleccionarArchivo);

    Swal.fire({
      title: 'Subiendo imagen...',
      allowOutsideClick: false,
      didOpen: () => Swal.showLoading()
    });

    this.equiposService.subirImagen(formData).subscribe({
      next: (response: any) => {
        Swal.close();
        if (response?.ruta) {
          this.equipo.imagenEquipo = response.ruta;
          this.seleccionarArchivo = undefined;
          this.imagenAnterior = undefined;

          Swal.fire({
            icon: 'success',
            title: 'Imagen subida',
            timer: 1500,
            showConfirmButton: false
          });
        }
      },
      error: () => {
        Swal.close();
        Swal.fire('Error', 'No se pudo subir la imagen', 'error');
      }
    });
  }

  getImagenUrl(equipo: Equipo | null): string {
    if (!equipo?.imagenEquipo) return '';
    return `http://localhost:8080/${equipo.imagenEquipo}`;
  }

  getImagenActual(): string {
    if (this.imagenAnterior) return this.imagenAnterior;
    if (this.equipo.imagenEquipo)
      return `http://localhost:8080/${this.equipo.imagenEquipo}`;
    return '';
  }

  limpiarImagen(): void {
    this.seleccionarArchivo = undefined;
    this.imagenAnterior = undefined;
    if (this.fileInput) {
      this.fileInput.nativeElement.value = '';
    }
  }

  // ===============================
  // 💾 CRUD
  // ===============================

  save(): void {
    const equipoGuardar = {
      ...this.equipo,
      idEquipos: 0
    };

    this.equiposService.save(equipoGuardar).subscribe({
      next: () => {
        Swal.fire('Éxito', 'Equipo creado correctamente', 'success');
        this.resetFormulario();
        this.findAll();
        this.cerrarModal();
      },
      error: () => {
        Swal.fire('Error', 'No se pudo guardar', 'error');
      }
    });
  }

  update(): void {
    if (this.idEditar === null) return;

    const equipoActualizar = {
      ...this.equipo,
      idEquipos: this.idEditar
    };

    this.equiposService.update(this.idEditar, equipoActualizar).subscribe({
      next: () => {
        Swal.fire('Éxito', 'Equipo actualizado', 'success');
        this.resetFormulario();
        this.findAll();
        this.cerrarModal();
      },
      error: () => {
        Swal.fire('Error', 'No se pudo actualizar', 'error');
      }
    });
  }

  delete(equipo: Equipo): void {
    Swal.fire({
      title: '¿Eliminar equipo?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí'
    }).then((result: SweetAlertResult) => {
      if (result.isConfirmed) {
        this.equiposService.delete(equipo.idEquipos).subscribe(() => {
          this.findAll();
          Swal.fire('Eliminado', '', 'success');
        });
      }
    });
  }

  guardar(form: NgForm): void {
    this.editar ? this.update() : this.save();
  }

  // editarEquipo(equipo: Equipo): void {
  //   this.equipo = { ...equipo };
  //   this.idEditar = equipo.idEquipos;
  //   this.editar = true;
  //   this.dialog.open(this.modalEquipo, { width: '600px' });
  // }

  editarEquipo(equipo: Equipo): void {
  this.equipo = { ...equipo };
  this.idEditar = equipo.idEquipos;
  this.editar = true;

  // scroll hacia arriba
  this.formContainer.nativeElement.scrollIntoView({ behavior: 'smooth' });
}

  // ===============================
  // 👁 MODALES
  // ===============================

  // abrirModal(): void {
  //   this.resetFormulario();
  //   this.dialog.open(this.modalEquipo, {
  //     width: '600px',
  //     disableClose: true
  //   });
  // }

  abrirDetalles(equipo: Equipo): void {
    this.equipoSeleccionado = equipo;
    this.dialog.open(this.modalDetalles, {
      width: '500px'
    });
  }

  cerrarModal(): void {
    this.dialog.closeAll();
    this.equipoSeleccionado = null;
  }

  editarEquiposCancelar(): void {
    this.resetFormulario();
    this.cerrarModal();
  }

  resetFormulario(): void {
    this.equipo = {
      idEquipos: 0,
      nombre: '',
      tipo: '',
      descripcion: '',
      imagenEquipo: ''
    };
    this.idEditar = null;
    this.editar = false;
    this.limpiarImagen();
  }

  applyFilter(event: Event): void {
    const filtro = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filtro.trim().toLowerCase();
  }
}