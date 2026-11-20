import { Link } from 'react-router-dom';
import { Card } from '../../../shared/components/Card';
import type { Postura } from '../types';

interface PosturaDetailViewProps {
  postura: Postura;
}

export function PosturaDetailView({ postura }: PosturaDetailViewProps) {
  return (
    <div className="flex flex-col gap-4">
      <Link to="/posturas" className="text-sm font-semibold text-primary hover:underline">
        ← Volver a posturas
      </Link>
      <Card>
        <span className="inline-block rounded-full bg-secondary-50 px-3 py-1 text-xs font-semibold text-secondary-700">
          {postura.categoria}
        </span>
        <h1 className="mt-3 text-2xl font-bold text-gray-900">{postura.nombre}</h1>
        <p className="mt-1 text-sm text-gray-500">Duración: {postura.tiempo}</p>
        <p className="mt-4 text-gray-700">{postura.descripcion}</p>

        <h2 className="mt-6 text-lg font-bold text-gray-900">Recomendaciones</h2>
        <ul className="mt-2 flex flex-col gap-2">
          {postura.recomendaciones.map((recomendacion) => (
            <li key={recomendacion} className="flex gap-2 text-gray-700">
              <span className="text-secondary">•</span>
              <span>{recomendacion}</span>
            </li>
          ))}
        </ul>
      </Card>
    </div>
  );
}
