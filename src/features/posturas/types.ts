export interface Postura {
  id: number;
  nombre: string;
  categoria: string;
  tiempo: string;
  descripcion: string;
  recomendaciones: string[];
}

export type LoadStatus = 'idle' | 'loading' | 'succeeded' | 'failed';
