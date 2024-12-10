package cz.inqool.tennisapp.domain.surfaceType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class SurfaceTypeRepositoryTest {

    @Autowired
    private SurfaceTypeRepository surfaceTypeRepository;

    @Test
    void findByDeletedFalse(){
        SurfaceType grass = new SurfaceType("Grass", 5);
        SurfaceType carpet = new SurfaceType("Carpet", 10);
        grass.setDeleted(false);
        carpet.setDeleted(true);

        surfaceTypeRepository.save(grass);
        surfaceTypeRepository.save(carpet);

        assertEquals(1, surfaceTypeRepository.findByDeletedFalse().size());
    }
}
