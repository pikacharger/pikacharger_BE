package elice04_pikacharger.pikacharger.domain.common.mapper;

import java.util.List;

public interface EntityMapper<D, E> {
    D toDto(final E entity);
    List<D> toDtoList(final List<E> entities);
}

//public interface EntityMapper<D, E> {
//    E toEntity(final D dto);
//    D toDto(final E entity);
//}