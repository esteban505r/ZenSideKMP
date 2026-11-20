import { useState, type FormEvent } from 'react';
import { TextField } from '../../../shared/components/TextField';
import { Button } from '../../../shared/components/Button';
import type { AuthCredentials } from '../types';

interface LoginFormProps {
  onSubmit: (credentials: AuthCredentials) => void;
  isSubmitting: boolean;
  errorMessage: string | null;
}

export function LoginForm({ onSubmit, isSubmitting, errorMessage }: LoginFormProps) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    onSubmit({ email, password });
  }

  return (
    <form onSubmit={handleSubmit} className="flex flex-col gap-4">
      <TextField
        id="login-email"
        label="Correo"
        type="email"
        autoComplete="email"
        required
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />
      <TextField
        id="login-password"
        label="Contraseña"
        type="password"
        autoComplete="current-password"
        required
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      {errorMessage && <p className="text-sm text-red-600">{errorMessage}</p>}
      <Button type="submit" disabled={isSubmitting} className="mt-2 w-full">
        {isSubmitting ? 'Ingresando…' : 'Ingresar'}
      </Button>
    </form>
  );
}
