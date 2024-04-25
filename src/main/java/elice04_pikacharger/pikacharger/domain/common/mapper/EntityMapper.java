package elice04_pikacharger.pikacharger.domain.common.mapper;

import java.util.List;

public interface EntityMapper<D, E> {
    D toDto(final E entity);
    List<D> toDtoList(final List<E> entities);
}