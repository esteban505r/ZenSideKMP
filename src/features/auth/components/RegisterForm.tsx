import { useState, type FormEvent } from 'react';
import { TextField } from '../../../shared/components/TextField';
import { Button } from '../../../shared/components/Button';
import type { RegisterInput } from '../types';

interface RegisterFormProps {
  onSubmit: (input: RegisterInput) => void;
  isSubmitting: boolean;
  errorMessage: string | null;
}

export function RegisterForm({ onSubmit, isSubmitting, errorMessage }: RegisterFormProps) {
  const [displayName, setDisplayName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    onSubmit({ displayName, email, password });
  }

  return (
    <form onSubmit={handleSubmit} className="flex flex-col gap-4">
      <TextField
        id="register-name"
        label="Nombre"
        type="text"
        autoComplete="name"
        required
        value={displayName}
        onChange={(e) => setDisplayName(e.target.value)}
      />
      <TextField
        id="register-email"
        label="Correo"
        type="email"
        autoComplete="email"
        required
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />
      <TextField
        id="register-password"
        label="Contraseña"
        type="password"
        autoComplete="new-password"
        required
        minLength={6}
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      {errorMessage && <p className="text-sm text-red-600">{errorMessage}</p>}
      <Button type="submit" disabled={isSubmitting} className="mt-2 w-full">
        {isSubmitting ? 'Creando cuenta…' : 'Crear cuenta'}
      </Button>
    </form>
  );
}
