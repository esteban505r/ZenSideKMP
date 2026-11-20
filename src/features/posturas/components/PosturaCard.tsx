import { Link } from 'react-router-dom';
import { Card } from '../../../shared/components/Card';
import type { Postura } from '../types';

interface PosturaCardProps {
  postura: Postura;
}

export function PosturaCard({ postura }: PosturaCardProps) {
  return (
    <Link to={`/posturas/${postura.id}`}>
      <Card className="h-full transition-shadow hover:shadow-md">
        <span className="inline-block rounded-full bg-secondary-50 px-3 py-1 text-xs font-semibold text-secondary-700">
          {postura.categoria}
        </span>
        <h2 className="mt-3 text-lg font-bold text-gray-900">{postura.nombre}</h2>
        <p className="mt-1 text-sm text-gray-500">{postura.tiempo}</p>
        <p className="mt-3 line-clamp-2 text-sm text-gray-600">{postura.descripcion}</p>
      </Card>
    </Link>
  );
}
