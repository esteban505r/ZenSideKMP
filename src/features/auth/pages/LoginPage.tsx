import { Link, Navigate, useNavigate } from 'react-router-dom';
import { Card } from '../../../shared/components/Card';
import { LoginForm } from '../components/LoginForm';
import { useAppDispatch, useAppSelector } from '../../../app/hooks';
import {
  authFailed,
  authRequestStarted,
  authSucceeded,
  selectAuthError,
  selectAuthStatus,
  selectIsAuthenticated,
} from '../authSlice';
import { AuthError, login } from '../authStorage';
import type { AuthCredentials } from '../types';

export function LoginPage() {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const status = useAppSelector(selectAuthStatus);
  const error = useAppSelector(selectAuthError);
  const isAuthenticated = useAppSelector(selectIsAuthenticated);

  if (isAuthenticated) {
    return <Navigate to="/posturas" replace />;
  }

  function handleSubmit(credentials: AuthCredentials) {
    dispatch(authRequestStarted());
    try {
      const user = login(credentials);
      dispatch(authSucceeded(user));
      navigate('/posturas');
    } catch (err) {
      const message = err instanceof AuthError ? err.message : 'No se pudo iniciar sesión.';
      dispatch(authFailed(message));
    }
  }

  return (
    <div className="flex min-h-screen items-center justify-center bg-background px-4">
      <Card className="w-full max-w-sm">
        <h1 className="mb-1 text-2xl font-bold text-primary-700">ZenSide</h1>
        <p className="mb-6 text-sm text-gray-500">Ingresa a tu cuenta para continuar.</p>
        <LoginForm
          onSubmit={handleSubmit}
          isSubmitting={status === 'loading'}
          errorMessage={error}
        />
        <p className="mt-6 text-center text-sm text-gray-500">
          ¿No tienes cuenta?{' '}
          <Link to="/register" className="font-semibold text-primary hover:underline">
            Regístrate
          </Link>
        </p>
      </Card>
    </div>
  );
}
