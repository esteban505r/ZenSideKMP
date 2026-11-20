interface CategoriaFilterProps {
  categorias: string[];
  selected: string | null;
  onSelect: (categoria: string | null) => void;
}

export function CategoriaFilter({ categorias, selected, onSelect }: CategoriaFilterProps) {
  return (
    <div className="flex flex-wrap gap-2">
      <FilterPill label="Todas" isActive={selected === null} onClick={() => onSelect(null)} />
      {categorias.map((categoria) => (
        <FilterPill
          key={categoria}
          label={categoria}
          isActive={selected === categoria}
          onClick={() => onSelect(categoria)}
        />
      ))}
    </div>
  );
}

interface FilterPillProps {
  label: string;
  isActive: boolean;
  onClick: () => void;
}

function FilterPill({ label, isActive, onClick }: FilterPillProps) {
  return (
    <button
      type="button"
      onClick={onClick}
      className={`rounded-full px-4 py-1.5 text-sm font-semibold transition-colors ${
        isActive ? 'bg-primary text-white' : 'bg-primary-50 text-primary-700 hover:bg-primary-100'
      }`}
    >
      {label}
    </button>
  );
}
