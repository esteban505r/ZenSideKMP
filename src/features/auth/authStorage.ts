import type { AuthCredentials, RegisterInput, StoredAccount, User } from './types';

// Local-only auth storage. This is a placeholder ahead of a real backend —
// NOT secure (plaintext password comparison, no hashing). See
// docs/PRODUCT.md Phase 3 for the planned networked replacement.
const ACCOUNTS_KEY = 'zenside_accounts';
const SESSION_KEY = 'zenside_session';

function readAccounts(): StoredAccount[] {
  try {
    const raw = localStorage.getItem(ACCOUNTS_KEY);
    return raw ? (JSON.parse(raw) as StoredAccount[]) : [];
  } catch {
    return [];
  }
}

function writeAccounts(accounts: StoredAccount[]): void {
  localStorage.setItem(ACCOUNTS_KEY, JSON.stringify(accounts));
}

function toUser(account: StoredAccount): User {
  return { displayName: account.displayName, email: account.email };
}

export class AuthError extends Error {}

export function register(input: RegisterInput): User {
  const accounts = readAccounts();
  const normalizedEmail = input.email.trim().toLowerCase();

  if (accounts.some((account) => account.email.toLowerCase() === normalizedEmail)) {
    throw new AuthError('Ya existe una cuenta con ese correo.');
  }

  const account: StoredAccount = {
    displayName: input.displayName.trim(),
    email: normalizedEmail,
    password: input.password,
  };

  writeAccounts([...accounts, account]);
  localStorage.setItem(SESSION_KEY, normalizedEmail);
  return toUser(account);
}

export function login(credentials: AuthCredentials): User {
  const accounts = readAccounts();
  const normalizedEmail = credentials.email.trim().toLowerCase();
  const account = accounts.find((item) => item.email.toLowerCase() === normalizedEmail);

  if (!account || account.password !== credentials.password) {
    throw new AuthError('Correo o contraseña incorrectos.');
  }

  localStorage.setItem(SESSION_KEY, normalizedEmail);
  return toUser(account);
}

export function logout(): void {
  localStorage.removeItem(SESSION_KEY);
}

export function restoreSession(): User | null {
  const email = localStorage.getItem(SESSION_KEY);
  if (!email) return null;

  const account = readAccounts().find((item) => item.email.toLowerCase() === email.toLowerCase());
  return account ? toUser(account) : null;
}
