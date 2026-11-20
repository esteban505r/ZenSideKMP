import { useEffect, useMemo } from 'react';
import { useAppDispatch, useAppSelector } from '../../../app/hooks';
import { RutinaList } from '../components/RutinaList';
import { loadRutinasFromPosturas, selectAllRutinas, selectRutinasError, selectRutinasStatus } from '../rutinasSlice';
import { loadPosturasFromSeed, selectAllPosturas, selectPosturasStatus } from '../../posturas/posturasSlice';

export function RutinasListPage() {
  const dispatch = useAppDispatch();
  const posturasStatus = useAppSelector(selectPosturasStatus);
  const posturas = useAppSelector(selectAllPosturas);
  const rutinasStatus = useAppSelector(selectRutinasStatus);
  const rutinasError = useAppSelector(selectRutinasError);
  const rutinas = useAppSelector(selectAllRutinas);

  useEffect(() => {
    if (posturasStatus === 'idle') {
      dispatch(loadPosturasFromSeed());
    }
  }, [posturasStatus, dispatch]);

  useEffect(() => {
    if (posturasStatus === 'succeeded' && rutinasStatus === 'idle') {
      dispatch(loadRutinasFromPosturas());
    }
  }, [posturasStatus, rutinasStatus, dispatch]);

  const posturasById = useMemo(() => new Map(posturas.map((p) => [p.id, p])), [posturas]);

  const isLoading = posturasStatus === 'loading' || rutinasStatus === 'loading';
  const hasFailed = posturasStatus === 'failed' || rutinasStatus === 'failed';

  return (
    <div className="flex flex-col gap-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Rutinas</h1>
        <p className="mt-1 text-sm text-gray-500">
          Secuencias de posturas agrupadas por categoría.
        </p>
      </div>

      {isLoading && <p className="text-sm text-gray-500">Cargando rutinas…</p>}
      {hasFailed && (
        <p className="text-sm text-red-600">{rutinasError ?? 'No se pudieron cargar las rutinas.'}</p>
      )}
      {rutinasStatus === 'succeeded' && (
        <RutinaList rutinas={rutinas} posturasById={posturasById} />
      )}
    </div>
  );
}
