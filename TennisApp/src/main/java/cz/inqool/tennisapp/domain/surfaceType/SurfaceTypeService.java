package cz.inqool.tennisapp.domain.surfaceType;

import cz.inqool.tennisapp.utils.exceptions.AlreadyDeletedException;
import cz.inqool.tennisapp.utils.exceptions.BadRequestException;
import cz.inqool.tennisapp.utils.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class SurfaceTypeService {
    private final SurfaceTypeRepository surfaceTypeRepository;

    public List<SurfaceType> getAllSurfaceTypes() {
        return surfaceTypeRepository.findByDeletedFalse();
    }

    public SurfaceType createSurfaceType(SurfaceType surfaceType) {
        if (surfaceType.getName() == null) {
            throw new BadRequestException();
        }

        return surfaceTypeRepository.save(surfaceType);
    }

    public void deleteSurfaceType(int surfaceTypeId) {
        SurfaceType surface = surfaceTypeRepository.findById((long) surfaceTypeId)
                .orElseThrow(NotFoundException::new);

        if (surface.isDeleted()){
            throw new AlreadyDeletedException();
        }

        surface.setDeleted(true);
        surfaceTypeRepository.save(surface);
    }

    public SurfaceType updateSurfaceType(int surfaceTypeId, SurfaceType surfaceType) throws IllegalArgumentException {
        SurfaceType existingSurfaceType = surfaceTypeRepository.findById((long) surfaceTypeId)
                .orElseThrow(NotFoundException::new);
        if (surfaceType.getName() == null) {
            throw new BadRequestException();
        }

        existingSurfaceType.setName(surfaceType.getName());
        existingSurfaceType.setPricePerMinute(surfaceType.getPricePerMinute());
        return surfaceTypeRepository.save(existingSurfaceType);
    }
}
