import { createSlice, createSelector, type PayloadAction } from '@reduxjs/toolkit';
import type { AppDispatch, RootState } from '../../app/store';
import type { LoadStatus, Postura } from './types';
import posturasSeed from '../../data/posturas.json';

interface PosturasState {
  items: Postura[];
  status: LoadStatus;
  error: string | null;
}

const initialState: PosturasState = {
  items: [],
  status: 'idle',
  error: null,
};

const posturasSlice = createSlice({
  name: 'posturas',
  initialState,
  reducers: {
    posturasLoadStarted(state) {
      state.status = 'loading';
      state.error = null;
    },
    posturasLoadSucceeded(state, action: PayloadAction<Postura[]>) {
      state.status = 'succeeded';
      state.items = action.payload;
    },
    posturasLoadFailed(state, action: PayloadAction<string>) {
      state.status = 'failed';
      state.error = action.payload;
    },
  },
});

export const { posturasLoadStarted, posturasLoadSucceeded, posturasLoadFailed } =
  posturasSlice.actions;
export default posturasSlice.reducer;

// Local seed data load. There's no backend yet, so this resolves
// synchronously — the status field is kept consistent with the pattern that
// will matter once posturas come from a real API (see docs/PRODUCT.md Phase 3).
export function loadPosturasFromSeed() {
  return (dispatch: AppDispatch) => {
    dispatch(posturasLoadStarted());
    try {
      dispatch(posturasLoadSucceeded(posturasSeed as Postura[]));
    } catch {
      dispatch(posturasLoadFailed('No se pudieron cargar las posturas.'));
    }
  };
}

export const selectPosturasStatus = (state: RootState) => state.posturas.status;
export const selectPosturasError = (state: RootState) => state.posturas.error;
export const selectAllPosturas = (state: RootState) => state.posturas.items;

export const selectCategorias = createSelector(selectAllPosturas, (posturas) =>
  Array.from(new Set(posturas.map((p) => p.categoria))).sort(),
);

export const selectPosturaById = (id: number) => (state: RootState) =>
  state.posturas.items.find((p) => p.id === id);
