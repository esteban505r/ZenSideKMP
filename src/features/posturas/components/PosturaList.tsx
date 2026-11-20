import { PosturaCard } from './PosturaCard';
import type { Postura } from '../types';

interface PosturaListProps {
  posturas: Postura[];
}

export function PosturaList({ posturas }: PosturaListProps) {
  if (posturas.length === 0) {
    return <p className="text-sm text-gray-500">No hay posturas en esta categoría.</p>;
  }

  return (
    <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-3">
      {posturas.map((postura) => (
        <PosturaCard key={postura.id} postura={postura} />
      ))}
    </div>
  );
}
