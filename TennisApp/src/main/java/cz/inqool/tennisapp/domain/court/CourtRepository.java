package cz.inqool.tennisapp.domain.court;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourtRepository extends CrudRepository<Court, Long> {
    List<Court> findByDeletedFalse();
}
