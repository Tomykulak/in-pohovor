package cz.inqool.tennisapp.domain.court;

import cz.inqool.tennisapp.domain.surfaceType.SurfaceType;
import cz.inqool.tennisapp.domain.surfaceType.SurfaceTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class CourtRepositoryTest {
    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private SurfaceTypeRepository surfaceTypeRepository;

    @Test
    public void findByDeletedFalse() {
        SurfaceType grass = new SurfaceType("Grass", 5);
        surfaceTypeRepository.save(grass);

        // Initialize courts
        Court court1 = new Court("Grass court 1", grass);
        Court court2 = new Court("Grass court 1", grass);
        Court court3 = new Court("Grass court 1", grass);

        court1.setDeleted(true);
        court2.setDeleted(true);

        // save courts
        courtRepository.save(court1); // deleted
        courtRepository.save(court2); // deleted
        courtRepository.save(court3); // not deleted
        Assertions.assertEquals(1, (long) courtRepository.findByDeletedFalse().size());
    }

    @Test
    void getAllCourts() {}
}
