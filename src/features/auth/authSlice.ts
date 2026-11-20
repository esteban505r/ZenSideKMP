import { createSlice, type PayloadAction } from '@reduxjs/toolkit';
import type { RootState } from '../../app/store';
import type { AuthStatus, User } from './types';
import { restoreSession } from './authStorage';

interface AuthState {
  user: User | null;
  status: AuthStatus;
  error: string | null;
}

const initialState: AuthState = {
  user: restoreSession(),
  status: 'idle',
  error: null,
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    authRequestStarted(state) {
      state.status = 'loading';
      state.error = null;
    },
    authSucceeded(state, action: PayloadAction<User>) {
      state.status = 'succeeded';
      state.user = action.payload;
      state.error = null;
    },
    authFailed(state, action: PayloadAction<string>) {
      state.status = 'failed';
      state.error = action.payload;
    },
    loggedOut(state) {
      state.user = null;
      state.status = 'idle';
      state.error = null;
    },
  },
});

export const { authRequestStarted, authSucceeded, authFailed, loggedOut } = authSlice.actions;
export default authSlice.reducer;

export const selectCurrentUser = (state: RootState) => state.auth.user;
export const selectAuthStatus = (state: RootState) => state.auth.status;
export const selectAuthError = (state: RootState) => state.auth.error;
export const selectIsAuthenticated = (state: RootState) => state.auth.user !== null;
