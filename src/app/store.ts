import { configureStore } from '@reduxjs/toolkit';
import authReducer from '../features/auth/authSlice';
import posturasReducer from '../features/posturas/posturasSlice';
import rutinasReducer from '../features/rutinas/rutinasSlice';

export const store = configureStore({
  reducer: {
    auth: authReducer,
    posturas: posturasReducer,
    rutinas: rutinasReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
