import type { ButtonHTMLAttributes } from 'react';

type ButtonVariant = 'primary' | 'secondary' | 'ghost';

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: ButtonVariant;
}

const variantClasses: Record<ButtonVariant, string> = {
  primary: 'bg-primary text-white hover:bg-primary-600 disabled:bg-primary-200',
  secondary: 'bg-secondary text-white hover:bg-secondary-600 disabled:bg-secondary-200',
  ghost: 'bg-transparent text-primary hover:bg-primary-50 disabled:text-primary-200',
};

export function Button({ variant = 'primary', className = '', ...props }: ButtonProps) {
  const base =
    'rounded-xl px-4 py-2.5 font-semibold transition-colors disabled:cursor-not-allowed';
  return <button className={`${base} ${variantClasses[variant]} ${className}`} {...props} />;
}
