import { Link, Navigate, useNavigate } from 'react-router-dom';
import { Card } from '../../../shared/components/Card';
import { RegisterForm } from '../components/RegisterForm';
import { useAppDispatch, useAppSelector } from '../../../app/hooks';
import {
  authFailed,
  authRequestStarted,
  authSucceeded,
  selectAuthError,
  selectAuthStatus,
  selectIsAuthenticated,
} from '../authSlice';
import { AuthError, register } from '../authStorage';
import type { RegisterInput } from '../types';

export function RegisterPage() {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const status = useAppSelector(selectAuthStatus);
  const error = useAppSelector(selectAuthError);
  const isAuthenticated = useAppSelector(selectIsAuthenticated);

  if (isAuthenticated) {
    return <Navigate to="/posturas" replace />;
  }

  function handleSubmit(input: RegisterInput) {
    dispatch(authRequestStarted());
    try {
      const user = register(input);
      dispatch(authSucceeded(user));
      navigate('/posturas');
    } catch (err) {
      const message = err instanceof AuthError ? err.message : 'No se pudo crear la cuenta.';
      dispatch(authFailed(message));
    }
  }

  return (
    <div className="flex min-h-screen items-center justify-center bg-background px-4">
      <Card className="w-full max-w-sm">
        <h1 className="mb-1 text-2xl font-bold text-primary-700">ZenSide</h1>
        <p className="mb-6 text-sm text-gray-500">Crea tu cuenta para empezar.</p>
        <RegisterForm
          onSubmit={handleSubmit}
          isSubmitting={status === 'loading'}
          errorMessage={error}
        />
        <p className="mt-6 text-center text-sm text-gray-500">
          ¿Ya tienes cuenta?{' '}
          <Link to="/login" className="font-semibold text-primary hover:underline">
            Inicia sesión
          </Link>
        </p>
      </Card>
    </div>
  );
}
