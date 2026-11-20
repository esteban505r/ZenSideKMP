import type { ReactElement } from 'react';
import { Navigate } from 'react-router-dom';
import { useAppSelector } from './hooks';
import { selectIsAuthenticated } from '../features/auth/authSlice';

interface RequireAuthProps {
  children: ReactElement;
}

export function RequireAuth({ children }: RequireAuthProps) {
  const isAuthenticated = useAppSelector(selectIsAuthenticated);

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  return children;
}
