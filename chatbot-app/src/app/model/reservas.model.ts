import { Cliente } from "./cliente.model";
import { Sede } from "./sedes.model";
import { Equipo } from "./equipos.model";
import { Combos } from "./combos.model";

export interface Reserva {
  idReservas?: number;
  fechaReservas: string;
  horaRegistro: string;
  estado: string;
  total: number;
  cliente: Cliente;
  sedes: Sede;
  equipos: Equipo;
  combo: Combos;
  imagen?: string;
}
