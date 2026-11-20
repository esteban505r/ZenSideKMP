import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from '../../../app/hooks';
import { PosturaDetailView } from '../components/PosturaDetailView';
import {
  loadPosturasFromSeed,
  selectPosturaById,
  selectPosturasError,
  selectPosturasStatus,
} from '../posturasSlice';

export function PosturaDetailPage() {
  const { id } = useParams<{ id: string }>();
  const dispatch = useAppDispatch();
  const status = useAppSelector(selectPosturasStatus);
  const error = useAppSelector(selectPosturasError);
  const postura = useAppSelector(selectPosturaById(Number(id)));

  useEffect(() => {
    if (status === 'idle') {
      dispatch(loadPosturasFromSeed());
    }
  }, [status, dispatch]);

  if (status === 'loading' || status === 'idle') {
    return <p className="text-sm text-gray-500">Cargando postura…</p>;
  }

  if (status === 'failed') {
    return <p className="text-sm text-red-600">{error ?? 'No se pudo cargar la postura.'}</p>;
  }

  if (!postura) {
    return <p className="text-sm text-gray-500">No encontramos esa postura.</p>;
  }

  return <PosturaDetailView postura={postura} />;
}
