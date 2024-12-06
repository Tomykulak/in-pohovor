package cz.inqool.tennisapp.domain.surfaceType;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SurfaceTypeRepository extends CrudRepository<SurfaceType, Long> {
    List<SurfaceType> findByDeletedFalse();
}
