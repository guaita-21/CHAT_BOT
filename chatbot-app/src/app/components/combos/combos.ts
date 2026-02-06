import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Combos } from '../../model/combos.model';
import Swal from "sweetalert2";
import { NgForm } from "@angular/forms";
import { MatTableDataSource } from "@angular/material/table";
import { CombosService } from "../../services/combos";
import { MatPaginator } from "@angular/material/paginator";
import { MatSort } from "@angular/material/sort";

@Component({
  selector: 'app-combos',
  standalone: false,
  templateUrl: './combos.html',
  styleUrls: ['./combos.css'], 
})
export class CombosComponent implements OnInit {

  @ViewChild('formularioCombos') formularioCombos!: ElementRef;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  combos: Combos[] = [];
  combo: Combos = {} as Combos;
  editar: boolean = false;
  idEditar: number | null = null;
  dataSource!: MatTableDataSource<Combos>;

  mostrarColumnas: string[] = [
    'idCombo', 'nombre', 'incluyeBalas', 'tiempo_min', 'jugadores', 'precio', 'acciones'
  ];

  constructor(private combosService: CombosService) { }

  ngOnInit(): void {
    this.findAll();
  }

  // ===================== CRUD =====================
  findAll(): void {
  this.combosService.findAll().subscribe({
    next: (data: Combos[]) => {
      console.log('Combos cargados:', data);
      this.combos = data;
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    },
    error: (error) => {
      console.error('Error cargando combos:', error);
      Swal.fire('Error', 'No se pudieron cargar los combos', 'error');
    }
  });
}

 save(): void {
  console.log('Datos a guardar:', this.combo);
  
  // Validaciones básicas
  if (!this.combo.nombre || this.combo.nombre.trim() === '') {
    Swal.fire('Error', 'El nombre del combo es obligatorio', 'warning');
    return;
  }
  
  if (this.combo.precio <= 0) {
    Swal.fire('Error', 'El precio debe ser mayor a 0', 'warning');
    return;
  }
  
  // Crear objeto para enviar (asegurar ID=0)
  const comboParaGuardar = {
    ...this.combo,
    idCombo: 0  // Importante para auto-increment
  };
  
  this.combosService.save(comboParaGuardar).subscribe({
    next: (response) => {
      console.log('✅ Combo guardado:', response);
      Swal.fire('Éxito', 'Combo creado correctamente', 'success');
      
      // Limpiar formulario
      
      
      // Recargar lista
      this.findAll();
    },
    error: (error) => {
      console.error(' Error al guardar:', error);
      
      let mensaje = 'Error al guardar el combo';
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
  if (this.idEditar === null) {
    Swal.fire('Error', 'No hay un combo seleccionado para editar', 'warning');
    return;
  }
  
  // Validaciones
  if (!this.combo.nombre || this.combo.nombre.trim() === '') {
    Swal.fire('Error', 'El nombre del combo es obligatorio', 'warning');
    return;
  }
  
  if (this.combo.precio <= 0) {
    Swal.fire('Error', 'El precio debe ser mayor a 0', 'warning');
    return;
  }
  
  this.combosService.update(this.idEditar, this.combo).subscribe({
    next: (response) => {
      console.log('✅ Combo actualizado:', response);
      Swal.fire('Éxito', 'Combo actualizado correctamente', 'success');
      
      // Limpiar y salir del modo edición
     
      // Recargar lista
      this.findAll();
    },
    error: (error) => {
      console.error('S Error al actualizar:', error);
      Swal.fire('Error', 'No se pudo actualizar el combo', 'error');
    }
  });
}

delete(combo: Combos): void {
  Swal.fire({
    title: '¿Eliminar combo?',
    text: `¿Está seguro de eliminar el combo "${combo.nombre}"?`,
    icon: 'warning',
    showCancelButton: true,
    confirmButtonText: 'Sí, eliminar',
    cancelButtonText: 'Cancelar',
    confirmButtonColor: '#d33',
    cancelButtonColor: '#3085d6'
  }).then((result) => {
    if (result.isConfirmed) {
      this.combosService.delete(combo.idCombo).subscribe({
        next: () => {
          Swal.fire('Eliminado', 'El combo ha sido eliminado', 'success');
          this.findAll();
        },
        error: (error) => {
          console.error('Error eliminando combo:', error);
          
          let mensaje = 'No se pudo eliminar el combo';
          if (error.status === 404) {
            mensaje = 'El combo no existe';
          } else if (error.status === 409) {
            mensaje = 'No se puede eliminar porque tiene registros relacionados';
          }
          
          Swal.fire('Error', mensaje, 'error');
        }
      });
    }
  });
}
  // ===================== Interacción UI =====================
  editarCombos(combos: Combos): void {
    this.combo = { ...combos };
    this.idEditar = combos.idCombo;
    this.editar = true;

    setTimeout(() => {
      this.formularioCombos.nativeElement.scrollIntoView({
        behavior: 'smooth',
        block: 'start'
      });
    }, 100);
  }

  editarCombosCancelar(form: NgForm): void {
    this.combo = {} as Combos;
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

  // ===================== Filtro =====================
  applyFilter(event: Event): void {
    const filtro = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filtro.trim().toLowerCase();
  }
}
