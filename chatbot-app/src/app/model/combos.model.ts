export interface Combos {
  idCombo: number;
  nombre: string;
  incluyeBalas: number;
  tiempo_min: number;
  jugadores: number;
  precio: number;     // si tu backend usa BigDecimal, Angular puede usar number
  stock: number;
  imagen?: string;    // ✅ opcional
}