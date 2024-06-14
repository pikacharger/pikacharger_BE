package elice04_pikacharger.pikacharger.domain.common.mapper;

import java.util.List;

public interface EntityMapper<D, E> {
    D toDto(final E e);

    E toEntity(D d);

    List<E> toDtoList(List<E> entityList);

    List<D> toEntityList(List<D> dtoList);
}