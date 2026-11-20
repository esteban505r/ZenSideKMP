import { useEffect, useState } from 'react';
import { useAppDispatch, useAppSelector } from '../../../app/hooks';
import { PosturaList } from '../components/PosturaList';
import { CategoriaFilter } from '../components/CategoriaFilter';
import {
  loadPosturasFromSeed,
  selectAllPosturas,
  selectCategorias,
  selectPosturasError,
  selectPosturasStatus,
} from '../posturasSlice';

export function PosturasListPage() {
  const dispatch = useAppDispatch();
  const status = useAppSelector(selectPosturasStatus);
  const error = useAppSelector(selectPosturasError);
  const posturas = useAppSelector(selectAllPosturas);
  const categorias = useAppSelector(selectCategorias);
  const [categoriaFilter, setCategoriaFilter] = useState<string | null>(null);

  useEffect(() => {
    if (status === 'idle') {
      dispatch(loadPosturasFromSeed());
    }
  }, [status, dispatch]);

  const filteredPosturas = categoriaFilter
    ? posturas.filter((postura) => postura.categoria === categoriaFilter)
    : posturas;

  return (
    <div className="flex flex-col gap-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Catálogo de posturas</h1>
        <p className="mt-1 text-sm text-gray-500">
          Elige una postura para ver la descripción y recomendaciones.
        </p>
      </div>

      {status === 'loading' && <p className="text-sm text-gray-500">Cargando posturas…</p>}
      {status === 'failed' && (
        <p className="text-sm text-red-600">{error ?? 'No se pudieron cargar las posturas.'}</p>
      )}
      {status === 'succeeded' && (
        <>
          <CategoriaFilter
            categorias={categorias}
            selected={categoriaFilter}
            onSelect={setCategoriaFilter}
          />
          <PosturaList posturas={filteredPosturas} />
        </>
      )}
    </div>
  );
}
