import { Outlet, useNavigate } from 'react-router-dom';
import { AppHeader } from '../shared/components/AppHeader';
import { useAppDispatch, useAppSelector } from './hooks';
import { loggedOut, selectCurrentUser } from '../features/auth/authSlice';
import { logout } from '../features/auth/authStorage';

export function AppLayout() {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const user = useAppSelector(selectCurrentUser);

  function handleLogout() {
    logout();
    dispatch(loggedOut());
    navigate('/login');
  }

  return (
    <div className="min-h-screen bg-background">
      <AppHeader displayName={user?.displayName ?? ''} onLogout={handleLogout} />
      <main className="mx-auto max-w-5xl px-6 py-8">
        <Outlet />
      </main>
    </div>
  );
}
