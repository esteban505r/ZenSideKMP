export interface Rutina {
  id: string;
  nombre: string;
  categoria: string;
  posturaIds: number[];
}

export type LoadStatus = 'idle' | 'loading' | 'succeeded' | 'failed';
