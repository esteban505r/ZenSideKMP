import type { InputHTMLAttributes } from 'react';

interface TextFieldProps extends InputHTMLAttributes<HTMLInputElement> {
  label: string;
}

export function TextField({ label, id, className = '', ...props }: TextFieldProps) {
  return (
    <div className="flex flex-col gap-1.5">
      <label htmlFor={id} className="text-sm font-medium text-gray-700">
        {label}
      </label>
      <input
        id={id}
        className={`rounded-xl border border-primary-100 bg-white px-4 py-2.5 text-gray-900 outline-none transition-colors focus:border-primary focus:ring-2 focus:ring-primary-100 ${className}`}
        {...props}
      />
    </div>
  );
}
