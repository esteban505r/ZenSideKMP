import { createSlice, type PayloadAction } from '@reduxjs/toolkit';
import type { AppDispatch, RootState } from '../../app/store';
import type { LoadStatus, Rutina } from './types';

interface RutinasState {
  items: Rutina[];
  status: LoadStatus;
  error: string | null;
}

const initialState: RutinasState = {
  items: [],
  status: 'idle',
  error: null,
};

const rutinasSlice = createSlice({
  name: 'rutinas',
  initialState,
  reducers: {
    rutinasLoadStarted(state) {
      state.status = 'loading';
      state.error = null;
    },
    rutinasLoadSucceeded(state, action: PayloadAction<Rutina[]>) {
      state.status = 'succeeded';
      state.items = action.payload;
    },
    rutinasLoadFailed(state, action: PayloadAction<string>) {
      state.status = 'failed';
      state.error = action.payload;
    },
  },
});

export const { rutinasLoadStarted, rutinasLoadSucceeded, rutinasLoadFailed } =
  rutinasSlice.actions;
export default rutinasSlice.reducer;

// Sample rutinas: one per categoría, grouping all posturas that share it —
// same grouping approach docs/PRODUCT.md describes for v1. Derived from
// whatever posturas are already loaded in the store, rather than a second
// copy of the seed data.
export function loadRutinasFromPosturas() {
  return (dispatch: AppDispatch, getState: () => RootState) => {
    dispatch(rutinasLoadStarted());
    try {
      const { items: posturas } = getState().posturas;
      const byCategoria = new Map<string, number[]>();

      for (const postura of posturas) {
        const ids = byCategoria.get(postura.categoria) ?? [];
        ids.push(postura.id);
        byCategoria.set(postura.categoria, ids);
      }

      const rutinas: Rutina[] = Array.from(byCategoria.entries()).map(
        ([categoria, posturaIds]) => ({
          id: categoria.toLowerCase(),
          nombre: `Rutina de ${categoria}`,
          categoria,
          posturaIds,
        }),
      );

      dispatch(rutinasLoadSucceeded(rutinas));
    } catch {
      dispatch(rutinasLoadFailed('No se pudieron generar las rutinas.'));
    }
  };
}

export const selectRutinasStatus = (state: RootState) => state.rutinas.status;
export const selectRutinasError = (state: RootState) => state.rutinas.error;
export const selectAllRutinas = (state: RootState) => state.rutinas.items;
export const selectRutinaById = (id: string) => (state: RootState) =>
  state.rutinas.items.find((r) => r.id === id);
