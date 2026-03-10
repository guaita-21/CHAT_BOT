import { Component, ElementRef, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { Combos } from '../../model/combos.model';
import Swal from 'sweetalert2';
import { NgForm } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { CombosService } from '../../services/combos';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-combos',
  standalone: false,
  templateUrl: './combos.html',
  styleUrls: ['./combos.css'],
})
export class CombosComponent implements OnInit {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild('modalCombo') modalCombo!: TemplateRef<any>;
  @ViewChild('modalDetalles') modalDetalles!: TemplateRef<any>;
  @ViewChild('fileInput') fileInput!: ElementRef<HTMLInputElement>;

  combos: Combos[] = [];
  combo: Combos = this.nuevoCombo();

  editar = false;
  idEditar: number | null = null;

  // Imagen
  seleccionarArchivo?: File;
  imagenSrc: string = ''; // ✅ aquí guardamos lo que se muestra (base64 o URL)

  dataSource!: MatTableDataSource<Combos>;
  combosSeleccionado: Combos | null = null;

  mostrarColumnas: string[] = [
    'idCombo', 'nombre', 'incluyeBalas', 'tiempo_min', 'jugadores', 'precio', 'imagen', 'acciones'
  ];

  constructor(
    private combosService: CombosService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.findAll();
  }

  nuevoCombo(): Combos {
    return {
      idCombo: 0,
      nombre: '',
      incluyeBalas: 0,
      tiempo_min: 0,
      jugadores: 0,
      precio: 0,
      stock: 0,
      imagen: ''
    };
  }

  findAll(): void {
    this.combosService.findAll().subscribe({
      next: (data: Combos[]) => {
        this.combos = data;
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      error: () => Swal.fire('Error', 'No se pudieron cargar los combos', 'error'),
    });
  }

  // ========= Imagen =========
  onFileSelected(event: any): void {
    const file: File | undefined = event.target.files?.[0];
    if (!file) return;

    if (!file.type.startsWith('image/')) {
      Swal.fire('Error', 'Solo se permiten imágenes', 'error');
      return;
    }
    if (file.size > 5 * 1024 * 1024) {
      Swal.fire('Error', 'Máximo 5MB', 'error');
      return;
    }

    this.seleccionarArchivo = file;

    // ✅ preview estable (base64)
    const reader = new FileReader();
    reader.onload = () => {
      this.imagenSrc = String(reader.result || '');
    };
    reader.readAsDataURL(file);
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
      didOpen: () => Swal.showLoading(),
    });

    this.combosService.subirImagen(formData).subscribe({
      next: (response: any) => {
        Swal.close();

        if (!response?.ruta) {
          Swal.fire('Error', 'El backend no devolvió "ruta"', 'error');
          return;
        }

        // ✅ Guardamos ruta en el modelo (esto es lo que se guarda en BD)
        this.combo.imagen = response.ruta; // "portadas/xxx.jpg"

        // ✅ y actualizamos el src a la URL real para verla ya desde el server
        this.imagenSrc = `http://localhost:8080/${response.ruta}`;

        // reset file
        this.seleccionarArchivo = undefined;
        if (this.fileInput?.nativeElement) this.fileInput.nativeElement.value = '';

        Swal.fire({
          icon: 'success',
          title: 'Imagen subida',
          timer: 1200,
          showConfirmButton: false,
        });
      },
      error: (err) => {
        Swal.close();
        console.error(err);
        Swal.fire('Error', 'No se pudo subir la imagen', 'error');
      },
    });
  }

  limpiarImagen(): void {
    this.seleccionarArchivo = undefined;
    this.imagenSrc = '';
    this.combo.imagen = ''; // ✅ importante para que se borre del payload si quieres
    if (this.fileInput?.nativeElement) this.fileInput.nativeElement.value = '';
  }

  getImagenUrl(c: Combos | null): string {
    if (!c?.imagen) return '';
    return `http://localhost:8080/${c.imagen}`;
  }

  // ========= Modales =========
  abrirModal(combo?: Combos): void {
    if (combo) {
      this.combo = { ...combo };
      this.editar = true;
      this.idEditar = combo.idCombo;

      // ✅ precargar imagen si existe
      this.imagenSrc = combo.imagen ? `http://localhost:8080/${combo.imagen}` : '';
      this.seleccionarArchivo = undefined;
      if (this.fileInput?.nativeElement) this.fileInput.nativeElement.value = '';

    } else {
      this.combo = this.nuevoCombo();
      this.editar = false;
      this.idEditar = null;
      this.limpiarImagen();
    }

    this.dialog.open(this.modalCombo, { width: '650px', disableClose: true });
  }

  abrirModalDetalle(combo: Combos): void {
    this.combosSeleccionado = combo;
    this.dialog.open(this.modalDetalles, { width: '450px' });
  }

  cerrarModal(): void {
    this.dialog.closeAll();
    this.combosSeleccionado = null;
  }

  // ========= CRUD =========
  save(): void {
    // ✅ aseguramos que imagen vaya en payload
    const payload: Combos = { ...this.combo, idCombo: 0 };

    console.log('POST payload:', payload);

    this.combosService.save(payload).subscribe({
      next: () => {
        Swal.fire('Éxito', 'Combo creado', 'success');
        this.combo = this.nuevoCombo();
        this.limpiarImagen();
        this.findAll();
        this.cerrarModal();
      },
      error: (err) => {
        console.error('POST error:', err);
        Swal.fire('Error', 'No se pudo guardar el combo', 'error');
      },
    });
  }

  update(): void {
  if (this.idEditar == null) return;

  const payload: Combos = { ...this.combo, idCombo: this.idEditar };

  console.log('PUT payload:', payload);

  this.combosService.update(this.idEditar, payload).subscribe({
    next: () => {
      Swal.fire('Éxito', 'Combo actualizado', 'success');
      this.combo = this.nuevoCombo();
      this.editar = false;
      this.idEditar = null;
      this.limpiarImagen();
      this.findAll();
      this.cerrarModal();
    },
    error: (err) => {
      console.error('PUT error:', err);
      console.error('Error status:', err.status);        // ← Agrega esto
      console.error('Error message:', err.message);      // ← Agrega esto
      console.error('Error details:', err.error);        // ← Agrega esto
      Swal.fire('Error', `No se pudo actualizar: ${err.message}`, 'error');
    },
  });
}

  delete(combo: Combos): void {
    Swal.fire({
      title: '¿Eliminar combo?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí',
    }).then((r) => {
      if (r.isConfirmed) {
        this.combosService.delete(combo.idCombo).subscribe({
          next: () => {
            Swal.fire('Eliminado', '', 'success');
            this.findAll();
          },
        });
      }
    });
  }

  guardar(form: NgForm): void {
    this.editar ? this.update() : this.save();
  }

  applyFilter(event: Event): void {
    const filtro = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filtro.trim().toLowerCase();
  }
}