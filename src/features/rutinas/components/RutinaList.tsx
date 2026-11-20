import { RutinaCard } from './RutinaCard';
import type { Rutina } from '../types';
import type { Postura } from '../../posturas/types';

interface RutinaListProps {
  rutinas: Rutina[];
  posturasById: Map<number, Postura>;
}

export function RutinaList({ rutinas, posturasById }: RutinaListProps) {
  if (rutinas.length === 0) {
    return <p className="text-sm text-gray-500">Todavía no hay rutinas disponibles.</p>;
  }

  return (
    <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-3">
      {rutinas.map((rutina) => (
        <RutinaCard
          key={rutina.id}
          rutina={rutina}
          posturas={rutina.posturaIds
            .map((id) => posturasById.get(id))
            .filter((postura): postura is Postura => postura !== undefined)}
        />
      ))}
    </div>
  );
}
