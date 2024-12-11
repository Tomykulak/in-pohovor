package cz.inqool.tennisapp.utils.config;

import cz.inqool.tennisapp.domain.court.Court;
import cz.inqool.tennisapp.domain.court.CourtRepository;
import cz.inqool.tennisapp.domain.surfaceType.SurfaceType;
import cz.inqool.tennisapp.domain.surfaceType.SurfaceTypeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataImporter implements CommandLineRunner {
    private final CourtRepository courtRepository;
    private final SurfaceTypeRepository surfaceTypeRepository;

    @Value("${tennis-app.data.initialize}")
    private boolean initialize;

    public DataImporter(CourtRepository courtRepository, SurfaceTypeRepository surfaceTypeRepository) {
        this.courtRepository = courtRepository;
        this.surfaceTypeRepository = surfaceTypeRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        if (initialize) {

            // Initialize surface types
            SurfaceType grass = new SurfaceType("Grass", 5);
            SurfaceType carpet = new SurfaceType("Carpet", 10);
            surfaceTypeRepository.save(grass);
            surfaceTypeRepository.save(carpet);

            // Initialize courts
            courtRepository.save(new Court("Grass court 1", grass));
            courtRepository.save(new Court("Grass court 2", grass));
            courtRepository.save(new Court("Carpet court 1", carpet));
            courtRepository.save(new Court("Carpet court 2", carpet));

            System.out.println("Data imported successfully into H2 database.");
        } else {
            System.out.println("Data initialization failed.");
        }
    }
}
