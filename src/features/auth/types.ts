export interface User {
  displayName: string;
  email: string;
}

export interface StoredAccount extends User {
  password: string;
}

export interface AuthCredentials {
  email: string;
  password: string;
}

export interface RegisterInput extends AuthCredentials {
  displayName: string;
}

export type AuthStatus = 'idle' | 'loading' | 'succeeded' | 'failed';
