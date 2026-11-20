import { Navigate, Route, BrowserRouter, Routes } from 'react-router-dom';
import { RequireAuth } from './RequireAuth';
import { AppLayout } from './AppLayout';
import { LoginPage } from '../features/auth/pages/LoginPage';
import { RegisterPage } from '../features/auth/pages/RegisterPage';
import { PosturasListPage } from '../features/posturas/pages/PosturasListPage';
import { PosturaDetailPage } from '../features/posturas/pages/PosturaDetailPage';
import { RutinasListPage } from '../features/rutinas/pages/RutinasListPage';

export function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route
          element={
            <RequireAuth>
              <AppLayout />
            </RequireAuth>
          }
        >
          <Route path="/posturas" element={<PosturasListPage />} />
          <Route path="/posturas/:id" element={<PosturaDetailPage />} />
          <Route path="/rutinas" element={<RutinasListPage />} />
        </Route>
        <Route path="/" element={<Navigate to="/posturas" replace />} />
        <Route path="*" element={<Navigate to="/posturas" replace />} />
      </Routes>
    </BrowserRouter>
  );
}
