import { NavLink } from 'react-router-dom';

interface AppHeaderProps {
  displayName: string;
  onLogout: () => void;
}

const linkClasses = ({ isActive }: { isActive: boolean }) =>
  `rounded-xl px-3 py-2 text-sm font-semibold transition-colors ${
    isActive ? 'bg-primary-50 text-primary-700' : 'text-gray-500 hover:text-primary-700'
  }`;

export function AppHeader({ displayName, onLogout }: AppHeaderProps) {
  return (
    <header className="flex items-center justify-between border-b border-primary-100 bg-white px-6 py-4">
      <div className="flex items-center gap-6">
        <span className="text-lg font-bold text-primary-700">ZenSide</span>
        <nav className="flex items-center gap-1">
          <NavLink to="/posturas" className={linkClasses}>
            Posturas
          </NavLink>
          <NavLink to="/rutinas" className={linkClasses}>
            Rutinas
          </NavLink>
        </nav>
      </div>
      <div className="flex items-center gap-4">
        <span className="text-sm text-gray-500">Hola, {displayName}</span>
        <button
          onClick={onLogout}
          className="rounded-xl px-3 py-2 text-sm font-semibold text-primary hover:bg-primary-50"
        >
          Salir
        </button>
      </div>
    </header>
  );
}
