import { Card } from '../../../shared/components/Card';
import type { Rutina } from '../types';
import type { Postura } from '../../posturas/types';

interface RutinaCardProps {
  rutina: Rutina;
  posturas: Postura[];
}

export function RutinaCard({ rutina, posturas }: RutinaCardProps) {
  return (
    <Card>
      <span className="inline-block rounded-full bg-secondary-50 px-3 py-1 text-xs font-semibold text-secondary-700">
        {rutina.categoria}
      </span>
      <h2 className="mt-3 text-lg font-bold text-gray-900">{rutina.nombre}</h2>
      <p className="mt-1 text-sm text-gray-500">
        {posturas.length} {posturas.length === 1 ? 'postura' : 'posturas'}
      </p>
      <ul className="mt-3 flex flex-col gap-1">
        {posturas.map((postura) => (
          <li key={postura.id} className="text-sm text-gray-700">
            {postura.nombre}
          </li>
        ))}
      </ul>
    </Card>
  );
}
